package com.study.business.sc.order.service;

import com.study.business.sc.course.enums.CourseChargeTypeEnum;
import com.study.business.sc.course.repo.model.ScCourse;
import com.study.business.sc.course.repo.model.ScCourseCharge;
import com.study.business.sc.course.repo.model.ScCourseCla;
import com.study.business.sc.course.service.IScClaTimeAttendService;
import com.study.business.sc.course.service.IScCourseChargeService;
import com.study.business.sc.course.service.IScCourseClaService;
import com.study.business.sc.course.service.IScCourseService;
import com.study.business.sc.log.enums.LogTypeEnum;
import com.study.business.sc.log.repo.model.ScStudentCourseLog;
import com.study.business.sc.log.service.IScStudentCourseLogService;
import com.study.business.sc.order.domain.req.ReqBusinessSignUp;
import com.study.business.sc.order.domain.req.ReqBusinessSignUpItem;
import com.study.business.sc.order.domain.req.ReqBusinessSignUpReceipt;
import com.study.business.sc.order.domain.req.ReqSearchScOrder;
import com.study.business.sc.order.domain.resp.RespOrder;
import com.study.business.sc.order.enums.OrderStatusEnum;
import com.study.business.sc.order.repo.mapper.ScOrderMapper;
import com.study.business.sc.order.repo.model.ScOrder;
import com.study.business.sc.order.repo.model.ScOrderAccount;
import com.study.business.sc.order.repo.model.ScOrderDetail;
import com.study.business.sc.student.repo.model.ScStudent;
import com.study.business.sc.student.repo.model.ScStudentCourse;
import com.study.business.sc.student.repo.model.ScStudentCourseOrder;
import com.study.business.sc.student.service.IScStudentAccountService;
import com.study.business.sc.student.service.IScStudentCourseOrderService;
import com.study.business.sc.student.service.IScStudentCourseService;
import com.study.business.sc.student.service.IScStudentService;
import com.study.business.sys.admin.repo.model.SysDept;
import com.study.business.sys.admin.repo.model.SysUser;
import com.study.business.sys.admin.service.ISysDeptService;
import com.study.business.sys.admin.service.ISysUserService;
import com.study.business.sys.receipt.repo.model.SysReceiptAccount;
import com.study.business.sys.receipt.service.ISysReceiptAccountService;
import com.study.business.sys.staff.repo.model.SysStaff;
import com.study.business.sys.staff.service.ISysStaffService;
import com.study.business.sys.tag.service.ISysTagService;
import com.study.config.exception.BusinessException;
import com.study.config.login.LoginUser;
import com.study.core.api.APIBaseResponse;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.core.page.RespPage;
import com.study.core.utils.DateUtil;
import com.study.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
@Slf4j
public class BusinessScOrderService {

    @Autowired
    private IScOrderService scOrderService;
    @Autowired
    private IScOrderDetailService orderDetailService;
    @Autowired
    private ISysReceiptAccountService receiptAccountService;
    @Autowired
    private IScOrderAccountService orderAccountService;
    @Autowired
    private IScStudentCourseService studentCourseService;
    @Autowired
    private IScStudentCourseOrderService courseOrderService;
    @Autowired
    private IScCourseService scCourseService;
    @Autowired
    private IScCourseClaService courseClaService;
    @Autowired
    private IScCourseChargeService courseChargeService;
    @Autowired
    private IScStudentService studentService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private IScStudentAccountService studentAccountService;
    @Autowired
    private ISysTagService tagService;
    @Autowired
    private ScOrderMapper orderMapper;
    @Autowired
    private ISysStaffService staffService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IScClaTimeAttendService claTimeAttendService;
    @Autowired
    private IScStudentCourseLogService scStudentCourseLogService;

    /**
     * 查询
     *
     * @param reqSearchScOrder
     * @return
     */
    public APIResponse searchList(ReqSearchScOrder reqSearchScOrder) {
        RespPage<RespOrder> page = new RespPage<>(reqSearchScOrder.getPageNum(), reqSearchScOrder.getPageSize());
        List<RespOrder> orderList = orderMapper.selectFroSearchTable(reqSearchScOrder, page);
        page.setRows(orderList);
        return APIResponse.toAPIResponse(page);
    }

