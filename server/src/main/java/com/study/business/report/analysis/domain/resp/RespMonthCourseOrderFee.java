package com.study.business.report.analysis.domain.resp;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 月份课程 营业收入
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/12/24 19:27
 */
@Data
public class RespMonthCourseOrderFee {

    private Integer id;

    private String month;

    private Long courseId;

    private String courseName;

    private BigDecimal income;

    private List<RespMonthCourseOrderFee> children;

}
