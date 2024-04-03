package com.study.business.sc.order.repo.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单详情
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_order_detail")
public class ScOrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 详单编号
     */
    @TableId(value = "order_detail_id", type = IdType.ASSIGN_ID)
    private Long orderDetailId;

    /**
     * 订单编号
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 课程
     */
    @TableField("course_id")
    private Long courseId;

    /**
     * 课程
     */
    @TableField("course_name")
    private String courseName;

    /**
     * 班级
     */
    @TableField("cla_id")
    private Long claId;

    /**
     * 班级
     */
    @TableField("cla_name")
    private String claName;

    /**
     * 校区
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 校区
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 类型 1新报 2续报 3扩科
     */
    @TableField("detail_tag")
    private String detailTag;

    /**
     * 收费方式名称
     */
    @TableField("charge_name")
    private String chargeName;

    /**
     * 收费模式
     */
    @TableField("charge_type")
    private String chargeType;

    /**
     * 收费方式包含课时数量
     */
    @TableField("charge_count")
    private BigDecimal chargeCount;

    /**
     * 收费方式金额
     */
    @TableField("charge_fee")
    private BigDecimal chargeFee;

    /**
     * 时间周期
     */
    @TableField("date_unit")
    private String dateUnit;

    /**
     * 购买数量
     */
    @TableField("buy_count")
    private BigDecimal buyCount;

    /**
     * 原价  buy_count*charge_fee
     */
    @TableField("original_fee")
    private BigDecimal originalFee;

    /**
     * 生效时间
     */
    @TableField("begin_date")
    private String beginDate;

    /**
     * 失效时间
     */
    @TableField("end_date")
    private String endDate;

    /**
     * 过期时间
     */
    @TableField("expire_date")
    private String expireDate;

    /**
     * 优惠编号
     */
    @TableField("discount_id")
    private Long discountId;

    /**
     * 活动名称
     */
    @TableField("activity_name")
    private String activityName;

    /**
     * 折扣类型
     */
    @TableField("discount_type")
    private String discountType;

    /**
     * 优惠明细编号
     */
    @TableField("discount_detail_id")
    private Long discountDetailId;

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

    /**
     * 直接折扣
     */
    @TableField("direct_discount")
    private BigDecimal directDiscount;

    /**
     * 直接减免金额
     */
    @TableField("direct_reduce_fee")
    private BigDecimal directReduceFee;

    /**
     * 实际价格  original_fee-满减-直接减免
     */
    @TableField("actual_fee")
    private BigDecimal actualFee;

    /**
     * 内部备注
     */
    @TableField("inside_memo")
    private String insideMemo;

    /**
     * 外部订单备注
     */
    @TableField("outside_memo")
    private String outsideMemo;

    /**
     * 订单状态 1待支付 2已支付 3已作废
     */
    @TableField("order_detail_status")
    private String orderDetailStatus;

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


}
