package com.study.base.business.sc.course.service;

import cn.xluobo.business.sc.course.domain.req.time.ReqClaTimeCount;
import cn.xluobo.business.sc.course.repo.model.ScClaTimeAttend;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 上课出勤表 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-30 02:33:26
 */
public interface IScClaTimeAttendService extends com.baomidou.mybatisplus.extension.service.IService<ScClaTimeAttend> {

    /**
     * 学生报的的课程，指定日期 上课记录 数量
     * @param studentCourseId
     * @param beginDate
     * @param endDate
     * @return
     */
    int studentCourseAttendCount(Long studentCourseId, String beginDate, String endDate);

    /**
     * 指定时间段内应出席人次
     * @param beginDate
     * @param endDate
     * @return
     */
    Integer getNeedAttendCount(String beginDate, String endDate);

    /**
     * 指定时间段内上课人次
     * @param beginDate
     * @param endDate
     * @param attendStatus 到课状态
     * @return
     */
    Integer getAttendCount(String beginDate, String endDate, String[] attendStatus);

    /**
     * 消耗课时
     * @param beginDate
     * @param endDate
     * @param needAttend 是否应消
     * @return
     */
    BigDecimal getAttendCostHour(String beginDate, String endDate, boolean needAttend);

    /**
     * 教师获得课时数量
     * 只统计按课时收费
     *
     * @param reqClaTimeCount
     * @return
     */
    BigDecimal getTeacherGetHour(ReqClaTimeCount reqClaTimeCount);

    /**
     * 上课记录
     * @param courseTimeId
     * @return
     */
    List<ScClaTimeAttend> getAttendList(Long courseTimeId);
}
