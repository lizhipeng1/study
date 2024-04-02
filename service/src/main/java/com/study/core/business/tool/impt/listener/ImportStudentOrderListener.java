package com.study.core.business.tool.impt.listener;

import cn.xluobo.business.sc.base.domain.req.ReqSchoolSelect;
import cn.xluobo.business.sc.base.domain.resp.RespSchoolSelect;
import cn.xluobo.business.sc.base.repo.model.ScSchool;
import cn.xluobo.business.sc.base.service.IScSchoolService;
import cn.xluobo.business.sc.course.domain.export.ExpCourse;
import cn.xluobo.business.sc.course.domain.req.ReqCourseClaSelect;
import cn.xluobo.business.sc.course.domain.req.ReqSearchScCourse;
import cn.xluobo.business.sc.course.domain.resp.RespCourseClaSelectInfo;
import cn.xluobo.business.sc.course.enums.ChargeDateUnitEnum;
import cn.xluobo.business.sc.course.enums.CourseChargeTypeEnum;
import cn.xluobo.business.sc.course.repo.mapper.ScCourseClaMapper;
import cn.xluobo.business.sc.course.repo.mapper.ScCourseMapper;
import cn.xluobo.business.sc.course.repo.model.ScCourseCharge;
import cn.xluobo.business.sc.course.service.IScCourseChargeService;
import cn.xluobo.business.sc.log.enums.LogTypeEnum;
import cn.xluobo.business.sc.log.repo.model.ScStudentCourseLog;
import cn.xluobo.business.sc.log.service.IScStudentCourseLogService;
import cn.xluobo.business.sc.order.enums.OrderStatusEnum;
import cn.xluobo.business.sc.order.repo.model.ScOrder;
import cn.xluobo.business.sc.order.repo.model.ScOrderAccount;
import cn.xluobo.business.sc.order.repo.model.ScOrderDetail;
import cn.xluobo.business.sc.order.service.IScOrderAccountService;
import cn.xluobo.business.sc.order.service.IScOrderDetailService;
import cn.xluobo.business.sc.order.service.IScOrderService;
import cn.xluobo.business.sc.student.repo.model.ScStudent;
import cn.xluobo.business.sc.student.repo.model.ScStudentContact;
import cn.xluobo.business.sc.student.repo.model.ScStudentCourse;
import cn.xluobo.business.sc.student.repo.model.ScStudentCourseOrder;
import cn.xluobo.business.sc.student.service.IScStudentContactService;
import cn.xluobo.business.sc.student.service.IScStudentCourseOrderService;
import cn.xluobo.business.sc.student.service.IScStudentCourseService;
import cn.xluobo.business.sc.student.service.IScStudentService;
import cn.xluobo.business.sys.admin.domain.resp.RespTreeSelect;
import cn.xluobo.business.sys.admin.repo.model.SysDictData;
import cn.xluobo.business.sys.admin.service.BusinessSysDeptService;
import cn.xluobo.business.sys.admin.service.BusinessSysDictDataService;
import cn.xluobo.business.sys.receipt.repo.model.SysReceiptAccount;
import cn.xluobo.business.sys.receipt.service.ISysReceiptAccountService;
import cn.xluobo.business.sys.staff.repo.model.SysStaff;
import cn.xluobo.business.sys.staff.service.ISysStaffService;
import cn.xluobo.business.tool.impt.domain.ImportStudentOrder;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.core.api.APIBaseResponse;
import cn.xluobo.core.page.RespPage;
import cn.xluobo.core.utils.DateUtil;
import cn.xluobo.utils.ContextUtils;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/12 17:12
 */
@Slf4j
@Data
public class ImportStudentOrderListener extends AnalysisEventListener<ImportStudentOrder> {

    private IScStudentService studentService;
    private IScOrderService orderService;
    private IScOrderDetailService orderDetailService;
    private IScSchoolService schoolService;
    private IScStudentContactService studentContactService;
    private IScStudentCourseService studentCourseService;
    private IScStudentCourseOrderService courseOrderService;
    private IScOrderAccountService orderAccountService;
    private BusinessSysDictDataService dictDataService;
    private BusinessSysDeptService deptService;
    private ScCourseMapper courseMapper;
    private ScCourseClaMapper claMapper;
    private ISysReceiptAccountService receiptAccountService;
    private ISysStaffService staffService;
    private IScCourseChargeService courseChargeService;
    private IScStudentCourseLogService scStudentCourseLogService;

    /**
     * 是否需要保存
     */
    private Boolean needSave;

