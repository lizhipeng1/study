package com.study.base.business.sc.order.repo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author zhangby
 * @since 2020-08-24 10:22:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_order")
public class ScOrder implements Serializable {


    /**
     * 订单编号
     */
    @TableId(value = "order_id", type = IdType.ASSIGN_ID)
    private Long orderId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 学员编号
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 学员名称
     */
    @TableField("student_name")
    private String studentName;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 订单类型 1报名
     */
    @TableField("order_type")
    private String orderType;

    /**
     * 原价
     */
    @TableField("original_total_fee")
    private BigDecimal originalTotalFee;

    /**
     * 实际价格
     */
    @TableField("actual_total_fee")
    private BigDecimal actualTotalFee;

    /**
     * 收款金额 receipt_fee+balance_fee=actual_total_fee
     */
    @TableField("receipt_fee")
    private BigDecimal receiptFee;

    /**
     * 使用余额支付金额
     */
    @TableField("balance_fee")
    private BigDecimal balanceFee;

    /**
     * 订单标签
     */
    @TableField("order_tag")
    private String orderTag;

    /**
     * 销售来源标签
     */
    @TableField("sale_source_tag")
    private String saleSourceTag;

    /**
     * 销售员工
     */
    @TableField("sale_staff_id")
    private Long saleStaffId;

    /**
     * 销售员工
     */
    @TableField("sale_staff_name")
    private String saleStaffName;

    /**
     * 订单状态 1待支付 2已支付 3已作废
     */
    @TableField("order_status")
    private String orderStatus;

    /**
     * 内部备注
     */
    @TableField("memo")
    private String memo;

    /**
     * 经办校区
     */
    @TableField("handle_dept_id")
    private Long handleDeptId;

    /**
     * 经办校区
     */
    @TableField("handle_dept_name")
    private String handleDeptName;

    /**
     * 经办日期
     */
    @TableField("handle_date")
    private String handleDate;

    /**
     * 创建者
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField("last_update_user")
    private String lastUpdateUser;

    /**
     * 更新时间
     */
    @TableField("last_update_time")
    private Date lastUpdateTime;

    /**
     * 办理人员姓名
     */
    @TableField(exist = false)
    private String handleStaffName;
}
