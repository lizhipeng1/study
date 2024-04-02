package com.study.base.business.sc.order.repo.model;

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
 * 订单收款账户
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_order_account")
public class ScOrderAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "order_account_id", type = IdType.ASSIGN_ID)
    private Long orderAccountId;

    /**
     * 订单编号
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 收款账户
     */
    @TableField("account_id")
    private Long accountId;

    /**
     * 账户名称
     */
    @TableField("account_name")
    private String accountName;

    /**
     * 实收
     */
    @TableField("fee")
    private BigDecimal fee;


}
