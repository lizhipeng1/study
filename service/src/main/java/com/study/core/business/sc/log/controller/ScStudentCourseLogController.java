package com.study.core.business.sc.log.controller;

import cn.xluobo.business.sc.log.domain.req.ReqSearchStuCourseLog;
import cn.xluobo.business.sc.log.service.BusinessScStudentCourseLogService;
import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志
 * @author ：zhangbaoyu
 * @date ：Created in 2020/12/21 14:45
 */
@RestController
@RequestMapping("/api/sc/student/log")
public class ScStudentCourseLogController {

    @Autowired
    private BusinessScStudentCourseLogService studentCourseLogService;

    /**
     * 列表
     *
     * @param reqSearchStuCourseLog
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchStuCourseLog reqSearchStuCourseLog) {
        return studentCourseLogService.searchList(reqSearchStuCourseLog);
    }
}
