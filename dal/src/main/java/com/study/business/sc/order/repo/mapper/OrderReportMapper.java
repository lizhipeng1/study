package com.study.business.sc.order.repo.mapper;

import com.study.business.sc.order.domain.req.ReqReportMonthCourseIncome;
import com.study.business.sc.order.domain.resp.RespReportMonthCourseIncome;

import java.util.List;

/**
 * 订单报表相关
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/12/24 19:50
 */
public interface OrderReportMapper {

    /**
     * 月份、课程 营收情况
     * @param reqReportMonthCourseIncome
     * @return
     */
    List<RespReportMonthCourseIncome> selectMonthCourseIncomeList(ReqReportMonthCourseIncome reqReportMonthCourseIncome);

}
