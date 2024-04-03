package com.study.business.mk.discount.repo.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 优惠配置明细
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mk_discount_detail")
public class MkDiscountDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 明细编号
     */
    @TableId(value = "discount_detail_id", type = IdType.ASSIGN_ID)
    private Long discountDetailId;

    /**
     * 折扣编号
     */
    @TableField("discount_id")
    private Long discountId;

    /**
     * 满足条件 金额/课时
     */
    @TableField("meet_condition")
    private BigDecimal meetCondition;

    /**
     * 赠送 金额/折扣/课时
     */
    @TableField("gift")
    private BigDecimal gift;


}