    /**
     * 详情
     *
     * @param orderId
     * @return
     */
    public APIResponse detailById(Long orderId) {
        if (null == orderId) {
            return APIResponse.toAPIResponse(null);
        }
        Map<String, Object> detailMap = Maps.newHashMap();

        // 订单信息
        ScOrder order = scOrderService.getById(orderId);
        // 经办人姓名
        if (null != order.getCreateUser()) {
            SysUser user = userService.getById(order.getCreateUser());
            order.setHandleStaffName(user.getName());
        }
        detailMap.put("orderInfo", order);

        // 订单明细
        List<ScOrderDetail> orderDetail = orderDetailService.getByOrderDetail(orderId);
        detailMap.put("orderDetail", orderDetail);

        // 收款账户
        List<ScOrderAccount> orderAccountList = orderAccountService.getOrderAccountList(orderId);
        detailMap.put("orderAccountList", orderAccountList);

        return APIResponse.toAPIResponse(detailMap);
    }

    /**
     * 新办
     *
     * @param reqBusinessSignUp
     * @return
     */
    public APIResponse signUp(ReqBusinessSignUp reqBusinessSignUp) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        APIResponse checkParam = reqBusinessSignUp.checkParam();
        if (!checkParam.isSuccess()) {
            return checkParam;
        }
        Long studentId = reqBusinessSignUp.getStudentId();
        Long handleDepartId = reqBusinessSignUp.getHandleDepartId();

        ScStudent student = studentService.getById(studentId);
        if (null == student) {
            return APIResponse.toExceptionResponse("无法获取学生信息,请重试!");
        }
        SysDept handleDept = deptService.getById(handleDepartId);
        if (null == handleDept) {
            return APIResponse.toExceptionResponse("无法获取经办校区信息,请重试!");
        }

        BigDecimal reqReceiptWaySumMoney = reqBusinessSignUp.getReceiptWaySumMoney();
        List<ReqBusinessSignUpReceipt> signUpReceiptList = reqBusinessSignUp.getSignUpReceiptList();
        List<ReqBusinessSignUpItem> signUpItemList = reqBusinessSignUp.getSignUpItemList();
        for (ReqBusinessSignUpItem signUpItem : signUpItemList) {
            // 校验学生是否可报读 课程
            APIBaseResponse studentCanSignUpCourse = studentCourseService.checkStudentCanSignUpCourse(studentId, signUpItem.getCourseId(), signUpItem.getDepartId(), null);
            if (!studentCanSignUpCourse.isSuccess()) {
                return APIResponse.toExceptionResponse(studentCanSignUpCourse.getRespMsg());
            }
        }

        // 原价
        BigDecimal originalTotalFee = BigDecimal.ZERO;
        // 实际价格
        BigDecimal actualTotalFee = BigDecimal.ZERO;
        // 应收
        BigDecimal needReceiptFee = BigDecimal.ZERO;
        // 实收
        BigDecimal receiptWaySumMoney = BigDecimal.ZERO;

        // 计算实收
        for (ReqBusinessSignUpReceipt signUpReceipt : signUpReceiptList) {
            receiptWaySumMoney = receiptWaySumMoney.add(signUpReceipt.getReceiptMoney());
        }
        if (reqReceiptWaySumMoney.compareTo(receiptWaySumMoney) != 0) {
            return APIResponse.toExceptionResponse("实收金额不一致,请重试");
        }

        // 课程信息、收费模式缓存
        Map<Long, ScCourse> courseCacheMap = Maps.newHashMap();
        Map<Long, ScCourseCharge> chargeCacheMap = Maps.newHashMap();

