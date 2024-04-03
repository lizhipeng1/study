package com.study.business.sc.course.domain.req.course;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 课程收费模式
 * @author ：zhangbaoyu
 * @date ：Created in 2020/7/23 11:07
 */
@Data
public class ReqAddScCourseChargeItem {

    private Long chargeId;

    /**
     * 校区id
     */
    private Long campusId;

    /**
     * 校区名称
     */
    private String campusName;

    /**
     * 数量
     */
    private BigDecimal cnt;

    /**
     * 总价格
     */
    private BigDecimal totalFee;

    /**
     * 时间段 日 月 季 年
     */
    private String dateType;

}
