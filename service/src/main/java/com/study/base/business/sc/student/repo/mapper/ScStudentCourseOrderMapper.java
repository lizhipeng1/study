package com.study.base.business.sc.student.repo.mapper;

import cn.xluobo.business.sc.student.repo.model.ScStudentCourseOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 学生课程关联订单 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
public interface ScStudentCourseOrderMapper extends BaseMapper<ScStudentCourseOrder> {


    /**
     * 校验按时间收费 收费日期是否存在覆盖
     *
     * @param studentId
     * @param courseId
     * @param beginDate
     * @param endDate
     * @return
     */
    int checkDateCover(@Param("studentId") Long studentId, @Param("courseId") Long courseId,
                       @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    /**
     * 获取需扣减课时的订单
     * @param studentCourseId
     * @return
     */
    ScStudentCourseOrder selectSubtractHourOrder(Long studentCourseId);

    /**
     * 当前生效 按时间 缴费订单
     * @param studentCourseId
     * @return
     */
    ScStudentCourseOrder selectNowValidDateOrder(Long studentCourseId);

    /**
     * 获取可扣减课时的订单列表
     * @param studentCourseId
     * @return
     */
    List<ScStudentCourseOrder> selectSubtractHourOrderList(Long studentCourseId);

    /**
     * 按课时收费 订单列表，按时间倒叙
     * @param studentCourseId
     * @return
     */
    List<ScStudentCourseOrder> selectRecoverHourOrderList(Long studentCourseId);

    /**
     * 扣减课时
     * @param courseOrderId
     * @param newHour
     * @param oldHour
     * @return
     */
    int updateSubtractHour(@Param("courseOrderId") Long courseOrderId,
                           @Param("newHour") BigDecimal newHour,
                           @Param("oldHour") BigDecimal oldHour);
}
