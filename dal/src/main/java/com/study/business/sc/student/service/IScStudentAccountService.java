package com.study.business.sc.student.service;

import com.study.business.sc.student.repo.model.ScStudentAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 学生余额账户 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
public interface IScStudentAccountService extends IService<ScStudentAccount> {

    /**
     * 学生账户余额
     * @param studentId
     * @return
     */
    BigDecimal selectStudentAccountBalance(Long studentId);

    /**
     * 增加学生账户余额
     * @param studentId
     * @param addBalance
     * @param userId
     */
    void addBalance(Long studentId, BigDecimal addBalance,String userId);
}
