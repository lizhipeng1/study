package com.study.base.business.sc.student.service;

import cn.xluobo.business.sc.student.repo.model.ScStudentCourseOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 学生课程关联订单 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
public interface IScStudentCourseOrderService extends IService<ScStudentCourseOrder> {

    /**
     * 校验按时间收费 收费日期是否存在覆盖
     *
     * @param studentId
     * @param courseId
     * @param beginDate
     * @param endDate
     * @return
     */
    boolean checkDateCover(Long studentId, Long courseId,
                       String beginDate, String endDate);

    /**
     * 获取应扣课时订单信息
     * @param studentCourseId
     * @return
     */
    ScStudentCourseOrder getSubtractHourOrder(Long studentCourseId);

    /**
     * 当前生效 按时间 缴费订单
     * @param studentCourseId
     * @return
     */
    ScStudentCourseOrder getNowValidDateOrder(Long studentCourseId);


    /**
     * 课程订单扣减课时
     * @param studentCourseId
     * @param loseHour
     * @return 扣减总课时费用
     */
    BigDecimal subtractCourseOrderBalanceHour(Long studentCourseId, BigDecimal loseHour);

    /**
     * 课程订单 恢复课时
     * 当删除上课记录时调用
     * @param studentCourseId
     * @param recoverHour 恢复课时数量
     * @return
     */
    void recoverOrderLoseHour(Long studentCourseId, BigDecimal recoverHour);

    /**
     * 更新订单 剩余课时
     * @param courseOrderId
     * @param newBalanceHour
     * @param oldBalanceHour
     */
    void updateBalanceHour(Long courseOrderId, BigDecimal newBalanceHour, BigDecimal oldBalanceHour);

}