        // 计算原价 实际价格 应收
        for (ReqBusinessSignUpItem signUpItem : signUpItemList) {
            Long courseId = signUpItem.getCourseId();
            Long chargeId = signUpItem.getChargeId();
            boolean openDiscount = signUpItem.isOpenDiscount();
            boolean openDiscountFee = signUpItem.isOpenDiscountFee();
            ScCourse scCourse = scCourseService.getById(courseId);
            ScCourseCharge courseCharge = courseChargeService.getById(chargeId);
            if (!signUpItem.getChargeType().equals(courseCharge.getChargeType())) {
                throw new BusinessException(scCourse.getCourseName() + "收费方式不一致,请重试");
            }

            courseCacheMap.put(courseId, scCourse);
            chargeCacheMap.put(chargeId, courseCharge);

            Integer buyCount = signUpItem.getBuyCount();
            BigDecimal chargeTotalFee = courseCharge.getTotalFee();
            BigDecimal courseOriginalTotalFee = chargeTotalFee.multiply(new BigDecimal(buyCount));
            BigDecimal courseActualTotalFee = courseOriginalTotalFee;

            // 折扣
            if (openDiscount) {
                // 实际价格= 实际价格 * 折扣(9.8/10)
                courseActualTotalFee = courseActualTotalFee.multiply(signUpItem.getDiscount()).divide(new BigDecimal(10));
            }
            // 优惠金额
            if (openDiscountFee) {
                courseActualTotalFee = courseActualTotalFee.subtract(signUpItem.getDiscountFee());
            }

            // 原价
            originalTotalFee = originalTotalFee.add(courseOriginalTotalFee);
            actualTotalFee = actualTotalFee.add(courseActualTotalFee);
        }
        // 实收 = 应收 - 余额支付
        needReceiptFee = actualTotalFee.subtract(reqBusinessSignUp.getBalancePayValue());
        if (needReceiptFee.compareTo(reqBusinessSignUp.getNeedReceiptFee()) != 0) {
            throw new BusinessException("应收金额不一致,请重试");
        }

        // 入 sc_order 表
        Long orderId = null;
        {
            ScOrder order = ScOrder.builder()
                    .studentId(studentId)
                    .studentName(student.getStudentName())
                    .phone(student.getPhone())
                    .orderType("1")
                    .originalTotalFee(originalTotalFee)
                    .actualTotalFee(actualTotalFee)
                    .receiptFee(receiptWaySumMoney)
                    .balanceFee(reqBusinessSignUp.getBalancePayValue())
                    .orderStatus(OrderStatusEnum.HAD_PAY.getOrderStatus())
                    .memo(reqBusinessSignUp.getMemo())
                    .handleDeptId(handleDept.getDeptId())
                    .handleDeptName(handleDept.getDeptName())
                    .handleDate(reqBusinessSignUp.getHandleDate())
                    .createUser(loginUser.getUserId())
                    .lastUpdateUser(loginUser.getUserId())
                    .build();

            if (null != reqBusinessSignUp.getOrderTag()) {
                order.setOrderTag(String.join(",", reqBusinessSignUp.getOrderTag()));
            }
            if (null != reqBusinessSignUp.getSaleSourceTag()) {
                order.setSaleSourceTag(String.join(",", reqBusinessSignUp.getSaleSourceTag()));
            }
            if (null != reqBusinessSignUp.getSaleStaffId()) {
                SysStaff saleStaff = staffService.getById(reqBusinessSignUp.getSaleStaffId());
                if (null == saleStaff) {
                    throw new BusinessException("无法获取销售员工信息");
                }
                order.setSaleStaffId(saleStaff.getStaffId());
                order.setSaleStaffName(saleStaff.getStaffName());
            }

            scOrderService.save(order);
            orderId = order.getOrderId();
            log.info("save order success,orderId={}", orderId);

            if (null != reqBusinessSignUp.getOrderTag()) {
                tagService.autoCreateTag(reqBusinessSignUp.getOrderTag(), "2", loginUser.getNowTenantId(), loginUser.getUserId());
            }
            if (null != reqBusinessSignUp.getSaleSourceTag()) {
                tagService.autoCreateTag(reqBusinessSignUp.getSaleSourceTag(), "1", loginUser.getNowTenantId(), loginUser.getUserId());
            }
        }

        // 入sc_order_account
        for (ReqBusinessSignUpReceipt signUpReceipt : signUpReceiptList) {
            SysReceiptAccount receiptAccount = receiptAccountService.getById(signUpReceipt.getAccountId());
            if (null == receiptAccount) {
                throw new BusinessException("无发获取收款账户,请核查后重试!");
            }
            ScOrderAccount orderAccount = new ScOrderAccount();
            orderAccount.setOrderId(orderId);
            orderAccount.setAccountId(signUpReceipt.getAccountId());
            orderAccount.setAccountName(receiptAccount.getAccountName());
            orderAccount.setFee(signUpReceipt.getReceiptMoney());
            orderAccountService.save(orderAccount);
        }

