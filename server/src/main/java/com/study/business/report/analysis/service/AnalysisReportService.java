package com.study.business.report.analysis.service;

import com.study.business.report.analysis.domain.resp.RespMonthCourseOrderFee;
import com.study.business.sc.order.domain.req.ReqReportMonthCourseIncome;
import com.study.business.sc.order.domain.resp.RespReportMonthCourseIncome;
import com.study.business.sc.order.repo.mapper.OrderReportMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/12/24 19:12
 */
@Service
public class AnalysisReportService {

    @Autowired
    private OrderReportMapper orderReportMapper;


    /**
     * 月份课程 营业收入报表
     *
     * @return
     */
    public List<RespMonthCourseOrderFee> monthCourseOrderFeeReport(ReqReportMonthCourseIncome reqReportMonthCourseIncome) {
        // 查询db 按月、课程 收入情况
        List<RespReportMonthCourseIncome> monthCourseIncomeList = orderReportMapper.selectMonthCourseIncomeList(reqReportMonthCourseIncome);

        // 月份对应数据
        Map<String, List<RespReportMonthCourseIncome>> monthMap = Maps.newHashMap();
        for (RespReportMonthCourseIncome respReportMonthCourseIncome : monthCourseIncomeList) {
            String month = respReportMonthCourseIncome.getMonth();
            if (monthMap.containsKey(month)) {
                monthMap.get(month).add(respReportMonthCourseIncome);
            } else {
                ArrayList<RespReportMonthCourseIncome> incomeArrayList = Lists.newArrayList(respReportMonthCourseIncome);
                monthMap.put(month, incomeArrayList);
            }
        }

        // 返回结果 月份下 包含各课程收入
        List<RespMonthCourseOrderFee> courseOrderFeeList = Lists.newArrayList();

        monthMap.forEach((month, value) -> {

            // 一个月份 各课程收入
            List<RespMonthCourseOrderFee> childrenList = Lists.newArrayList();

            BigDecimal totalFee = BigDecimal.ZERO;

            for (int i = 0; i < value.size(); i++) {
                RespReportMonthCourseIncome respReportMonthCourseIncome = value.get(i);
                totalFee = totalFee.add(respReportMonthCourseIncome.getFee());

                RespMonthCourseOrderFee item = new RespMonthCourseOrderFee();
                item.setId(Integer.parseInt(month + (i + 1)));
                item.setCourseId(respReportMonthCourseIncome.getCourseId());
                item.setCourseName(respReportMonthCourseIncome.getCourseName());
                item.setIncome(respReportMonthCourseIncome.getFee());

                childrenList.add(item);
            }

            childrenList.sort((o1, o2) -> {
                BigDecimal incomeBefore = o1.getIncome();
                BigDecimal incomeAfter = o2.getIncome();
                return incomeBefore.compareTo(incomeAfter);
            });


            // 月份 总收入
            RespMonthCourseOrderFee orderFee = new RespMonthCourseOrderFee();
            orderFee.setId(Integer.parseInt(month));
            orderFee.setMonth(month);
            orderFee.setIncome(totalFee);
            orderFee.setChildren(childrenList);

            // 单条记录
            courseOrderFeeList.add(orderFee);
        });

        courseOrderFeeList.sort((o1, o2) -> {
            int before = Integer.parseInt(o1.getMonth());
            int after = Integer.parseInt(o2.getMonth());
            return before- after;
        });

        return courseOrderFeeList;
    }

}
