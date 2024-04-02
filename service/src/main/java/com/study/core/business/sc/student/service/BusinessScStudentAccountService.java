package com.study.core.business.sc.student.service;

import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/2 14:04
 */
@Service
@Transactional
public class BusinessScStudentAccountService {

    @Autowired
    private IScStudentAccountService accountService;

    /**
     * 学生账户信息
     *
     * @param studentId
     * @return
     */
    public APIResponse studentAccountBalance(Long studentId) {
        if (null == studentId) {
            return APIResponse.toAPIResponse(BigDecimal.ZERO);
        }
        BigDecimal accountBalance = accountService.selectStudentAccountBalance(studentId);
        return APIResponse.toAPIResponse(accountBalance);
    }
}
