package com.study.base.business.wechat.cp.repo.model;

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
 * 企业应用信息
 * </p>
 *
 * @author zhangby
 * @since 2024-01-23 06:59:58
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wechat_cp_account")
public class WechatCpAccount implements Serializable {


    @TableId(value = "cp_account_id", type = IdType.AUTO)
    private Integer cpAccountId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 企业名称
     */
    @TableField("company_name")
    private String companyName;

    /**
     * 企业ID
     */
    @TableField("corp_id")
    private String corpId;

    /**
     * 应用id
     */
    @TableField("agent_id")
    private Integer agentId;

    /**
     * 应用密钥
     */
    @TableField("agent_secret")
    private String agentSecret;

    /**
     * 回调url
     */
    @TableField("call_back_url")
    private String callBackUrl;

    /**
     * 回调token
     */
    @TableField("call_back_token")
    private String callBackToken;

    /**
     * 回调Key
     */
    @TableField("call_back_key")
    private String callBackKey;

    /**
     * 状态 1在用 0停用
     */
    @TableField("in_use")
    private String inUse;

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
