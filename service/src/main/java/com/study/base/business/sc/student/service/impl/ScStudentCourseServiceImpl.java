package com.study.base.business.sc.student.service.impl;

import cn.xluobo.business.sc.course.enums.CourseChargeTypeEnum;
import cn.xluobo.business.sc.course.repo.mapper.ScCourseChargeMapper;
import cn.xluobo.business.sc.course.repo.mapper.ScCourseMapper;
import cn.xluobo.business.sc.course.repo.model.ScClaTime;
import cn.xluobo.business.sc.course.repo.model.ScClaTimeAttend;
import cn.xluobo.business.sc.course.repo.model.ScCourse;
import cn.xluobo.business.sc.course.repo.model.ScCourseCharge;
import cn.xluobo.business.sc.log.enums.LogTypeEnum;
import cn.xluobo.business.sc.log.repo.model.ScStudentCourseLog;
import cn.xluobo.business.sc.log.service.IScStudentCourseLogService;
import cn.xluobo.business.sc.student.repo.mapper.ScStudentCourseMapper;
import cn.xluobo.business.sc.student.repo.model.ScStudentCourse;
import cn.xluobo.business.sc.student.service.IScStudentCourseOrderService;
import cn.xluobo.business.sc.student.service.IScStudentCourseService;
import cn.xluobo.business.sys.admin.repo.mapper.SysDeptMapper;
import cn.xluobo.business.sys.admin.repo.model.SysDept;
import cn.xluobo.config.exception.BusinessException;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.core.api.APIBaseResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 学生课程 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
@Service
public class ScStudentCourseServiceImpl extends ServiceImpl<ScStudentCourseMapper, ScStudentCourse> implements IScStudentCourseService {

    @Autowired
    private ScCourseChargeMapper courseChargeMapper;
    @Autowired
    private ScCourseMapper courseMapper;
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private IScStudentCourseOrderService courseOrderService;
    @Autowired
    private IScStudentCourseLogService scStudentCourseLogService;

    @Override
    public APIBaseResponse checkStudentCanSignUpCourse(Long studentId, Long[] courseIds, Long deptId) {
        // 学生已报名课程收费模式
        QueryWrapper<ScStudentCourse> qw = new QueryWrapper<>();
        qw.select("course_id", "charge_type");
        qw.eq("student_id", studentId);
        List<ScStudentCourse> studentCourseList = this.list(qw);
        Map<Long, String> studentCourseChargeTypeMap = studentCourseList.stream().collect(Collectors.toMap(ScStudentCourse::getCourseId, ScStudentCourse::getChargeType));
        for (Long courseId : courseIds) {
            APIBaseResponse response = this.checkStudentCanSignUpCourse(studentId, courseId, deptId, studentCourseChargeTypeMap);
            if(!response.isSuccess()) {
                return response;
            }
        }

        return APIBaseResponse.success();
    }

    @Override
    public APIBaseResponse checkStudentCanSignUpCourse(Long studentId, Long courseId, Long deptId,Map<Long, String> studentCourseChargeTypeMap) {
        if (null == studentCourseChargeTypeMap) {
            QueryWrapper<ScStudentCourse> qw = new QueryWrapper<>();
            qw.select("course_id", "charge_type");
            qw.eq("student_id", studentId);
            List<ScStudentCourse> studentCourseList = this.list(qw);
            studentCourseChargeTypeMap = studentCourseList.stream().collect(Collectors.toMap(ScStudentCourse::getCourseId, ScStudentCourse::getChargeType));
        }
        ScCourse scCourse = courseMapper.selectById(courseId);
        if( null == scCourse) {
            return APIBaseResponse.fail("无法获取课程信息,请重试");
        }
        // 已报读的 收费方式
        String studentCourseChargeType = studentCourseChargeTypeMap.get(courseId);
        if("cycle".equals(studentCourseChargeType)) {
            return APIBaseResponse.fail("该学生 已" + CourseChargeTypeEnum.getChargeType(studentCourseChargeType) + "报读'" + scCourse.getCourseName() + "',无法重复报读!");
        }
        // 课程收费配置信息
        QueryWrapper<ScCourseCharge> qw = new QueryWrapper<>();
        qw.eq("course_id", courseId);
        if (StringUtils.isNotEmpty(studentCourseChargeType)) {
            qw.eq("charge_type", studentCourseChargeType);
        }
        List<ScCourseCharge> courseChargeList = courseChargeMapper.selectList(qw);

        // 校验学生是否可选择课程 (是否含 已报读的 收费方式)
        if (studentCourseChargeTypeMap.containsKey(courseId)) {
            // 是否包含
            boolean checkHasStudentCourseChargeType = courseChargeList.stream().anyMatch(item -> item.getChargeType().equals(studentCourseChargeType));
            if (!checkHasStudentCourseChargeType) {
                return APIBaseResponse.fail("该学生 已" + CourseChargeTypeEnum.getChargeType(studentCourseChargeType) + "报读'" + scCourse.getCourseName() + "',目前该课程暂无此收费模式,请核查!");
            }
        }

        if (null != deptId) {
            // 校验是否配置了本校区
            boolean checkChargeDept = courseChargeList.stream().anyMatch(item -> item.getDepartId().compareTo(deptId) == 0 || item.getDepartId().compareTo(-1L) == 0);
            if (!checkChargeDept) {
                SysDept sysDept = deptMapper.selectById(deptId);
                return APIBaseResponse.fail(scCourse.getCourseName() + "(" + CourseChargeTypeEnum.getChargeType(studentCourseChargeType) + ")未在'" + sysDept.getDeptName() + "'开课");
            }
        }
        return APIBaseResponse.success();
    }

