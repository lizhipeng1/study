package com.study.business.sc.student.service;

import com.study.business.sc.base.repo.model.ScRoom;
import com.study.business.sc.base.service.IScRoomService;
import com.study.business.sc.course.enums.CourseChargeTypeEnum;
import com.study.business.sc.course.repo.enums.ClaTimeAttendStatusEnums;
import com.study.business.sc.course.repo.enums.ClaTimeSourceEnums;
import com.study.business.sc.course.repo.enums.ClaTimeStatusEnums;
import com.study.business.sc.course.repo.model.ScClaTime;
import com.study.business.sc.course.repo.model.ScClaTimeAttend;
import com.study.business.sc.course.repo.model.ScCourse;
import com.study.business.sc.course.repo.model.ScCourseCla;
import com.study.business.sc.course.service.IScClaTimeAttendService;
import com.study.business.sc.course.service.IScClaTimeService;
import com.study.business.sc.course.service.IScCourseClaService;
import com.study.business.sc.course.service.IScCourseService;
import com.study.business.sc.log.enums.LogTypeEnum;
import com.study.business.sc.log.repo.model.ScStudentCourseLog;
import com.study.business.sc.log.service.IScStudentCourseLogService;
import com.study.business.sc.student.domain.req.*;
import com.study.business.sc.student.domain.resp.RespBusinessStudentCourseInfo;
import com.study.business.sc.student.domain.req.ReqSearchStuCourseSignUp;
import com.study.business.sc.student.domain.req.ReqSearchStudentCourse;
import com.study.business.sc.student.domain.req.ReqSearchStudentCourseCla;
import com.study.business.sc.student.domain.req.ReqStudentCourseChooseCla;
import com.study.business.sc.student.domain.resp.RespCourseClaStudent;
import com.study.business.sc.student.domain.resp.RespStuCourseSignUpStudent;
import com.study.business.sc.student.domain.resp.RespStudentCourse;
import com.study.business.sc.student.enums.StudentCourseStatusEnum;
import com.study.business.sc.student.repo.mapper.ScStudentCourseMapper;
import com.study.business.sc.student.repo.model.ScStudent;
import com.study.business.sc.student.repo.model.ScStudentContact;
import com.study.business.sc.student.repo.model.ScStudentCourse;
import com.study.business.sc.student.repo.model.ScStudentCourseOrder;
import com.study.business.sys.admin.repo.model.SysDept;
import com.study.business.sys.admin.service.ISysDeptService;
import com.study.business.sys.staff.repo.model.SysStaff;
import com.study.business.sys.staff.service.ISysStaffService;
import com.study.config.exception.BusinessException;
import com.study.config.login.LoginUser;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.core.page.RespPage;
import com.study.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/2 15:06
 */
@Service
@Transactional
public class BusinessScStudentCourseService {

    @Autowired
    private ScStudentCourseMapper studentCourseMapper;
    @Autowired
    private IScStudentService studentService;
    @Autowired
    private IScStudentContactService contactService;
    @Autowired
    private IScCourseClaService claService;
    @Autowired
    private IScCourseService courseService;
    @Autowired
    private IScStudentCourseService studentCourseService;
    @Autowired
    private IScClaTimeService claTimeService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private IScRoomService roomService;
    @Autowired
    private IScClaTimeAttendService attendService;
    @Autowired
    private ISysStaffService staffService;
    @Autowired
    private IScStudentCourseOrderService courseOrderService;
    @Autowired
    private IScStudentCourseLogService scStudentCourseLogService;