    // key=value  value=contactRelationId
    private Map<String, String> contactRelationMap = Maps.newHashMap();
    // key=value  value=sexId
    private Map<String, String> sexMap = Maps.newHashMap();
    // key=schoolName  value=schoolId
    private Map<String, Long> schoolMap = Maps.newHashMap();
    // key=detailTagName  value=detailTagId
    private Map<String, String> orderDetailTagMap = Maps.newHashMap();
    // key=deptName  value=deptId
    private Map<String, String> campusMap = Maps.newHashMap();
    // key=courseChargeName  value=chargeId
    private Map<String, Long> courseChargeMap = Maps.newHashMap();
    private Map<String, RespCourseClaSelectInfo> claMap = Maps.newHashMap();
    // key=courseId  value=value
    private Map<Long, String> courseMap = Maps.newHashMap();
    // key=name  value=receiptAccountId
    private Map<String, Long> receiptAccountMap = Maps.newHashMap();
    // key=staffName  value=staffId
    private Map<String, Long> staffMap = Maps.newHashMap();
    private Map<Long, ScCourseCharge> chargeCacheMap = Maps.newHashMap();
    // key=studentName  value=studentId
    private Map<String, Long> studentMap = Maps.newHashMap();
    private boolean hadMoreStudent;

    // 登录用户id
    private String loginUserId = "";
    private LoginUser loginUser;

    // 导入id
    private Long importId;

    // 需保存的successRecordList
    private List<ImportStudentOrder> successRecordList = Lists.newArrayList();

    // 校验失败的列表
    private List<ImportStudentOrder> failRecordList = Lists.newArrayList();

    /**
     * 最多读取多少行
     */
    int maxRecord = 200;

    /**
     * 当前已读取
     */
    int hadReadRecord = 0;

    public ImportStudentOrderListener() {
        studentService = ContextUtils.getBean(IScStudentService.class);
        orderService = ContextUtils.getBean(IScOrderService.class);
        orderDetailService = ContextUtils.getBean(IScOrderDetailService.class);
        schoolService = ContextUtils.getBean(IScSchoolService.class);
        studentContactService = ContextUtils.getBean(IScStudentContactService.class);
        studentCourseService = ContextUtils.getBean(IScStudentCourseService.class);
        courseOrderService = ContextUtils.getBean(IScStudentCourseOrderService.class);
        orderAccountService = ContextUtils.getBean(IScOrderAccountService.class);
        dictDataService = ContextUtils.getBean(BusinessSysDictDataService.class);
        deptService = ContextUtils.getBean(BusinessSysDeptService.class);
        courseMapper = ContextUtils.getBean(ScCourseMapper.class);
        claMapper = ContextUtils.getBean(ScCourseClaMapper.class);
        receiptAccountService = ContextUtils.getBean(ISysReceiptAccountService.class);
        staffService = ContextUtils.getBean(ISysStaffService.class);
        courseChargeService = ContextUtils.getBean(IScCourseChargeService.class);
        scStudentCourseLogService = ContextUtils.getBean(IScStudentCourseLogService.class);
        this.initCacheMap();
    }

    @Override
    public void invoke(ImportStudentOrder data, AnalysisContext context) {
        APIBaseResponse checkParam = checkParam(data);
        if (!checkParam.isSuccess()) {
            data.setFailMsg(checkParam.getRespMsg());
            failRecordList.add(data);
            return;
        }
        successRecordList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (needSave) {
            saveStudentOrder();
        }
        log.info("doAfterAllAnalysed");
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        if (hadReadRecord++ < maxRecord) {
            return true;
        }
        return false;
    }