    @Override
    public ScStudentCourse selectByStudentIdCourseId(Long studentId, Long courseId) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("student_id",studentId);
        qw.eq("course_id",courseId);
        return this.getOne(qw);
    }

    @Override
    public int deleteWhenTotalHourZeroForInvalid(Long studentCourseId) {
        return baseMapper.deleteWhenTotalHourZeroForInvalid(studentCourseId);
    }

    @Override
    public int deleteWhenTotalDayZeroForInvalid(Long studentCourseId) {
        return baseMapper.deleteWhenTotalDayZeroForInvalid(studentCourseId);
    }

    @Override
    public Integer getWillExpireDateCount(Integer minBalanceDay) {
        return baseMapper.selectWillExpireDateCount(minBalanceDay);
    }

    @Override
    public Integer getWillExpireHourCount(Integer minBalanceHour) {
        return baseMapper.selectWillExpireHourCount(minBalanceHour);
    }

    @Override
    public void recoverStudentCourseHour(ScClaTimeAttend claTimeAttend, ScClaTime claTime, LoginUser loginUser) {
        Long studentCourseId = claTimeAttend.getStudentCourseId();
        String chargeType = claTimeAttend.getChargeType();
        BigDecimal payHour = claTimeAttend.getPayHour();

        // 学员日志
        StringBuffer sb = new StringBuffer("");
        sb.append("恢复课时[编辑/删除上课记录],").append("上课时间").append(claTime.getRealClaDate()).append(" ")
                .append(claTime.getRealStartTime()).append("~").append(claTime.getRealEndTime()).append(",");
        ScStudentCourseLog studentCourseLog = ScStudentCourseLog.builder()
                .studentId(claTimeAttend.getStudentId())
                .logType(LogTypeEnum.DELETE_ATTEND_CLA.getLogType())
                .courseId(claTimeAttend.getCourseId())
                .courseName(claTime.getCourseName())
                .claId(claTimeAttend.getClaId())
                .claName(claTime.getClaName())
                .deptName(claTime.getDeptName())
                .changeHour(claTimeAttend.getPayHour())
                .changeFee(claTimeAttend.getPayFee())
                .createUser(loginUser.getUserId())
                .createUserName(loginUser.getName())
                .createTime(new Date())
                .build();

        if (!CourseChargeTypeEnum.DATE.getChargeType().equals(chargeType)) {
            ScStudentCourse studentCourse = this.getById(studentCourseId);
            if (null == studentCourse) {
                throw new BusinessException("恢复学生课时失败,无法获取学生课时信息!");
            }
            // 消耗课时> 0 , 恢复课时
            if (payHour.compareTo(BigDecimal.ZERO) > 0) {
                UpdateWrapper<ScStudentCourse> uwStudentCourse = new UpdateWrapper<>();
                uwStudentCourse
                        .eq("student_course_id", studentCourseId)
                        .eq("balance_hour", studentCourse.getBalanceHour())
                        .set("balance_hour", studentCourse.getBalanceHour().add(payHour))
                        .set("last_update_user", loginUser.getUserId())
                        .set("last_update_time", new Date());
                boolean update = this.update(uwStudentCourse);
                if (!update) {
                    throw new BusinessException("学员剩余课时恢复失败,请重试!");
                }

                studentCourseLog.setAfterBalanceHour(studentCourse.getBalanceHour().add(payHour));
            } else {
                studentCourseLog.setAfterBalanceHour(studentCourse.getBalanceHour().add(payHour));
            }

            sb.append("恢复").append(payHour.toString()).append("课时");

            // 恢复订单课时
            courseOrderService.recoverOrderLoseHour(studentCourse.getStudentCourseId(), payHour);
        }

        studentCourseLog.setMemo(sb.toString());
        scStudentCourseLogService.save(studentCourseLog);
    }

}
