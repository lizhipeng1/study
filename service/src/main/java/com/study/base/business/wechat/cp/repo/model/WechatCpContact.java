package com.study.base.business.wechat.cp.repo.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.chanjar.weixin.cp.bean.external.contact.ExternalContact;

/**
 * <p>
 * 企业微信客户信息
 * </p>
 *
 * @author xluobo
 * @since 2024-01-25 05:53:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wechat_cp_contact")
public class WechatCpContact implements Serializable {


    /**
     * 外部联系人的userid
     */
    @TableId(value = "external_user_id", type = IdType.INPUT)
    private String externalUserId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 企业ID
     */
    @TableField("corp_id")
    private String corpId;

    /**
     * 外部联系人的名称
     */
    @TableField("name")
    private String name;

    /**
     * 外部联系人头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 外部联系人的类型，1表示该外部联系人是微信用户，2表示该外部联系人是企业微信用户
     */
    @TableField("type")
    private Integer type;

    /**
     * 外部联系人性别 0-未知 1-男性 2-女性
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 外部联系人在微信开放平台的唯一身份标识（微信unionid）
     */
    @TableField("unionid")
    private String unionid;

    /**
     * 外部联系人的职位
     */
    @TableField("position")
    private String position;

    /**
     * 外部联系人所在企业的简称
     */
    @TableField("corp_name")
    private String corpName;

    /**
     * 外部联系人所在企业的主体名称
     */
    @TableField("corp_full_name")
    private String corpFullName;

    /**
     * 删除标志（1删除 0在用）
     */
    @TableField("delete_flag")
    private String deleteFlag;

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
     * 接口返回信息转换为数据库存储信息
     * @param externalContact
     * @return
     */
    public static WechatCpContact transferByCpBean(ExternalContact externalContact) {
        WechatCpContact wechatCpContact = new WechatCpContact();
        wechatCpContact.setExternalUserId(externalContact.getExternalUserId());
        wechatCpContact.setName(externalContact.getName());
        wechatCpContact.setAvatar(externalContact.getAvatar());
        wechatCpContact.setType(externalContact.getType());
        wechatCpContact.setGender(externalContact.getGender());
        wechatCpContact.setUnionid(externalContact.getUnionId());
        wechatCpContact.setPosition(externalContact.getPosition());
        wechatCpContact.setCorpName(externalContact.getCorpName());
        wechatCpContact.setCorpFullName(externalContact.getCorpFullName());
        wechatCpContact.setDeleteFlag("0");
        return wechatCpContact;
    }
}
