package com.study.core.business.sc.student.controller;

import cn.xluobo.business.sc.student.service.BusinessScStudentAccountService;
import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生账户
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/2 14:03
 */
@RestController
@RequestMapping("/api/sc/studentAccount")
public class ScStudentAccountController {

    @Autowired
    private BusinessScStudentAccountService accountService;

    /**
     * 学生账户余额
     *
     * @param studentId
     * @return
     */
    @GetMapping("/info/studentAccountBalance/{studentId}")
    public APIResponse studentAccountBalance(@PathVariable("studentId") Long studentId) {
        return accountService.studentAccountBalance(studentId);
    }

}
