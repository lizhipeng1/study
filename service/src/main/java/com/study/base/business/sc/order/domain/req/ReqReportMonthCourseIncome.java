package com.study.base.business.sc.order.domain.req;

import lombok.Data;

/**
 * 月份、课程 营收情况
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/12/24 19:57
 */
@Data
public class ReqReportMonthCourseIncome {

    private String beginMonth;

    private String endMonth;

    private Long deptId;

    private Long[] deptIds;

    private Long[] courseIds;

}
