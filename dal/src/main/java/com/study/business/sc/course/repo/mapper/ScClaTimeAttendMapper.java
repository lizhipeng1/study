package com.study.business.sc.course.repo.mapper;

import com.study.business.sc.course.domain.req.time.ReqClaTimeCount;
import com.study.business.sc.course.domain.req.time.ReqSearchScClaTimeAttend;
import com.study.business.sc.course.domain.resp.time.RespClaTimeAttend;
import com.study.business.sc.course.repo.model.ScClaTimeAttend;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 上课出勤表 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-09-30 02:33:26
 */
public interface ScClaTimeAttendMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<ScClaTimeAttend> {

    /**
     * 获取明细
     *
     * @param reqSearchScClaTimeAttend
     * @param page
     * @return
     */
    List<RespClaTimeAttend> selectTimeAttendList(@Param("reqSearchScClaTimeAttend") ReqSearchScClaTimeAttend reqSearchScClaTimeAttend, @Param("page") Page page);

    /**
     * 学生出席记录数量
     *
     * @param studentCourseId 学生报读课程id
     * @param beginDate       开始时间
     * @param endDate         结束时间
     * @return
     */
    int selectStudentAttendCount(@Param("studentCourseId") Long studentCourseId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    /**
     * 应上课人数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    Integer selectNeedAttendCount(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    /**
     * 学生上课出席数量
     *
     * @param beginDate
     * @param endDate
     * @param attendStatus
     * @return
     */
    Integer selectAttendCount(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("attendStatus") String[] attendStatus);

    /**
     * 实消 学生消耗课时数量
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    BigDecimal selectAttendCostHour(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    /**
     * 应消 学生消耗课时数量
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    BigDecimal selectNeedAttendCostHour(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    /**
     * 教师 指定日期范围内 累计获得课时
     * 只统计 课时，不统计按时间缴费的
     *
     * @param reqClaTimeCount
     * @return
     */
    BigDecimal selectTeacherSumGetHour(ReqClaTimeCount reqClaTimeCount);
}
