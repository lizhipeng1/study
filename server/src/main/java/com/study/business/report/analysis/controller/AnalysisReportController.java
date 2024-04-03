package com.study.business.report.analysis.controller;

import com.study.business.report.analysis.domain.resp.RespMonthCourseOrderFee;
import com.study.business.report.analysis.service.AnalysisReportService;
import com.study.business.sc.order.domain.req.ReqReportMonthCourseIncome;
import com.study.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/12/24 19:12
 */
@RestController
@RequestMapping("/api/report/analysis")
public class AnalysisReportController {

    @Autowired
    private AnalysisReportService analysisReportService;

    /**
     * 月份课程 营业收入报表
     * @return
     */
    @GetMapping("/monthCourseOrderFeeReport")
    public APIResponse monthCourseOrderFeeReport(ReqReportMonthCourseIncome reqReportMonthCourseIncome) {
        List<RespMonthCourseOrderFee> courseOrderFeeList = analysisReportService.monthCourseOrderFeeReport(reqReportMonthCourseIncome);
        return APIResponse.toAPIResponse(courseOrderFeeList);
    }

}
