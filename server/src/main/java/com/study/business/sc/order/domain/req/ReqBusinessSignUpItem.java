package com.study.business.sc.order.domain.req;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 报名详情
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/3 16:06
 */
@Data
public class ReqBusinessSignUpItem {

    private Long courseId;

    private Long claId;

    // 报读校区
    private Long departId;

    private Long chargeId;

    private String chargeType;

    // 购买数量
    private Integer buyCount;

    // 按课时收费 是否设置有效期
    private boolean openExpire;

    // 失效时间
    private String expireDate;

    // 按时间收费 生效时间
    private String beginDate;
    // 按时间收费 失效时间
    private String endDate;

    // 是否学费折扣
    private boolean openDiscount;
    // 是否学费优惠
    private boolean openDiscountFee;
    // 折扣 0-10之间
    private BigDecimal discount;
    // 优惠
    private BigDecimal discountFee;

    // 内部备注
    private String insideMemo;
    // 外部备注
    private String outsideMemo;

    // 类型 1新报 2续报
    private String detailTag;

}