        // 报读课程
        for (ReqBusinessSignUpItem signUpItem : signUpItemList) {
            Long courseId = signUpItem.getCourseId();
            Long departId = signUpItem.getDepartId();
            Long claId = signUpItem.getClaId();
            Long chargeId = signUpItem.getChargeId();
            boolean openDiscount = signUpItem.isOpenDiscount();
            boolean openDiscountFee = signUpItem.isOpenDiscountFee();
            ScCourse scCourse = courseCacheMap.get(courseId);
            ScCourseCla scCourseCla = new ScCourseCla();
            if (null != claId) {
                scCourseCla = courseClaService.getById(claId);
                if (null == scCourseCla) {
                    throw new BusinessException("无法获取" + scCourse.getCourseName() + "对应的班级,请重试");
                }
            }
            ScCourseCharge courseCharge = chargeCacheMap.get(chargeId);
            if (!signUpItem.getChargeType().equals(courseCharge.getChargeType())) {
                throw new BusinessException(scCourse.getCourseName() + "收费方式不一致,请重试");
            }

            Integer buyCount = signUpItem.getBuyCount();
            BigDecimal chargeTotalFee = courseCharge.getTotalFee();
            BigDecimal courseOriginalTotalFee = chargeTotalFee.multiply(new BigDecimal(buyCount));
            BigDecimal courseActualTotalFee = courseOriginalTotalFee;

            // 折扣
            if (openDiscount) {
                // 实际价格= 实际价格 * 折扣(9.8/10)
                courseActualTotalFee = courseActualTotalFee.multiply(signUpItem.getDiscount()).divide(new BigDecimal(10));
            }
            // 优惠金额
            if (openDiscountFee) {
                courseActualTotalFee = courseActualTotalFee.subtract(signUpItem.getDiscountFee());
            }

            // 已报读本课程信息
            ScStudentCourse dbStudentCourse = studentCourseService.selectByStudentIdCourseId(studentId, courseId);

            BigDecimal addedHour = BigDecimal.ZERO;
            int addedDays = 0;

            if (CourseChargeTypeEnum.HOUR.getChargeType().equals(courseCharge.getChargeType())) {
                // 新增课时
                addedHour = courseCharge.getCount().multiply(new BigDecimal(buyCount));
            } else if (CourseChargeTypeEnum.DATE.getChargeType().equals(courseCharge.getChargeType())) {
                // 校验报读时间段是否重复
                boolean checkDateCover = courseOrderService.checkDateCover(studentId, courseId, signUpItem.getBeginDate(), signUpItem.getEndDate());
                if (checkDateCover) {
                    throw new BusinessException(scCourse.getCourseName() + "报读日期存在覆盖,请重新选择报读日期范围!");
                }
                // 新增天数
                DateTime beginDate = DateUtil.yyyMMddDayBegin(signUpItem.getBeginDate());
                DateTime endDate = DateUtil.yyyMMddDayBegin(signUpItem.getEndDate());
                Period period = new Period(beginDate, endDate, PeriodType.days());
                addedDays = period.getDays() + 1;
            } else if (CourseChargeTypeEnum.CYCLE.getChargeType().equals(courseCharge.getChargeType())) {
                // 新增课时
                addedHour = courseCharge.getCount().multiply(new BigDecimal(buyCount));
            }

            // 插入 sc_order_detail
            Long orderDetailId = null;
            {
                SysDept dept = deptService.getById(departId);
                ScOrderDetail orderDetail = ScOrderDetail.builder()
                        .orderId(orderId)
                        .courseId(courseId)
                        .courseName(scCourse.getCourseName())
                        .claId(claId)
                        .claName(scCourseCla.getClaName())
                        .deptId(dept.getDeptId())
                        .deptName(dept.getDeptName())
                        .detailTag(signUpItem.getDetailTag())
                        .chargeName(courseCharge.getChargeName())
                        .chargeType(courseCharge.getChargeType())
                        .chargeCount(courseCharge.getCount())
                        .chargeFee(courseCharge.getTotalFee())
                        .dateUnit(courseCharge.getDateUnit())
                        .buyCount(new BigDecimal(buyCount))
                        .originalFee(courseOriginalTotalFee)
                        .actualFee(courseActualTotalFee)
                        .insideMemo(signUpItem.getInsideMemo())
                        .outsideMemo(signUpItem.getOutsideMemo())
                        .orderDetailStatus(OrderStatusEnum.HAD_PAY.getOrderStatus())
                        .createUser(loginUser.getUserId())
                        .lastUpdateUser(loginUser.getUserId())
                        .build();
                if (openDiscount) {
                    orderDetail.setDirectDiscount(signUpItem.getDiscount());
                }
                if (openDiscountFee) {
                    orderDetail.setDirectReduceFee(signUpItem.getDiscountFee());
                }
                if (signUpItem.isOpenExpire()) {
                    orderDetail.setExpireDate(signUpItem.getExpireDate());
                }
                if (CourseChargeTypeEnum.DATE.getChargeType().equals(courseCharge.getChargeType())) {
                    orderDetail.setBeginDate(signUpItem.getBeginDate());
                    orderDetail.setEndDate(signUpItem.getEndDate());
                }
                orderDetailService.save(orderDetail);
                orderDetailId = orderDetail.getOrderDetailId();
            }

            // 插入/更新 sc_student_course表
            Long studentCourseId = null;
            {
                // db中包含
                if (null != dbStudentCourse) {
                    UpdateWrapper<ScStudentCourse> uw = new UpdateWrapper<>();
                    uw.eq("student_course_id", dbStudentCourse.getStudentCourseId());
                    uw.eq("total_fee", dbStudentCourse.getTotalFee());

                    if (CourseChargeTypeEnum.HOUR.getChargeType().equals(courseCharge.getChargeType())) {
                        uw.eq("balance_hour", dbStudentCourse.getBalanceHour());
                        uw.eq("total_hour", dbStudentCourse.getTotalHour());
                        uw.set("balance_hour", dbStudentCourse.getBalanceHour().add(addedHour));
                        uw.set("total_hour", dbStudentCourse.getTotalHour().add(addedHour));
                    } else if (CourseChargeTypeEnum.DATE.getChargeType().equals(courseCharge.getChargeType())) {
                        uw.eq("total_day", dbStudentCourse.getTotalDay());
                        uw.set("total_day", dbStudentCourse.getTotalDay().add(new BigDecimal(addedDays)));
                    } else if (CourseChargeTypeEnum.CYCLE.getChargeType().equals(courseCharge.getChargeType())) {
                        uw.eq("balance_hour", dbStudentCourse.getBalanceHour());
                        uw.eq("total_hour", dbStudentCourse.getTotalHour());
                        uw.set("balance_hour", dbStudentCourse.getBalanceHour().add(addedHour));
                        uw.set("total_hour", dbStudentCourse.getTotalHour().add(addedHour));
                    }
                    uw.set("total_fee", dbStudentCourse.getTotalFee().add(courseActualTotalFee));
                    uw.set("last_update_user", loginUser.getUserId());
                    uw.set("last_update_time", new Date());

                    boolean update = studentCourseService.update(uw);
                    if (!update) {
                        throw new BusinessException("学员新增课时失败,请稍后重试");
                    }
                    studentCourseId = dbStudentCourse.getStudentCourseId();
                } else {
                    ScStudentCourse addStudentCourse = new ScStudentCourse();
                    addStudentCourse.setStudentId(studentId);
                    addStudentCourse.setCourseId(courseId);
                    addStudentCourse.setCourseName(scCourse.getCourseName());
                    addStudentCourse.setDeptId(departId);
                    if (null != claId) {
                        addStudentCourse.setClaId(scCourseCla.getClaId());
                        addStudentCourse.setClaName(scCourseCla.getClaName());
                    }
                    addStudentCourse.setChargeType(courseCharge.getChargeType());
                    if (CourseChargeTypeEnum.HOUR.getChargeType().equals(courseCharge.getChargeType())) {
                        addStudentCourse.setTotalHour(addedHour);
                        addStudentCourse.setBalanceHour(addedHour);
                    } else if (CourseChargeTypeEnum.DATE.getChargeType().equals(courseCharge.getChargeType())) {
                        addStudentCourse.setTotalDay(new BigDecimal(addedDays));
                    } else if (CourseChargeTypeEnum.CYCLE.getChargeType().equals(courseCharge.getChargeType())) {
                        addStudentCourse.setTotalHour(addedHour);
                        addStudentCourse.setBalanceHour(addedHour);
                    }
                    addStudentCourse.setTotalFee(courseActualTotalFee);
                    addStudentCourse.setStatus("1");
                    addStudentCourse.setCreateUser(loginUser.getUserId());
                    addStudentCourse.setLastUpdateUser(loginUser.getUserId());
                    studentCourseService.save(addStudentCourse);
                    studentCourseId = addStudentCourse.getStudentCourseId();
                }
            }

            // 插入sc_student_course_order
            {
                ScStudentCourseOrder studentCourseOrder = ScStudentCourseOrder.builder()
                        .studentCourseId(studentCourseId)
                        .orderId(orderId)
                        .orderDetailId(orderDetailId)
                        .totalHour(addedHour)
                        .balanceHour(addedHour)
                        .totalDay(new BigDecimal(addedDays))
                        .totalFee(courseActualTotalFee)
                        .createUser(loginUser.getUserId())
                        .lastUpdateUser(loginUser.getUserId())
                        .build();
                if (signUpItem.isOpenExpire()) {
                    studentCourseOrder.setExpireDate(signUpItem.getExpireDate());
                }
                if (CourseChargeTypeEnum.DATE.getChargeType().equals(courseCharge.getChargeType())) {
                    studentCourseOrder.setBeginDate(signUpItem.getBeginDate());
                    studentCourseOrder.setEndDate(signUpItem.getEndDate());

                    // 单价 = 总价/天
                    studentCourseOrder.setUnitFee(courseActualTotalFee.divide(new BigDecimal(addedDays), 2, BigDecimal.ROUND_HALF_UP));
                } else {
                    // 单价 = 总价/课时数
                    studentCourseOrder.setUnitFee(courseActualTotalFee.divide(addedHour, 2, BigDecimal.ROUND_HALF_UP));
                }
                courseOrderService.save(studentCourseOrder);
            }

            // 学生报读 日志
            StringBuffer sb = new StringBuffer("");
            if ("1".equals(signUpItem.getDetailTag())) {
                sb.append("新报,");
            } else if ("2".equals(signUpItem.getDetailTag())) {
                sb.append("续报,");
            }
            sb.append("课程'").append(scCourse.getCourseName()).append("',");
            sb.append("金额").append(courseActualTotalFee.toString()).append("元,");
            ScStudentCourseLog studentCourseLog = ScStudentCourseLog.builder()
                    .studentId(studentId)
                    .logType(LogTypeEnum.PAY_FEE.getLogType())
                    .courseId(courseId)
                    .courseName(scCourse.getCourseName())
                    .claId(claId)
                    .claName(scCourseCla.getClaName())
                    .deptName(handleDept.getDeptName())
                    .changeFee(courseActualTotalFee)
                    .createUser(loginUser.getUserId())
                    .createUserName(loginUser.getName())
                    .createTime(new Date())
                    .build();
            if (CourseChargeTypeEnum.DATE.getChargeType().equals(courseCharge.getChargeType())) {
                sb.append("增加'").append(addedDays).append("'天");
                sb.append("(").append(signUpItem.getBeginDate()).append("~").append(signUpItem.getEndDate()).append(").");
            } else {
                sb.append("增加'").append(addedHour.toString()).append("'课时,");
                studentCourseLog.setChangeHour(addedHour);
                // 增加后剩余
                if (null != dbStudentCourse) {
                    studentCourseLog.setAfterBalanceHour(dbStudentCourse.getBalanceHour().add(addedHour));
                    sb.append("增加后剩余").append(dbStudentCourse.getBalanceHour().add(addedHour).toString()).append("'课时").append(".");
                } else {
                    studentCourseLog.setAfterBalanceHour(addedHour);
                    sb.append("增加后剩余").append(addedHour.toString()).append("'课时").append(".");
                }
            }
            studentCourseLog.setMemo(sb.toString());
            scStudentCourseLogService.save(studentCourseLog);
        }

