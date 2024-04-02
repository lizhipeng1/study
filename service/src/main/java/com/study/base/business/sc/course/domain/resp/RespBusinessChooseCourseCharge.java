package com.study.base.business.sc.course.domain.resp;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 选择课程的可选 收费模式
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/26 17:16
 */
@Data
@Builder
public class RespBusinessChooseCourseCharge {

    private Long chargeId;

    /**
     * 单个金额
     */
    private BigDecimal totalFee;

    /**
     * select label
     */
    private String label;

    // 收费类型
    private String chargeType;

    // 数量
    private BigDecimal count;

    // 按时间 时间单位
    private String dateUnit;
}
