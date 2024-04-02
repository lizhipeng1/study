package com.study.base.business.sc.student.service.impl;

import cn.xluobo.business.sc.student.repo.model.ScStudentAccount;
import cn.xluobo.business.sc.student.repo.mapper.ScStudentAccountMapper;
import cn.xluobo.business.sc.student.service.IScStudentAccountService;
import cn.xluobo.config.exception.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 学生余额账户 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
@Service
public class ScStudentAccountServiceImpl extends ServiceImpl<ScStudentAccountMapper, ScStudentAccount> implements IScStudentAccountService {

    @Override
    public BigDecimal selectStudentAccountBalance(Long studentId) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("student_id", studentId);
        ScStudentAccount studentAccount = baseMapper.selectOne(qw);
        if (null == studentAccount) {
            return BigDecimal.ZERO;
        } else {
            return studentAccount.getBalanceFee();
        }
    }

    @Override
    public void addBalance(Long studentId, BigDecimal addBalance, String userId) {
        if (null == studentId) {
            return;
        }
        if (null == addBalance || addBalance.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }

        QueryWrapper qw = new QueryWrapper();
        qw.eq("student_id", studentId);
        ScStudentAccount studentAccount = this.getOne(qw);
        if (null == studentAccount) {
            studentAccount = new ScStudentAccount();
            studentAccount.setStudentId(studentId);
            studentAccount.setBalanceFee(addBalance);
            studentAccount.setCreateUser(userId);
            studentAccount.setLastUpdateUser(userId);
            this.save(studentAccount);
        } else {
            UpdateWrapper<ScStudentAccount> uw = new UpdateWrapper<>();
            uw.eq("student_id",studentId);
            uw.eq("balance_fee",studentAccount.getBalanceFee());
            uw.set("balance_fee", addBalance.add(studentAccount.getBalanceFee()));
            uw.set("last_update_user", userId);
            uw.set("last_update_time", new Date());
            boolean update = this.update(uw);
            if(!update) {
                throw new BusinessException("余额增加失败,请重试!");
            }
        }
    }
}
