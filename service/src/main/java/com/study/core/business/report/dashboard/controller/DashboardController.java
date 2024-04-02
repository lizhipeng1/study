package com.study.core.business.report.dashboard.controller;

import cn.xluobo.business.report.dashboard.domain.resp.DashboardData;
import cn.xluobo.business.report.dashboard.service.DashboardService;
import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/18 15:31
 */
@RestController
@RequestMapping("/api/report/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * 首页各数据项
     * @return
     */
    @GetMapping("/data")
    public APIResponse dashboardData() {
        DashboardData dashboardData = dashboardService.dashboardData();
        return APIResponse.toAPIResponse(dashboardData);
    }

}