        // 实收 > 应收， 增加 学生账户余额
        if (receiptWaySumMoney.compareTo(needReceiptFee) > 0) {
            BigDecimal addBalanceFee = receiptWaySumMoney.subtract(needReceiptFee);
            studentAccountService.addBalance(studentId, addBalanceFee, loginUser.getUserId());
        }

        return APIResponse.toOkResponse();
    }

    /**
     * 作废订单
     *
     * @param orderIds
     * @return
     */
    public APIResponse invalidById(Long[] orderIds) {
        if (null == orderIds || orderIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }

        LoginUser loginUser = LoginUserUtil.getLoginUser();

        Integer dealSuccessCnt = 0;
        Integer dealFailCnt = 0;
        StringBuffer errorSb = new StringBuffer();

        for (Long orderId : orderIds) {
            ScOrder order = scOrderService.getById(orderId);
            if (null == order) {
                errorSb.append("订单'").append(orderId).append("',");
                errorSb.append("无法获取订单信息,请稍后重试;");
                dealFailCnt++;
                continue;
            }
            if (OrderStatusEnum.INVALID.getOrderStatus().equals(order.getOrderStatus())) {
                errorSb.append("订单'").append(orderId).append("',");
                errorSb.append("该订单已作废,无法再次作废;");
                dealFailCnt++;
                continue;
            }

            // 所有未作废的订单明细
            List<ScOrderDetail> orderDetailList = orderDetailService.getByOrderDetail(orderId, new String[]{OrderStatusEnum.INVALID.getOrderStatus()});

            // 订单明细map
            Map<Long, ScOrderDetail> orderDetailMap = Maps.newHashMap();
            for (ScOrderDetail orderDetail : orderDetailList) {
                orderDetailMap.put(orderDetail.getOrderDetailId(), orderDetail);
            }

            // 所有关联的课程订单
            List<Long> orderDetailIdList = orderDetailList.stream().map(ScOrderDetail::getOrderDetailId).collect(Collectors.toList());
            QueryWrapper<ScStudentCourseOrder> qw = new QueryWrapper<>();
            qw.eq("order_id", orderId);
            qw.in("order_detail_id", orderDetailIdList);
            List<ScStudentCourseOrder> courseOrderList = courseOrderService.list(qw);

            // 校验 如果消耗课时 不允许作废
            for (ScStudentCourseOrder courseOrder : courseOrderList) {
                ScOrderDetail orderDetail = orderDetailMap.get(courseOrder.getOrderDetailId());
                if (CourseChargeTypeEnum.DATE.getChargeType().equals(orderDetail.getChargeType())) {
                    String beginDate = courseOrder.getBeginDate();
                    String endDate = courseOrder.getEndDate();
                    Long studentCourseId = courseOrder.getStudentCourseId();
                    int studentCourseAttendCount = claTimeAttendService.studentCourseAttendCount(studentCourseId, beginDate, endDate);
                    if (0 != studentCourseAttendCount) {
                        // 订单日期范围内 有上课记录,不允许作废
                        ScStudentCourse studentCourse = studentCourseService.getById(studentCourseId);
                        ScCourse scCourse = scCourseService.getById(studentCourse.getCourseId());
                        errorSb.append("订单'").append(order.getOrderId()).append("',");
                        errorSb.append(scCourse.getCourseName()).append(",在").append(beginDate).append("~").append(endDate).append(",有上课记录(").append(studentCourseAttendCount).append("次),无发作废;");
                        dealFailCnt++;
                        continue;
                    }
                } else {
                    if (null != courseOrder.getBalanceHour() && courseOrder.getBalanceHour().compareTo(courseOrder.getTotalHour()) < 0) {
                        // 按课时收费 并且 剩余课时<总课时  已消耗,不允许作废
                        ScStudentCourse studentCourse = studentCourseService.getById(courseOrder.getStudentCourseId());
                        ScCourse scCourse = scCourseService.getById(studentCourse.getCourseId());
                        errorSb.append("订单:").append(order.getOrderId()).append(",");
                        errorSb.append(scCourse.getCourseName()).append(",已消耗课时,无法作废;");
                        dealFailCnt++;
                        continue;
                    } else if (null != courseOrder.getBalanceHour() && courseOrder.getBalanceHour().compareTo(courseOrder.getTotalHour()) > 0) {
                        // 按课时收费 并且 剩余课时>总课时  删除上课记录 恢复课时后，剩余课时>总课时,不允许作废
                        ScStudentCourse studentCourse = studentCourseService.getById(courseOrder.getStudentCourseId());
                        ScCourse scCourse = scCourseService.getById(studentCourse.getCourseId());
                        errorSb.append("订单:").append(order.getOrderId()).append(",");
                        errorSb.append(scCourse.getCourseName()).append(",剩余课时大于总课时,无法作废");
                        dealFailCnt++;
                        continue;
                    }
                }
                dealSuccessCnt++;
            }

            if(dealFailCnt != 0) {
                return APIResponse.toExceptionResponse("操作失败,成功:" + dealSuccessCnt + ",失败:" + dealFailCnt + ",原因如下:" + errorSb.toString());
            }

            // 订单作废、订单明细作废
            scOrderService.invalidOrder(orderId);
            orderDetailService.invalidOrder(orderId, orderDetailIdList);

            // 课程订单 失效、 剩余课时、时间 作废
            for (ScStudentCourseOrder courseOrder : courseOrderList) {
                // 课程订单 失效
                UpdateWrapper<ScStudentCourseOrder> uw = new UpdateWrapper<>();
                uw.eq("course_order_id", courseOrder.getCourseOrderId());
                uw.set("valid", false);
                courseOrderService.update(uw);

                // 学生课程信息
                ScStudentCourse studentCourse = studentCourseService.getById(courseOrder.getStudentCourseId());

                ScOrderDetail orderDetail = orderDetailMap.get(courseOrder.getOrderDetailId());
                // 收费方式
                String chargeType = orderDetail.getChargeType();
                // 实际价格
                BigDecimal actualFee = orderDetail.getActualFee();

                Long studentCourseId = courseOrder.getStudentCourseId();
                BigDecimal totalDay = courseOrder.getTotalDay();
                BigDecimal totalHour = courseOrder.getTotalHour();

                // 学生日志
                StringBuffer sb = new StringBuffer("作废订单,");
                ScStudentCourseLog studentCourseLog = ScStudentCourseLog.builder()
                        .studentId(order.getStudentId())
                        .logType(LogTypeEnum.INVALID_ORDER.getLogType())
                        .courseId(orderDetail.getCourseId())
                        .courseName(orderDetail.getCourseName())
                        .claId(orderDetail.getClaId())
                        .claName(orderDetail.getClaName())
                        .deptName(orderDetail.getDeptName())
                        .changeFee(actualFee.negate())
                        .createUser(loginUser.getUserId())
                        .createUserName(loginUser.getName())
                        .createTime(new Date())
                        .build();

                if (CourseChargeTypeEnum.DATE.getChargeType().equals(chargeType)) {

                    // 总天数 = 总天数 - 作废天数
                    // 总学费 = 总学费 - 作废学费
                    UpdateWrapper<ScStudentCourse> uwStudentCourse = new UpdateWrapper<>();
                    uwStudentCourse
                            .eq("student_course_id", studentCourseId)
                            .eq("total_day", studentCourse.getTotalDay())
                            .eq("total_fee", studentCourse.getTotalFee())
                            .eq("charge_type", "date")
                            .set("total_day", studentCourse.getTotalDay().subtract(totalDay))
                            .set("total_fee", studentCourse.getTotalFee().subtract(actualFee))
                            .set("last_update_user", loginUser.getUserId())
                            .set("last_update_time", new Date());
                    boolean update = studentCourseService.update(uwStudentCourse);
                    if (!update) {
                        throw new BusinessException("学员报读恢复失败,请重试!");
                    }

                    // 当总天数、总费用为0时，删除报读
                    studentCourseService.deleteWhenTotalDayZeroForInvalid(studentCourseId);

                    sb.append("作废").append(totalDay.toString()).append("天,")
                            .append(actualFee.toString()).append("元");
                } else {

                    // 剩余课时 = 剩余课时 - 作废课时
                    // 总学费 = 总学费 - 作废学费
                    UpdateWrapper<ScStudentCourse> uwStudentCourse = new UpdateWrapper<>();
                    uwStudentCourse
                            .eq("student_course_id", studentCourseId)
                            .eq("total_hour", studentCourse.getTotalHour())
                            .eq("balance_hour", studentCourse.getBalanceHour())
                            .eq("total_fee", studentCourse.getTotalFee())
                            .ne("charge_type", "date")
                            .set("total_hour", studentCourse.getTotalHour().subtract(totalHour))
                            .set("balance_hour", studentCourse.getBalanceHour().subtract(totalHour))
                            .set("total_fee", studentCourse.getTotalFee().subtract(actualFee))
                            .set("last_update_user", loginUser.getUserId())
                            .set("last_update_time", new Date());
                    boolean update = studentCourseService.update(uwStudentCourse);
                    if (!update) {
                        throw new BusinessException("学员报读恢复失败,请重试!");
                    }

                    // 当总课时、总费用为0时，删除报读
                    studentCourseService.deleteWhenTotalHourZeroForInvalid(studentCourseId);

                    studentCourseLog.setChangeHour(totalHour.negate());
                    studentCourseLog.setAfterBalanceHour(studentCourse.getBalanceHour().subtract(totalHour));
                    sb.append("作废").append(totalHour.toString()).append("课时,")
                            .append(actualFee.toString()).append("元");
                }
                studentCourseLog.setMemo(sb.toString());
                scStudentCourseLogService.save(studentCourseLog);
            }
        }

        if (dealFailCnt == 0) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse("操作失败,成功:" + dealSuccessCnt + ",失败:" + dealFailCnt + ",原因如下:" + errorSb.toString());
        }


    }
}
