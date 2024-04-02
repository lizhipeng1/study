package com.study.base.business.sc.order.domain.resp;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 月份、课程 营收情况
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/12/24 19:52
 */
@Data
public class RespReportMonthCourseIncome {

    private String month;

    private Long courseId;

    private String courseName;

    private BigDecimal fee;

}
