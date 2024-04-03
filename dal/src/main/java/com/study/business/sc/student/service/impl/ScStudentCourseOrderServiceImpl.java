package com.study.business.sc.student.service.impl;

import com.study.business.sc.student.repo.mapper.ScStudentCourseMapper;
import com.study.business.sc.student.repo.mapper.ScStudentCourseOrderMapper;
import com.study.business.sc.student.repo.mapper.ScStudentMapper;
import com.study.business.sc.student.repo.model.ScStudent;
import com.study.business.sc.student.repo.model.ScStudentCourse;
import com.study.business.sc.student.repo.model.ScStudentCourseOrder;
import com.study.business.sc.student.service.IScStudentCourseOrderService;
import com.study.config.exception.BusinessException;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 学生课程关联订单 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
@Service
public class ScStudentCourseOrderServiceImpl extends ServiceImpl<ScStudentCourseOrderMapper, ScStudentCourseOrder> implements IScStudentCourseOrderService {

    @Autowired
    private ScStudentCourseMapper studentCourseMapper;
    @Autowired
    private ScStudentMapper studentMapper;

    @Override
    public boolean checkDateCover(Long studentId, Long courseId, String beginDate, String endDate) {
        int checkDateCover = baseMapper.checkDateCover(studentId, courseId, beginDate, endDate);
        if (checkDateCover == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public ScStudentCourseOrder getSubtractHourOrder(Long studentCourseId) {
        return baseMapper.selectSubtractHourOrder(studentCourseId);
    }

    @Override
    public ScStudentCourseOrder getNowValidDateOrder(Long studentCourseId) {
        return baseMapper.selectNowValidDateOrder(studentCourseId);
    }

    @Override
    public BigDecimal subtractCourseOrderBalanceHour(Long studentCourseId, BigDecimal loseHour) {
        List<ScStudentCourseOrder> studentCourseOrderList = baseMapper.selectSubtractHourOrderList(studentCourseId);
        BigDecimal payFee = BigDecimal.ZERO;
        for (ScStudentCourseOrder courseOrder : studentCourseOrderList) {
            if (loseHour.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }
            BigDecimal balanceHour = courseOrder.getBalanceHour();

            // 订单剩余课时不足扣减
            if (balanceHour.compareTo(loseHour) < 0) {
                payFee = payFee.add(balanceHour.multiply(courseOrder.getUnitFee()));

                // 剩余课时不足扣减, 将剩余课时扣减完毕
                int updateSubtractHour = baseMapper.updateSubtractHour(courseOrder.getCourseOrderId(), BigDecimal.ZERO, balanceHour);
                if (updateSubtractHour == 0) {
                    throw new BusinessException("订单扣减课时失败,请重试!");
                }

                loseHour = loseHour.subtract(balanceHour);

            } else {
                payFee = payFee.add(loseHour.multiply(courseOrder.getUnitFee()));

                // 直接扣减课时
                BigDecimal newHour = balanceHour.subtract(loseHour);
                int updateSubtractHour = baseMapper.updateSubtractHour(courseOrder.getCourseOrderId(), newHour, balanceHour);
                if (updateSubtractHour == 0) {
                    throw new BusinessException("订单扣减课时失败,请重试!");
                }
            }
        }
        return payFee;
    }

    @Override
    public void recoverOrderLoseHour(Long studentCourseId, BigDecimal recoverHour) {
        if(recoverHour.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        List<ScStudentCourseOrder> recoverHourOrderList = baseMapper.selectRecoverHourOrderList(studentCourseId);
        if (recoverHourOrderList.size() == 0) {
            ScStudentCourse studentCourse = studentCourseMapper.selectById(studentCourseId);
            ScStudent student = studentMapper.selectById(studentCourse.getStudentId());
            throw new BusinessException("学员:" + student.getStudentName() + ",无生效订单,无法恢复课时!");
        }

        for (int i = 0; i < recoverHourOrderList.size(); i++) {

            if(recoverHour.compareTo(BigDecimal.ZERO) == 0) {
                return;
            }

            // 是否为最后一条记录
            boolean lastRecord = (i == (recoverHourOrderList.size() - 1));
            ScStudentCourseOrder studentCourseOrder = recoverHourOrderList.get(i);
            BigDecimal balanceHour = studentCourseOrder.getBalanceHour();
            BigDecimal totalHour = studentCourseOrder.getTotalHour();
            if(lastRecord) {
                BigDecimal newBalanceHour = studentCourseOrder.getBalanceHour().add(recoverHour);
                this.updateBalanceHour(studentCourseOrder.getCourseOrderId(), newBalanceHour, balanceHour);
                break;
            } else {
                if(balanceHour.compareTo(totalHour) < 0) {
                    // 可恢复课时数量
                    BigDecimal canRecoverHour = totalHour.subtract(balanceHour);

                    if(canRecoverHour.compareTo(recoverHour) > 0) {
                        // 订单可恢复课时数量canRecoverHour > 需要恢复的课时数量recoverHour，直接全部恢复

                        BigDecimal newBalanceHour = studentCourseOrder.getBalanceHour().add(recoverHour);
                        this.updateBalanceHour(studentCourseOrder.getCourseOrderId(), newBalanceHour, balanceHour);
                        recoverHour = BigDecimal.ZERO;
                    } else {
                        // 订单可恢复课时数量canRecoverHour <= 需要恢复的课时数量recoverHour

                        BigDecimal newBalanceHour = studentCourseOrder.getBalanceHour().add(canRecoverHour);
                        this.updateBalanceHour(studentCourseOrder.getCourseOrderId(), newBalanceHour, balanceHour);

                        // 剩余需恢复课时
                        recoverHour = recoverHour.subtract(canRecoverHour);
                    }
                }
            }
        }
    }

    @Override
    public void updateBalanceHour(Long courseOrderId, BigDecimal newBalanceHour, BigDecimal oldBalanceHour) {
        UpdateWrapper<ScStudentCourseOrder> uw = new UpdateWrapper<>();
        uw.eq("course_order_id", courseOrderId);
        uw.eq("balance_hour", oldBalanceHour);
        uw.set("balance_hour", newBalanceHour);
        boolean update = this.update(uw);
        if (!update) {
            throw new BusinessException("订单恢复课时失败,请重试!");
        }
    }
}