    /**
     * 学生课程信息
     *
     * @param studentId
     * @return
     */
    public APIResponse studentCourseInfo(Long studentId) {
        if (null == studentId) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }
        ScStudent student = studentService.getById(studentId);
        // 联系人信息
        List<ScStudentContact> contactList = contactService.getStudentContactList(studentId);
        List<String> contactPhoneList = contactList.stream().map(item -> item.getContactPhone() + "(" + item.getContactNick() + ")").collect(Collectors.toList());
        // 报读课程
        List<String> courseNameList = studentCourseMapper.selectStudentCourseNameList(studentId);
        // 报读校区
        List<String> deptNameList = studentCourseMapper.selectStudentDeptNameList(studentId);
        RespBusinessStudentCourseInfo studentCourseInfo = RespBusinessStudentCourseInfo.builder()
                .contactPhone(student.getPhone())
                .contactPhoneDetail(String.join(",", contactPhoneList))
                .courseNames(String.join(",", courseNameList))
                .deptNames(String.join(",", deptNameList))
                .build();
        return APIResponse.toAPIResponse(studentCourseInfo);
    }

    /**
     * 课程、班级 学生列表
     *
     * @param reqSearchStudentCourseCla
     * @return
     */
    public RespPage<RespCourseClaStudent> searchCourseClaStudent(ReqSearchStudentCourseCla reqSearchStudentCourseCla) {
        RespPage<RespCourseClaStudent> page = new RespPage<>(reqSearchStudentCourseCla.getPageNum(), reqSearchStudentCourseCla.getPageSize());
        List<RespCourseClaStudent> claStudentList = studentCourseMapper.selectStudentList(reqSearchStudentCourseCla, page);
        page.setRows(claStudentList);
        return page;
    }

    /**
     * 已报名未选择班级 选择班级
     *
     * @param studentCourseChooseCla
     * @return
     */
    public APIResponse studentCourseChooseCla(ReqStudentCourseChooseCla studentCourseChooseCla) {
        if (!studentCourseChooseCla.checkParam()) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }
        Long courseId = studentCourseChooseCla.getCourseId();
        Long claId = studentCourseChooseCla.getClaId();
        Long[] studentCourseIds = studentCourseChooseCla.getChooseStudentCourseIds();
        if (null == studentCourseIds || studentCourseIds.length == 0) {
            return APIResponse.toExceptionResponse("未选择学生,请稍后重试");
        }
        ScCourseCla courseCla = claService.getById(claId);
        if (null == courseCla) {
            return APIResponse.toExceptionResponse("无法获取班级,请稍后重试");
        }

        // 更新
        UpdateWrapper<ScStudentCourse> uw = new UpdateWrapper<>();
        uw.set("cla_id", claId);
        uw.set("cla_name", courseCla.getClaName());

        uw.in("student_course_id", studentCourseIds);
        uw.isNull("cla_id");
        uw.eq("course_id", courseId);
        studentCourseService.update(uw);


        // 日志
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        SysDept sysDept = deptService.getById(courseCla.getDepartId());

        List<ScStudentCourseLog> courseLogList = Lists.newArrayList();
        for (Long studentCourseId : studentCourseIds) {
            ScStudentCourse studentCourse = studentCourseService.getById(studentCourseId);
            ScStudentCourseLog studentCourseLog = ScStudentCourseLog.builder()
                    .studentId(studentCourse.getStudentId())
                    .logType(LogTypeEnum.IN_CLA.getLogType())
                    .courseId(studentCourse.getCourseId())
                    .courseName(studentCourse.getCourseName())
                    .claId(courseCla.getClaId())
                    .claName(courseCla.getClaName())
                    .deptName(sysDept.getDeptName())
                    .memo("进入班级,进入班级'" + studentCourse.getClaName() + "'")
                    .createUser(loginUser.getUserId())
                    .createUserName(loginUser.getName())
                    .build();
            courseLogList.add(studentCourseLog);
        }
        scStudentCourseLogService.saveBatch(courseLogList);

        return APIResponse.toOkResponse();
    }

    /**
     * 记上课
     *
     * @param reqClaTimeAttend
     * @return
     */
    public APIResponse claTimeAttend(ReqClaTimeAttend reqClaTimeAttend) {
        APIResponse checkParam = reqClaTimeAttend.checkParam();
        if (!checkParam.isSuccess()) {
            return checkParam;
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        String attendType = reqClaTimeAttend.getAttendType();
        String startTime = reqClaTimeAttend.getStartTime();
        String endTime = reqClaTimeAttend.getEndTime();
        Long teacherId = reqClaTimeAttend.getTeacherId();
        Long courseTimeId = reqClaTimeAttend.getCourseTimeId();
        Long claId = reqClaTimeAttend.getClaId();
        ScCourseCla dbCla = claService.getById(claId);
        if (null == dbCla) {
            return APIResponse.toExceptionResponse("无法获取班级信息,请稍后重试");
        }
        Long courseId = dbCla.getCourseId();
        ScCourse scCourse = courseService.getById(courseId);
        if (null == scCourse) {
            return APIResponse.toExceptionResponse("无法获取班级信息,请稍后重试");
        }

        Long departId = dbCla.getDepartId();
        SysDept sysDept = deptService.getById(departId);
        if (null == sysDept) {
            return APIResponse.toExceptionResponse("无法获取校区信息,请稍后重试");
        }

        List<ReqClaTimeAttendItem> studentAttendList = reqClaTimeAttend.getStudentAttendList();

        // 总消耗课时金额
        BigDecimal totalPayFee = BigDecimal.ZERO;
        // 可课时消耗
        BigDecimal totalPayHour = BigDecimal.ZERO;
        // 实到人数
        Long realAttendCnt = studentAttendList.stream().filter(item -> item.getAttendStatus().equals(ClaTimeAttendStatusEnums.AT_CLASS.getAttendStatus())).count();
        // 请假人数
        Long leaveCnt = studentAttendList.stream().filter(item -> item.getAttendStatus().equals(ClaTimeAttendStatusEnums.LEAVE_CLASS.getAttendStatus())).count();
        // 旷课人数
        Long outCnt = studentAttendList.stream().filter(item -> item.getAttendStatus().equals(ClaTimeAttendStatusEnums.OUT_CLASS.getAttendStatus())).count();
        // 应到人数
        int needAttendCnt = studentCourseMapper.selectClaNeedAttendCnt(dbCla.getClaId());


        if ("rule".equals(attendType)) {
            // 排课记上课

            ScClaTime claTime = claTimeService.getById(courseTimeId);
            if (!ClaTimeStatusEnums.WAIT_CLASS.getStatus().equals(claTime.getStatus())) {
                return APIResponse.toExceptionResponse("选择计划上课日期,非待上课状态,无法重复上课!");
            }
            teacherId = claTime.getTeacherId();
            // 变更状态为已上课
            ScClaTime updateClaTime = new ScClaTime();
            updateClaTime.setCourseTimeId(courseTimeId);
            updateClaTime.setRealClaDate(claTime.getClaDate());
            updateClaTime.setRealStartTime(startTime);
            updateClaTime.setRealEndTime(endTime);
            updateClaTime.setStatus(ClaTimeStatusEnums.HAD_CLASS.getStatus());
            updateClaTime.setNeedAttendCnt(needAttendCnt);
            updateClaTime.setRealAttendCnt(realAttendCnt.intValue());
            updateClaTime.setMemo(reqClaTimeAttend.getMemo());
            updateClaTime.setLastUpdateUser(loginUser.getUserId());
            updateClaTime.setLastUpdateTime(new Date());
            claTimeService.updateById(updateClaTime);
        } else if ("change".equals(attendType)) {
            // 变更上课记录

            ScClaTime claTime = claTimeService.getById(courseTimeId);
            if (!ClaTimeStatusEnums.WAIT_CLASS.getStatus().equals(claTime.getStatus())) {
                return APIResponse.toExceptionResponse("选择计划上课日期,非待上课状态,无法重复上课!");
            }
            // 变更状态为已上课
            ScClaTime updateClaTime = new ScClaTime();
            updateClaTime.setCourseTimeId(courseTimeId);
            updateClaTime.setRealClaDate(reqClaTimeAttend.getClaDate());
            updateClaTime.setTeacherId(reqClaTimeAttend.getTeacherId());
            updateClaTime.setClassTheme(reqClaTimeAttend.getClassTheme());
            updateClaTime.setRealStartTime(startTime);
            updateClaTime.setRealEndTime(endTime);
            updateClaTime.setStatus(ClaTimeStatusEnums.HAD_CLASS.getStatus());
            updateClaTime.setNeedAttendCnt(needAttendCnt);
            updateClaTime.setRealAttendCnt(realAttendCnt.intValue());
            updateClaTime.setMemo(reqClaTimeAttend.getMemo());
            updateClaTime.setLastUpdateUser(loginUser.getUserId());
            updateClaTime.setLastUpdateTime(new Date());
            claTimeService.updateById(updateClaTime);
        } else if ("custom".equals(attendType)) {
            // 记录自定义上课信息

            ScClaTime addClaTime = new ScClaTime();
            addClaTime.setClaId(reqClaTimeAttend.getClaId());
            addClaTime.setClaDate(reqClaTimeAttend.getClaDate());
            addClaTime.setTeacherId(reqClaTimeAttend.getTeacherId());
            addClaTime.setClassTheme(reqClaTimeAttend.getClassTheme());
            addClaTime.setStartTime(reqClaTimeAttend.getStartTime());
            addClaTime.setEndTime(reqClaTimeAttend.getEndTime());
            addClaTime.setRealClaDate(reqClaTimeAttend.getClaDate());
            addClaTime.setRealStartTime(reqClaTimeAttend.getStartTime());
            addClaTime.setRealEndTime(reqClaTimeAttend.getEndTime());
            addClaTime.setSource(ClaTimeSourceEnums.UN_PLAN_CLA_TIME.getSource());
            addClaTime.setStatus(ClaTimeStatusEnums.HAD_CLASS.getStatus());
            addClaTime.setPayHour(dbCla.getEveryStuLoseHour());
            addClaTime.setNeedAttendCnt(needAttendCnt);
            addClaTime.setRealAttendCnt(realAttendCnt.intValue());
            addClaTime.setMemo(reqClaTimeAttend.getMemo());
            addClaTime.setCreateUser(loginUser.getUserId());
            addClaTime.setCreateTime(new Date());
            addClaTime.setLastUpdateUser(loginUser.getUserId());
            addClaTime.setLastUpdateTime(new Date());

            // 教室不为空 设置教室
            if (null != reqClaTimeAttend.getRoomId()) {
                ScRoom room = roomService.getById(reqClaTimeAttend.getRoomId());
                addClaTime.setRoomId(reqClaTimeAttend.getRoomId());
                addClaTime.setRoomName(room.getRoomName());
            }

            claTimeService.save(addClaTime);
            courseTimeId = addClaTime.getCourseTimeId();
        }

        // 教师信息
        SysStaff sysStaff = staffService.getById(teacherId);

        // 保存上课记录
        List<ScClaTimeAttend> saveAttendList = Lists.newArrayList();
        // 保存学生日志
        List<ScStudentCourseLog> studentCourseLogList = Lists.newArrayList();

        for (ReqClaTimeAttendItem attendItem : studentAttendList) {
            Long studentCourseId = attendItem.getStudentCourseId();
            String attendStatus = attendItem.getAttendStatus();
            String memo = attendItem.getMemo();
            BigDecimal stuLoseHour = attendItem.getStuLoseHour();

            // 如果是请假,消耗课时0
            if (ClaTimeAttendStatusEnums.LEAVE_CLASS.getAttendStatus().equals(attendStatus)) {
                stuLoseHour = BigDecimal.ZERO;
            }

            // 获取学生报读班级信息
            ScStudentCourse studentCourse = studentCourseService.getById(studentCourseId);
            String studentDbChargeType = studentCourse.getChargeType();

            // 消耗金额
            BigDecimal payFee;
            // 上课后剩余课时
            BigDecimal changeAfterBalanceHour = null;
            // 本次上课对应的订单
            Long courseOrderId = null;

            if (CourseChargeTypeEnum.DATE.getChargeType().equals(studentDbChargeType)) {
                // 按时间上课，不扣减课时

                // 获取 消耗金额
                ScStudentCourseOrder courseOrder = courseOrderService.getNowValidDateOrder(studentCourseId);
                payFee = courseOrder.getUnitFee();
                courseOrderId = courseOrder.getCourseOrderId();

            } else {
                // 本次上课对应的订单
                ScStudentCourseOrder courseOrder = courseOrderService.getSubtractHourOrder(studentCourseId);
                if (null == courseOrder) {
                    throw new BusinessException("无法获取对应课时订单,无法扣减课时,id=" + studentCourseId);
                }
                courseOrderId = courseOrder.getCourseOrderId();

                BigDecimal balanceHour = studentCourse.getBalanceHour();
                balanceHour = balanceHour.subtract(stuLoseHour);

                changeAfterBalanceHour = balanceHour;

                // 课时不足 不允许上课
                if (balanceHour.compareTo(BigDecimal.ZERO) < 0) {
                    throw new BusinessException("学员课时不足,不允许上课!");
                }

                // 更新剩余课时 cas
                UpdateWrapper<ScStudentCourse> uwStudentCourse = new UpdateWrapper<>();
                uwStudentCourse
                        .eq("student_course_id", studentCourseId)
                        .eq("balance_hour", studentCourse.getBalanceHour())
                        .set("balance_hour", balanceHour)
                        .set("last_update_user", loginUser.getUserId())
                        .set("last_update_time", new Date());
                boolean update = studentCourseService.update(uwStudentCourse);
                if (!update) {
                    throw new BusinessException("学员课时余额扣减失败,请重试!");
                }

                // 订单扣减课时，获取 消耗金额
                payFee = courseOrderService.subtractCourseOrderBalanceHour(studentCourseId, stuLoseHour);
            }

            // 消耗总金额
            totalPayFee = totalPayFee.add(payFee);

            // 每个上课记录
            ScClaTimeAttend addClaTimeAttend = new ScClaTimeAttend();
            addClaTimeAttend.setStudentCourseId(studentCourseId);
            addClaTimeAttend.setCourseOrderId(courseOrderId);
            addClaTimeAttend.setCourseTimeId(courseTimeId);
            addClaTimeAttend.setStudentId(studentCourse.getStudentId());
            addClaTimeAttend.setClaId(studentCourse.getClaId());
            addClaTimeAttend.setCourseId(studentCourse.getCourseId());
            addClaTimeAttend.setTeacherId(teacherId);
            addClaTimeAttend.setTeacherName(sysStaff.getStaffName());
            addClaTimeAttend.setChargeType(studentDbChargeType);
            if (CourseChargeTypeEnum.DATE.getChargeType().equals(studentDbChargeType)) {
                addClaTimeAttend.setTeacherGetHour(null);
                addClaTimeAttend.setPayHour(null);
            } else {
                addClaTimeAttend.setTeacherGetHour(stuLoseHour);
                addClaTimeAttend.setPayHour(stuLoseHour);
                totalPayHour = totalPayHour.add(stuLoseHour);
            }
            addClaTimeAttend.setAttendStatus(attendStatus);
            addClaTimeAttend.setMemo(memo);
            addClaTimeAttend.setCreateUser(loginUser.getUserId());
            addClaTimeAttend.setPayFee(payFee);
            saveAttendList.add(addClaTimeAttend);

            // 上课日志
            StringBuffer sb = new StringBuffer("");
            sb.append("上课[").append(ClaTimeAttendStatusEnums.getNameByStatus(attendStatus)).append("],上课时间").append(reqClaTimeAttend.getClaDate()).append(" ")
                    .append(reqClaTimeAttend.getStartTime()).append("~").append(reqClaTimeAttend.getEndTime()).append(",");
            ScStudentCourseLog studentCourseLog = ScStudentCourseLog.builder()
                    .studentId(studentCourse.getStudentId())
                    .logType(LogTypeEnum.ATTEND_CLA.getLogType())
                    .courseId(scCourse.getCourseId())
                    .courseName(scCourse.getCourseName())
                    .claId(dbCla.getClaId())
                    .claName(dbCla.getClaName())
                    .deptName(sysDept.getDeptName())
                    .changeFee(payFee.negate())
                    .createUser(loginUser.getUserId())
                    .createUserName(loginUser.getName())
                    .createTime(new Date())
                    .build();
            if (!CourseChargeTypeEnum.DATE.getChargeType().equals(studentDbChargeType)) {
                studentCourseLog.setChangeHour(stuLoseHour.negate());
                studentCourseLog.setAfterBalanceHour(changeAfterBalanceHour);
                sb.append("扣减").append(stuLoseHour.toString()).append("课时,");
                sb.append("消耗").append(payFee).append("元.");
            }
            studentCourseLog.setMemo(sb.toString());
            studentCourseLogList.add(studentCourseLog);
        }
        // 保存上课记录
        attendService.saveBatch(saveAttendList);
        // 保存学生日志
        scStudentCourseLogService.saveBatch(studentCourseLogList);

        // 更新上课总课时 人数信息
        ScClaTime updateClaTime = new ScClaTime();
        updateClaTime.setCourseTimeId(courseTimeId);
        updateClaTime.setPayTotalHour(totalPayHour);
        updateClaTime.setPayTotalFee(totalPayFee);
        updateClaTime.setAtClassCnt(realAttendCnt.intValue());
        updateClaTime.setLeaveCnt(leaveCnt.intValue());
        updateClaTime.setOutCnt(outCnt.intValue());
        claTimeService.updateById(updateClaTime);

        return APIResponse.toOkResponse();
    }

    /**
     * 停课
     *
     * @param studentCourseId
     * @return
     */
    public APIResponse stopStudentCourseStatus(Long studentCourseId) {
        if (null == studentCourseId) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }
        ScStudentCourse studentCourse = studentCourseService.getById(studentCourseId);
        if (null == studentCourse) {
            return APIResponse.toExceptionResponse("无法获取停课信息,请稍后重试!");
        }
        if (StudentCourseStatusEnum.STOP_CLA.getStudentCourseStatus().equals(studentCourse.getStatus())) {
            return APIResponse.toExceptionResponse("当前状态为停课,无需停课!");
        }
        ScStudentCourse update = new ScStudentCourse();
        update.setStudentCourseId(studentCourseId);
        update.setStatus(StudentCourseStatusEnum.STOP_CLA.getStudentCourseStatus());
        studentCourseService.updateById(update);
        return APIResponse.toOkResponse();
    }

    /**
     * 在读
     *
     * @param studentCourseId
     * @return
     */
    public APIResponse atClaStudentCourseStatus(Long studentCourseId) {
        if (null == studentCourseId) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }
        ScStudentCourse studentCourse = studentCourseService.getById(studentCourseId);
        if (null == studentCourse) {
            return APIResponse.toExceptionResponse("无法获取课程信息,请稍后重试!");
        }
        if (StudentCourseStatusEnum.AT_CLA.getStudentCourseStatus().equals(studentCourse.getStatus())) {
            return APIResponse.toExceptionResponse("当前状态为在读,无需变更!");
        }
        ScStudentCourse update = new ScStudentCourse();
        update.setStudentCourseId(studentCourseId);
        update.setStatus(StudentCourseStatusEnum.AT_CLA.getStudentCourseStatus());
        studentCourseService.updateById(update);
        return APIResponse.toOkResponse();
    }

    /**
     * 学生报读课程
     *
     * @return
     */
    public List<RespStudentCourse> searchStudentCourse(ReqSearchStudentCourse reqSearchStudentCourse) {
        return studentCourseMapper.selectStudentCourseList(reqSearchStudentCourse);
    }


    /**
     * 报读列表
     *
     * @param reqSearchStuCourseSignUp
     * @return
     */
    public RespPage<RespStuCourseSignUpStudent> searchStuCourseSignUpList(ReqSearchStuCourseSignUp reqSearchStuCourseSignUp) {
        RespPage<RespStuCourseSignUpStudent> page = new RespPage<>(reqSearchStuCourseSignUp.getPageNum(), reqSearchStuCourseSignUp.getPageSize());
        List<RespStuCourseSignUpStudent> signUpStudents = studentCourseMapper.selectStudentSignUpList(reqSearchStuCourseSignUp, page);
        page.setRows(signUpStudents);
        return page;
    }

    /**
     * 报读信息中 将学员从班级中移除
     *
     * @param studentCourseId
     * @return
     */
    public APIResponse removeStuFromCla(Long studentCourseId) {
        if (null == studentCourseId) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }
        ScStudentCourse studentCourse = studentCourseService.getById(studentCourseId);
        if (null == studentCourse) {
            return APIResponse.toExceptionResponse("无法获取班级学员信息,请稍后重试");
        }
        Long claId = studentCourse.getClaId();
        if (null == claId) {
            return APIResponse.toExceptionResponse("未报读班级,无需移除");
        }

        LoginUser loginUser = LoginUserUtil.getLoginUser();
        SysDept sysDept = deptService.getById(studentCourse.getDeptId());

        UpdateWrapper<ScStudentCourse> uw = new UpdateWrapper<>();
        uw.eq("student_course_id", studentCourse.getStudentCourseId());
        uw.isNotNull("cla_id");
        uw.set("cla_id", null);
        uw.set("cla_name", null);
        uw.set("last_update_user", loginUser.getUserId());
        uw.set("last_update_time", new Date());
        boolean update = studentCourseService.update(uw);
        if (!update) {
            return APIResponse.toExceptionResponse("从班级中移除失败,请稍后重试");
        }

        // 日志
        ScStudentCourseLog studentCourseLog = ScStudentCourseLog.builder()
                .studentId(studentCourse.getStudentId())
                .logType(LogTypeEnum.OUT_CLA.getLogType())
                .courseId(studentCourse.getCourseId())
                .courseName(studentCourse.getCourseName())
                .deptName(sysDept.getDeptName())
                .memo("退出班级,从'" + studentCourse.getClaName() + "'班级删除")
                .createUser(loginUser.getUserId())
                .createUserName(loginUser.getName())
                .build();
        scStudentCourseLogService.save(studentCourseLog);

        return APIResponse.toOkResponse();
    }
}