    /**
     * 保存班级信息
     */
    public void saveStudentOrder() {
        for (ImportStudentOrder studentOrder : successRecordList) {
            String studentName = studentOrder.getStudentName();
            Long studentId = studentMap.get(studentName);

            if (null == studentId && hadMoreStudent) {
                studentId = studentService.selectIdByName(studentName);
            }

            // 如无学生信息,新建学生信息
            if (null == studentId) {
                ScStudent student = new ScStudent();
                student.setStudentName(studentName);
                if (StringUtils.isNotEmpty(studentOrder.getSchoolName())) {
                    Long schoolId = schoolMap.get(studentOrder.getSchoolName());
                    if (null == schoolId) {
                        // 无学校信息 保存学校信息
                        ScSchool saveSchool = new ScSchool();
                        saveSchool.setSchoolName(studentOrder.getSchoolName());
                        saveSchool.setCreateUser(loginUserId);
                        schoolService.save(saveSchool);
                        schoolId = saveSchool.getSchoolId();
                        schoolMap.put(studentOrder.getSchoolName(), schoolId);
                    }
                    student.setSchoolId(schoolId);
                }
                if (StringUtils.isNotEmpty(studentOrder.getSex())) {
                    student.setSex(sexMap.get(studentOrder.getSex()));
                }
                student.setPhone(studentOrder.getContactPhone());
                if (StringUtils.isNotEmpty(studentOrder.getInTime())) {
                    student.setInTime(studentOrder.getInTime());
                }
                student.setCreateUser(loginUserId);
                studentService.save(student);
                studentId = student.getStudentId();

                // 保存联系人信息
                ScStudentContact studentContact = new ScStudentContact();
                String contactRelation = contactRelationMap.get(studentOrder.getContactRelation());
                studentContact.setContactRelation(contactRelation);
                studentContact.setContactPhone(studentOrder.getContactPhone());
                studentContact.setContactNick(studentOrder.getContactRelation());
                studentContact.setCreateUser(loginUserId);
                studentContact.setStudentId(student.getStudentId());
                studentContactService.save(studentContact);
            }

            // 订单信息
            BigDecimal receiptFee = new BigDecimal(studentOrder.getReceiptFee());
            BigDecimal actualFee = new BigDecimal(studentOrder.getActualFee());
            String handleDeptId = campusMap.get(studentOrder.getHandleDeptName());
            ScOrder order = ScOrder.builder()
                    .studentId(studentId)
                    .studentName(studentOrder.getStudentName())
                    .phone(studentOrder.getContactPhone())
                    .orderType("1")
                    .originalTotalFee(actualFee)
                    .actualTotalFee(actualFee)
                    .receiptFee(receiptFee)
                    .balanceFee(BigDecimal.ZERO)
                    .orderStatus(OrderStatusEnum.HAD_PAY.getOrderStatus())
                    .memo(studentOrder.getMemo())
                    .handleDeptId(Long.valueOf(handleDeptId))
                    .handleDeptName(studentOrder.getHandleDeptName())
                    .handleDate(studentOrder.getHandleDate())
                    .createUser(loginUserId)
                    .lastUpdateUser(loginUserId)
                    .build();
            if (StringUtils.isNotEmpty(studentOrder.getSaleStaffName())) {
                Long saleStaffId = staffMap.get(studentOrder.getSaleStaffName());
                order.setSaleStaffId(saleStaffId);
                order.setSaleStaffName(studentOrder.getSaleStaffName());
            }
            orderService.save(order);


            // 订单明细
            String deptId = campusMap.get(studentOrder.getDeptName());
            Long chargeId = courseChargeMap.get(studentOrder.getCourseName());
            ScCourseCharge courseCharge = chargeCacheMap.get(chargeId);
            RespCourseClaSelectInfo claInfo = claMap.get(studentOrder.getClaName());
            String detailTagId = orderDetailTagMap.get(studentOrder.getOrderDetailTag());
            String buyCount = studentOrder.getBuyCount();
            if (StringUtils.isEmpty(buyCount)) {
                buyCount = "1";
            }
            Integer buyCountInt = Integer.parseInt(buyCount);

            ScOrderDetail orderDetail = ScOrderDetail.builder()
                    .orderId(order.getOrderId())
                    .courseId(courseCharge.getCourseId())
                    .courseName("导入:" + courseMap.get(courseCharge.getCourseId()))
                    .deptId(Long.valueOf(deptId))
                    .deptName(studentOrder.getDeptName())
                    .detailTag(detailTagId)
                    .chargeName(courseCharge.getChargeName())
                    .chargeType(courseCharge.getChargeType())
                    .chargeCount(courseCharge.getCount())
                    .chargeFee(actualFee)
                    .dateUnit(courseCharge.getDateUnit())
                    .buyCount(new BigDecimal(buyCount))
                    .originalFee(actualFee)
                    .actualFee(receiptFee)
                    .insideMemo(null == studentOrder.getMemo() ? "导入" : "导入" + studentOrder.getMemo())
                    .outsideMemo("")
                    .orderDetailStatus(OrderStatusEnum.HAD_PAY.getOrderStatus())
                    .createUser(loginUserId)
                    .lastUpdateUser(loginUserId)
                    .build();
            if (StringUtils.isNotEmpty(studentOrder.getExpireDate())) {
                orderDetail.setExpireDate(studentOrder.getExpireDate());
            }
            if (null != claInfo) {
                orderDetail.setClaId(claInfo.getClaId());
                orderDetail.setClaName(claInfo.getClaName());
            }
            String beginDate = studentOrder.getBeginDate();
            DateTime beginDateTime = DateUtil.yyyMMddDayBegin(beginDate);
            DateTime endDateTime = DateUtil.yyyMMddDayEnd(beginDate);
            if (CourseChargeTypeEnum.DATE.getChargeType().equals(courseCharge.getChargeType())) {
                if (ChargeDateUnitEnum.DAY.getDateUnit().equals(courseCharge.getDateUnit())) {
                    endDateTime = beginDateTime.plusDays(buyCountInt).minusDays(1);
                } else if (ChargeDateUnitEnum.MONTH.getDateUnit().equals(courseCharge.getDateUnit())) {
                    endDateTime = beginDateTime.plusMonths(buyCountInt).minusDays(1);
                } else if (ChargeDateUnitEnum.SEASON.getDateUnit().equals(courseCharge.getDateUnit())) {
                    endDateTime = beginDateTime.plusMonths(buyCountInt * 3).minusDays(1);
                } else if (ChargeDateUnitEnum.YEAR.getDateUnit().equals(courseCharge.getDateUnit())) {
                    endDateTime = beginDateTime.plusYears(buyCountInt).minusDays(1);
                }
                orderDetail.setBeginDate(studentOrder.getBeginDate());
                orderDetail.setEndDate(endDateTime.toString("yyyy-MM-dd"));
            } else {
                // 收费方式按课时收费，实际课时数量
                orderDetail.setChargeCount(new BigDecimal(studentOrder.getBuyHour()));
            }
            orderDetailService.save(orderDetail);

            // 收款账户
            Long accountId = receiptAccountMap.get(studentOrder.getAccountName());
            ScOrderAccount orderAccount = new ScOrderAccount();
            orderAccount.setOrderId(order.getOrderId());
            orderAccount.setAccountId(accountId);
            orderAccount.setAccountName(studentOrder.getAccountName());
            orderAccount.setFee(receiptFee);
            orderAccountService.save(orderAccount);

            // sc_student_course表
            ScStudentCourse dbStudentCourse = studentCourseService.selectByStudentIdCourseId(studentId, courseCharge.getCourseId());
            BigDecimal buyHour = BigDecimal.ZERO;
            BigDecimal addedHour = BigDecimal.ZERO;
            int addedDays = 0;
            if (CourseChargeTypeEnum.DATE.getChargeType().equals(courseCharge.getChargeType())) {
                Period period = new Period(beginDateTime, endDateTime, PeriodType.days());
                addedDays = period.getDays() + 1;
            } else {
                // 新增课时
                buyHour = new BigDecimal(studentOrder.getBuyHour());
                addedHour = new BigDecimal(studentOrder.getBalanceHour());
            }
            Long studentCourseId = null;
            {
                // db中包含
                if (null != dbStudentCourse) {
                    ScStudentCourse updateStudentCourse = new ScStudentCourse();
                    updateStudentCourse.setStudentCourseId(dbStudentCourse.getStudentCourseId());
                    if (CourseChargeTypeEnum.DATE.getChargeType().equals(courseCharge.getChargeType())) {
                        updateStudentCourse.setTotalDay(dbStudentCourse.getTotalDay().add(new BigDecimal(addedDays)));
                    } else {
                        updateStudentCourse.setTotalHour(dbStudentCourse.getTotalHour().add(new BigDecimal(studentOrder.getBuyHour())));
                        updateStudentCourse.setBalanceHour(dbStudentCourse.getBalanceHour().add(addedHour));
                    }
                    updateStudentCourse.setTotalFee(dbStudentCourse.getTotalFee().add(receiptFee));
                    updateStudentCourse.setLastUpdateUser(loginUserId);
                    updateStudentCourse.setLastUpdateTime(new Date());
                    studentCourseService.updateById(updateStudentCourse);
                    studentCourseId = updateStudentCourse.getStudentCourseId();
                } else {
                    ScStudentCourse addStudentCourse = new ScStudentCourse();
                    addStudentCourse.setStudentId(studentId);
                    addStudentCourse.setCourseId(courseCharge.getCourseId());
                    addStudentCourse.setCourseName(courseMap.get(courseCharge.getCourseId()));
                    addStudentCourse.setDeptId(Long.valueOf(deptId));
                    if (null != claInfo) {
                        addStudentCourse.setClaId(claInfo.getClaId());
                        addStudentCourse.setClaName(claInfo.getClaName());
                    }
                    addStudentCourse.setChargeType(courseCharge.getChargeType());
                    if (CourseChargeTypeEnum.HOUR.getChargeType().equals(courseCharge.getChargeType())) {
                        addStudentCourse.setTotalHour(new BigDecimal(studentOrder.getBuyHour()));
                        addStudentCourse.setBalanceHour(addedHour);
                    } else if (CourseChargeTypeEnum.DATE.getChargeType().equals(courseCharge.getChargeType())) {
                        addStudentCourse.setTotalDay(new BigDecimal(addedDays));
                    } else if (CourseChargeTypeEnum.CYCLE.getChargeType().equals(courseCharge.getChargeType())) {
                        addStudentCourse.setTotalHour(new BigDecimal(studentOrder.getBuyHour()));
                        addStudentCourse.setBalanceHour(addedHour);
                    }
                    addStudentCourse.setTotalFee(receiptFee);
                    addStudentCourse.setStatus("1");
                    addStudentCourse.setCreateUser(loginUserId);
                    addStudentCourse.setLastUpdateUser(loginUserId);
                    studentCourseService.save(addStudentCourse);
                    studentCourseId = addStudentCourse.getStudentCourseId();
                }
            }

            // 插入sc_student_course_order
            {
                ScStudentCourseOrder studentCourseOrder = ScStudentCourseOrder.builder()
                        .studentCourseId(studentCourseId)
                        .orderId(order.getOrderId())
                        .orderDetailId(orderDetail.getOrderDetailId())
                        .totalFee(actualFee)
                        .createUser(loginUserId)
                        .lastUpdateUser(loginUserId)
                        .build();
                if (CourseChargeTypeEnum.DATE.getChargeType().equals(courseCharge.getChargeType())) {
                    studentCourseOrder.setTotalDay(new BigDecimal(addedDays));
                    studentCourseOrder.setBeginDate(beginDateTime.toString("yyyy-MM-dd"));
                    studentCourseOrder.setEndDate(endDateTime.toString("yyyy-MM-dd"));
                    if (StringUtils.isNotEmpty(studentOrder.getExpireDate())) {
                        studentCourseOrder.setExpireDate(studentOrder.getExpireDate());
                    }

                    // 单价 = 总价/天
                    studentCourseOrder.setUnitFee(actualFee.divide(new BigDecimal(addedDays), 2, BigDecimal.ROUND_HALF_UP));
                } else {
                    studentCourseOrder.setTotalHour(new BigDecimal(studentOrder.getBuyHour()));
                    studentCourseOrder.setBalanceHour(addedHour);

                    // 按课时收费 如果 学生 剩余课时<0，表示欠课时
                    if (null != dbStudentCourse && dbStudentCourse.getBalanceHour().compareTo(BigDecimal.ZERO) < 0) {
                        // 剩余课时 = 购买课时 - 欠课时
                        studentCourseOrder.setBalanceHour(addedHour.add(dbStudentCourse.getBalanceHour()));
                    }

                    // 单价 = 总价/课时数
                    studentCourseOrder.setUnitFee(actualFee.divide(buyHour, 2, BigDecimal.ROUND_HALF_UP));
                }
                courseOrderService.save(studentCourseOrder);
            }

            // 学生报读 日志
            StringBuffer sb = new StringBuffer("");
            if ("1".equals(detailTagId)) {
                sb.append("[导入]新报,");
            } else if ("2".equals(detailTagId)) {
                sb.append("[导入]续报,");
            }
            sb.append("课程'").append(courseMap.get(courseCharge.getCourseId())).append("',");
            sb.append("金额").append(receiptFee.toString()).append("元,");
            ScStudentCourseLog studentCourseLog = ScStudentCourseLog.builder()
                    .studentId(studentId)
                    .logType(LogTypeEnum.PAY_FEE.getLogType())
                    .courseId(courseCharge.getCourseId())
                    .courseName(courseMap.get(courseCharge.getCourseId()))
                    .deptName(studentOrder.getDeptName())
                    .changeFee(receiptFee)
                    .createUser(loginUser.getUserId())
                    .createUserName(loginUser.getName())
                    .createTime(new Date())
                    .build();
            if (null != claInfo) {
                studentCourseLog.setClaId(claInfo.getClaId());
                studentCourseLog.setClaName(claInfo.getClaName());
            }
            if (CourseChargeTypeEnum.DATE.getChargeType().equals(courseCharge.getChargeType())) {
                sb.append("增加'").append(addedDays).append("'天");
                sb.append("(").append(beginDateTime.toString("yyyy-MM-dd")).append("~").append(endDateTime.toString("yyyy-MM-dd")).append(").");
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
    }

    /**
     * 校验导入参数
     *
     * @param data
     * @return
     */
    public APIBaseResponse checkParam(ImportStudentOrder data) {
        if(receiptAccountMap.isEmpty()) {
            return APIBaseResponse.fail("系统未配置收款账户,请配置收款账户");
        }
        String studentName = data.getStudentName();
        if (StringUtils.isEmpty(data.getStudentName())) {
            return APIBaseResponse.fail("学生姓名未填写");
        } else if (studentName.length() > 20) {
            return APIBaseResponse.fail("学生姓名最多20个字符");
        } else if (StringUtils.isEmpty(data.getContactPhone())) {
            return APIBaseResponse.fail("联系电话未填写");
        } else if (data.getContactPhone().length() > 30) {
            return APIBaseResponse.fail("联系电话最多30个字符");
        } else if (StringUtils.isEmpty(data.getDeptName())) {
            return APIBaseResponse.fail("报读校区未填写");
        } else if (StringUtils.isEmpty(data.getCourseName())) {
            return APIBaseResponse.fail("报读课程未填写");
        } else if (StringUtils.isEmpty(data.getReceiptFee())) {
            return APIBaseResponse.fail("实缴学费未填写");
        } else if (StringUtils.isEmpty(data.getActualFee())) {
            return APIBaseResponse.fail("应收学费未填写");
        } else if (StringUtils.isEmpty(data.getHandleDeptName())) {
            return APIBaseResponse.fail("经办校区未填写");
        }

        try {
            new BigDecimal(data.getReceiptFee());
            new BigDecimal(data.getActualFee());
        } catch (Exception e) {
            return APIBaseResponse.fail("实缴、应收学费格式错误,请按照正确格式填写");
        }

        if (new BigDecimal(data.getReceiptFee()).compareTo(new BigDecimal(data.getActualFee())) > 0) {
            return APIBaseResponse.fail("实缴应大于应收,应小于等于应收");
        }

        Long chargeId = courseChargeMap.get(data.getCourseName());
        if (null == chargeId) {
            return APIBaseResponse.fail("根据课程无法获取对应信息,请核对填写信息或重新下载导入模板进行导入");
        }

        ScCourseCharge courseCharge = chargeCacheMap.get(chargeId);
        if (CourseChargeTypeEnum.DATE.getChargeType().equals(courseCharge.getChargeType())) {
            if (StringUtils.isEmpty(data.getBeginDate())) {
                return APIBaseResponse.fail("按时间收费,开始日期未填写");
            } else if (StringUtils.isEmpty(data.getBuyCount())) {
                return APIBaseResponse.fail("按时间收费,购买数量未填写");
            }
            try {
                DateUtil.yyyMMddDayBegin(data.getBeginDate());
            } catch (Exception e) {
                return APIBaseResponse.fail("按时间收费开始日期格式错误,请按照正确格式填写开始日期");
            }
            try {
                Integer.parseInt(data.getBuyCount());
                if (Integer.parseInt(data.getBuyCount()) == 0) {
                    return APIBaseResponse.fail("按时间收费购买数量不能为0");
                }
            } catch (Exception e) {
                return APIBaseResponse.fail("按时间收费购买数量格式错误,请按照正确格式填写购买数量");
            }
        } else {
            if (StringUtils.isEmpty(data.getBuyHour())) {
                return APIBaseResponse.fail("购买课时未填写");
            } else if (StringUtils.isEmpty(data.getBalanceHour())) {
                return APIBaseResponse.fail("剩余课时未填写");
            }

            try {
                new BigDecimal(data.getBuyHour());
                new BigDecimal(data.getBalanceHour());
            } catch (Exception e) {
                return APIBaseResponse.fail("购买课时、剩余课时格式错误,请按照正确格式填写");
            }

            if (new BigDecimal(data.getBuyHour()).compareTo(new BigDecimal(data.getBalanceHour())) < 0) {
                return APIBaseResponse.fail("购买课时小于剩余课时,请核对后重新填写");
            }
            if (new BigDecimal(data.getBuyHour()).compareTo(BigDecimal.ZERO) < 0) {
                return APIBaseResponse.fail("购买课时小于0");
            } else if (new BigDecimal(data.getBalanceHour()).compareTo(BigDecimal.ZERO) < 0) {
                return APIBaseResponse.fail("剩余课时小于0");
            }

            if (StringUtils.isNotEmpty(data.getExpireDate())) {
                try {
                    DateUtil.yyyMMddDayBegin(data.getExpireDate());
                } catch (Exception e) {
                    return APIBaseResponse.fail("按课时、周期收费，请按照正确格式填写课程到期日期");
                }
            }
        }


        if (StringUtils.isNotEmpty(data.getContactRelation())) {
            String contactRelationId = contactRelationMap.get(data.getContactRelation());
            if (StringUtils.isEmpty(contactRelationId)) {
                return APIBaseResponse.fail("根据主要联系人无法获取对应信息,请核对填写信息或重新下载导入模板进行导入");
            }
        } else {
            // 如未填，默认为其他
            data.setContactRelation("其他");
        }

        if (StringUtils.isNotEmpty(data.getSex())) {
            String sexId = sexMap.get(data.getSex());
            if (StringUtils.isEmpty(sexId)) {
                return APIBaseResponse.fail("根据性别无法获取对应信息,请核对填写信息或重新下载导入模板进行导入");
            }
        } else {
            // 如未填，默认为男
            data.setSex("男");
        }

        if (StringUtils.isNotEmpty(data.getOrderDetailTag())) {
            String detailTagId = orderDetailTagMap.get(data.getOrderDetailTag());
            if (StringUtils.isEmpty(detailTagId)) {
                return APIBaseResponse.fail("根据订单类型无法获取对应信息,请核对填写信息或重新下载导入模板进行导入");
            }
        } else {
            // 如未填，默认为新报
            data.setOrderDetailTag("新报");
        }

        String deptId = campusMap.get(data.getDeptName());
        if (StringUtils.isEmpty(deptId)) {
            return APIBaseResponse.fail("根据报读校区无法获取对应信息,请核对填写信息或重新下载导入模板进行导入");
        }

        if (courseCharge.getDepartId() != -1 && courseCharge.getDepartId().compareTo(Long.valueOf(deptId)) != 0) {
            return APIBaseResponse.fail("报读校区与报读课程不匹配，请核对后重新导入");
        }

        if (StringUtils.isNotEmpty(data.getClaName())) {
            RespCourseClaSelectInfo claInfo = claMap.get(data.getClaName());
            if (null == claInfo) {
                return APIBaseResponse.fail("根据报读班级无法获取对应信息,请核对填写信息或重新下载导入模板进行导入");
            } else if (claInfo.getCourseId().compareTo(courseCharge.getCourseId()) != 0) {
                return APIBaseResponse.fail("报读班级与报读课程不匹配,请核对后重新导入");
            } else if (claInfo.getDeptId().compareTo(Long.valueOf(deptId)) != 0) {
                return APIBaseResponse.fail("报读班级与报读课程不匹配,请核对后重新导入");
            }

        }

        String handleDeptId = campusMap.get(data.getHandleDeptName());
        if (StringUtils.isEmpty(handleDeptId)) {
            return APIBaseResponse.fail("根据经办校区无法获取对应信息,请核对填写信息或重新下载导入模板进行导入");
        }

        if (StringUtils.isNotEmpty(data.getHandleDate())) {
            try {
                DateUtil.yyyMMddDayBegin(data.getHandleDate());
            } catch (Exception e) {
                return APIBaseResponse.fail("请按照正确格式填写经办日期");
            }
        } else {
            data.setHandleDate(DateTime.now().toString("yyyy-MM-dd"));
        }

        if (StringUtils.isNotEmpty(data.getAccountName())) {
            Long accountId = receiptAccountMap.get(data.getAccountName());
            if (null == accountId) {
                return APIBaseResponse.fail("根据收款账户无法获取对应信息,请核对填写信息或重新下载导入模板进行导入");
            }
        } else if (!receiptAccountMap.isEmpty()) {
            String key = receiptAccountMap.keySet().iterator().next();
            // 如未填，默认第一个
            data.setAccountName(key);
        }

        if (StringUtils.isNotEmpty(data.getSaleStaffName())) {
            Long staffId = staffMap.get(data.getSaleStaffName());
            if (null == staffId) {
                return APIBaseResponse.fail("根据销售员工无法获取对应信息,请核对填写信息或重新下载导入模板进行导入");
            }
        }

        // 校验学生报读方式 是否与老报读方式一致
        Long studentId = studentMap.get(studentName);
        if (null == studentId && hadMoreStudent) {
            studentId = studentService.selectIdByName(studentName);
        }
        if (null != studentId) {
            ScStudentCourse studentCourse = studentCourseService.selectByStudentIdCourseId(studentId, courseCharge.getCourseId());
            if (null != studentCourse && !studentCourse.getChargeType().equals(courseCharge.getChargeType())) {
                return APIBaseResponse.fail("该学生 已" + CourseChargeTypeEnum.getChargeType(studentCourse.getChargeType()) + "报读,无法按" + courseCharge.getChargeType() + "重复报读!");
            }
        }


        // 按时间报读，报读日期是否存在覆盖
        if (CourseChargeTypeEnum.DATE.getChargeType().equals(courseCharge.getChargeType())) {
            String beginDate = data.getBeginDate();
            DateTime beginDateTime = DateUtil.yyyMMddDayBegin(beginDate);
            DateTime endDateTime = DateUtil.yyyMMddDayEnd(beginDate);
            if (ChargeDateUnitEnum.DAY.getDateUnit().equals(courseCharge.getDateUnit())) {
                endDateTime = beginDateTime.plusDays(Integer.parseInt(data.getBuyCount())).minusDays(1);
            } else if (ChargeDateUnitEnum.MONTH.getDateUnit().equals(courseCharge.getDateUnit())) {
                endDateTime = beginDateTime.plusMonths(Integer.parseInt(data.getBuyCount())).minusDays(1);
            } else if (ChargeDateUnitEnum.SEASON.getDateUnit().equals(courseCharge.getDateUnit())) {
                endDateTime = beginDateTime.plusMonths(Integer.parseInt(data.getBuyCount()) * 3).minusDays(1);
            } else if (ChargeDateUnitEnum.YEAR.getDateUnit().equals(courseCharge.getDateUnit())) {
                endDateTime = beginDateTime.plusYears(Integer.parseInt(data.getBuyCount())).minusDays(1);
            }
            boolean checkDateCover = courseOrderService.checkDateCover(studentId, courseCharge.getCourseId(), beginDateTime.toString("yyyy-MM-dd"), endDateTime.toString("yyyy-MM-dd"));
            if (checkDateCover) {
                return APIBaseResponse.fail("按时间收费,报读日期存在覆盖,请重新选择开始日期!");
            }
        }

        return APIBaseResponse.success();
    }

    public void initCacheMap() {
        // 联系人关系
        List<SysDictData> contactRelationList = dictDataService.dictTypeDataList("contact_relation");
        // 性别
        List<SysDictData> sexList = dictDataService.dictTypeDataList("sex");
        // 学校
        List<RespSchoolSelect> schoolList = schoolService.selectSchoolSelect(new ReqSchoolSelect());
        // 订单类型
        List<SysDictData> orderDetailTagList = dictDataService.dictTypeDataList("order_detail_tag");
        // 报读校区
        List<RespTreeSelect> campusList = deptService.campusList();
        // 报读课程 课程-收费方式(校区)
        ReqSearchScCourse reqSearchScCourse = new ReqSearchScCourse();
        reqSearchScCourse.setSale("1");
        List<ExpCourse> courseList = courseMapper.selectCourseForExport(reqSearchScCourse);
        // 报读班级 班级(教师)-课程
        List<RespCourseClaSelectInfo> claList = claMapper.selectForSelect(new ReqCourseClaSelect());
        // 收款账户
        List<SysReceiptAccount> receiptAccountList = receiptAccountService.select();
        // 销售员工
        List<SysStaff> staffList = staffService.staffList(null);
        // 收费模式
        QueryWrapper<ScCourseCharge> qw = new QueryWrapper<>();
        List<ScCourseCharge> courseChargeList = courseChargeService.list(qw);

        contactRelationList.forEach(item -> {
            contactRelationMap.put(item.getDictLabel(), item.getDictValue());
        });
        sexList.forEach(item -> {
            sexMap.put(item.getDictLabel(), item.getDictValue());
        });
        schoolList.forEach(item -> {
            schoolMap.put(item.getSchoolName(), item.getSchoolId());
        });
        orderDetailTagList.forEach(item -> {
            orderDetailTagMap.put(item.getDictLabel(), item.getDictValue());
        });
        campusList.forEach(item -> {
            campusMap.put(item.getLabel(), item.getId());
        });
        courseList.forEach(item -> {
            String courseName = item.getCourseName() + "(" + item.getDepartName() + ")-" + item.getChargeTypeName();
            if (CourseChargeTypeEnum.DATE.getChargeType().equals(item.getChargeType())) {
                courseName = courseName + "(" + item.getTotalFee().toString() + item.getDateUnitName() + ")";
            } else {
                courseName = courseName + "(" + item.getCount().toString() + "课时," + item.getTotalFee().toString() + "元)";
            }
            courseChargeMap.put(courseName, item.getChargeId());
        });
        courseList.forEach(item -> {
            courseMap.put(item.getCourseId(), item.getCourseName());
        });
        claList.forEach(item -> {
            String key = item.getDeptName() + "-" + item.getClaName() + "(" + item.getStaffName() + ")" + "-" + item.getCourseName();
            claMap.put(key, item);
        });
        receiptAccountList.forEach(item -> {
            receiptAccountMap.put(item.getAccountName(), item.getAccountId());
        });
        staffList.forEach(item -> {
            staffMap.put(item.getStaffName(), item.getStaffId());
        });
        courseChargeList.forEach(item -> {
            chargeCacheMap.put(item.getChargeId(), item);
        });

        RespPage<ScStudent> scStudentRespPage = studentService.selectStudentList(500);
        long pages = scStudentRespPage.getPages();
        hadMoreStudent = pages > 1;
        // 学生信息
        scStudentRespPage.getRows().forEach(item -> {
            studentMap.put(item.getStudentName(), item.getStudentId());
        });
    }

    public int getSuccessRecordSize() {
        return this.successRecordList.size();
    }

    public int getFailRecordSize() {
        return this.failRecordList.size();
    }
}
