package com.study.base.business.sc.student.service;

import cn.xluobo.business.sc.course.repo.model.ScClaTime;
import cn.xluobo.business.sc.course.repo.model.ScClaTimeAttend;
import cn.xluobo.business.sc.student.repo.model.ScStudentCourse;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.core.api.APIBaseResponse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 学生课程 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
public interface IScStudentCourseService extends IService<ScStudentCourse> {

    /**
     * 校验学生是否可报读课程
     *
     * @param studentId
     * @param courseIds
     * @param deptId
     * @return
     */
    APIBaseResponse checkStudentCanSignUpCourse(Long studentId, Long[] courseIds, Long deptId);

    /**
     * 校验学生是否可报读课程
     *
     * @param studentId
     * @param courseId
     * @param deptId
     * @param studentCourseChargeTypeMap 已报读信息 传null即可
     * @return
     */
    APIBaseResponse checkStudentCanSignUpCourse(Long studentId,
                                                Long courseId,
                                                Long deptId,
                                                Map<Long, String> studentCourseChargeTypeMap);

    /**
     * 根据学生、 课程获取报读信息
     * @param studentId
     * @param courseId
     * @return
     */
    ScStudentCourse selectByStudentIdCourseId(Long studentId, Long courseId);

    /**
     * 作废订单时，当报读课程总课时、总费用 为0时，删除报读
     * @param studentCourseId
     * @return
     */
    int deleteWhenTotalHourZeroForInvalid(Long studentCourseId);

    /**
     * 作废订单时，当报读课程总天数、总费用 为0时，删除报读
     * @param studentCourseId
     * @return
     */
    int deleteWhenTotalDayZeroForInvalid(Long studentCourseId);

    /**
     * 即将过期人员数量
     * @return
     */
    Integer getWillExpireDateCount(Integer minBalanceDay);

    /**
     * 剩余课时小于 最小 数量
     * @param minBalanceHour 最小剩余课时数量
     * @return
     */
    Integer getWillExpireHourCount(Integer minBalanceHour);

    /**
     * 根据上课记录 恢复学员课时
     * @param claTimeAttend
     * @param claTime
     * @param loginUser
     */
    void recoverStudentCourseHour(ScClaTimeAttend claTimeAttend, ScClaTime claTime, LoginUser loginUser);

}
