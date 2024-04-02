package com.study.base.business.mk.discount.repo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 优惠配置
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mk_discount")
public class MkDiscount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 优惠编号
     */
    @TableId(value = "discount_id", type = IdType.ASSIGN_ID)
    private Long discountId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 活动名称
     */
    @TableField("activity_name")
    private String activityName;

    /**
     * 折扣类型 1满减 2满折 3满赠
     */
    @TableField("discount_type")
    private String discountType;

    /**
     * 折扣开始时间
     */
    @TableField("begin_date")
    private Date beginDate;

    /**
     * 结束时间
     */
    @TableField("end_date")
    private Date endDate;

    /**
     * 是否生效
     */
    @TableField("enable")
    private Integer enable;

    /**
     * 版本号
     */
    @TableField("version")
    private Integer version;

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
