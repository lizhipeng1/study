package com.study.business.ad.intention.repo.model;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 申请试用表
 * </p>
 *
 * @author zhangby
 * @since 2022-03-13 09:43:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ad_intention_order")
public class AdIntentionOrder implements Serializable {


    @TableId(value = "order_id", type = IdType.ASSIGN_ID)
    private String orderId;

    /**
     * 机构名称
     */
    @TableField("org_name")
    private String orgName;

    /**
     * 联系人
     */
    @TableField("contact")
    private String contact;

    /**
     * 联系电话
     */
    @TableField("contact_phone")
    private String contactPhone;

    /**
     * 申请时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 申请试用说明
     */
    @TableField("intention_memo")
    private String intentionMemo;

    /**
     * 是否开通账号1开通 0未开通
     */
    @TableField("account_open")
    private Integer accountOpen;

    /**
     * 是否已处理完毕 1已处理 0未处理
     */
    @TableField("had_deal")
    private Integer hadDeal;

    /**
     * 处理结果类型
     */
    @TableField("deal_result_type")
    private String dealResultType;

    /**
     * 处理结果
     */
    @TableField("deal_result")
    private String dealResult;

    /**
     * 处理时间
     */
    @TableField("deal_time")
    private Date dealTime;

    /**
     * 账号信息
     */
    @TableField("user_id")
    private String userId;

    /**
     * 账号信息
     */
    @TableField("user_name")
    private String userName;

    /**
     * 账号开通时间
     */
    @TableField("account_open_date")
    private Date accountOpenDate;
}
