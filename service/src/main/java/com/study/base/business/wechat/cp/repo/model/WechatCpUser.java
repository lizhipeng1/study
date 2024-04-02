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
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 企业微信成员
 * </p>
 *
 * @author xluobo
 * @since 2024-01-26 08:08:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wechat_cp_user")
public class WechatCpUser implements Serializable {


    /**
     * 成员UserID
     */
    @TableId(value = "userid", type = IdType.INPUT)
    private String userid;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 成员名称
     */
    @TableField("name")
    private String name;

    /**
     * 手机号码
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 成员所属部门id列表
     */
    @TableField("department")
    private String department;

    /**
     * 部门内的排序值，默认为0。数量必须和department一致，数值越大排序越前面。值范围是[0, 2^32)
     */
    @TableField("user_order")
    private String userOrder;

    /**
     * 职务信息
     */
    @TableField("position")
    private String position;

    /**
     * 性别 0表示未定义，1表示男性，2表示女性
     */
    @TableField("gender")
    private String gender;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 企业邮箱
     */
    @TableField("biz_mail")
    private String bizMail;

    /**
     * 头像url
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业
     */
    @TableField("status")
    private String status;

    /**
     * 主部门，仅当应用对主部门有查看权限时返回
     */
    @TableField("main_department")
    private String mainDepartment;

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

    public static WechatCpUser transferByCpBean(WxCpUser wxCpUser) {
        WechatCpUser wechatCpUser = new WechatCpUser();
        wechatCpUser.setUserid(wxCpUser.getUserId());
        wechatCpUser.setName(wxCpUser.getName());
        wechatCpUser.setMobile(wxCpUser.getMobile());

        wechatCpUser.setDepartment(StringUtils.join(wxCpUser.getDepartIds(), ","));
        wechatCpUser.setUserOrder(StringUtils.join(wxCpUser.getOrders(), ","));
        wechatCpUser.setPosition(wxCpUser.getPosition());
        if (null != wxCpUser.getGender()) {
            wechatCpUser.setGender(wxCpUser.getGender().getCode());
        }
        wechatCpUser.setEmail(wxCpUser.getEmail());
        wechatCpUser.setBizMail(wxCpUser.getBizMail());
        wechatCpUser.setAvatar(wxCpUser.getAvatar());
        if (null != wxCpUser.getStatus()) {
            wechatCpUser.setStatus(wxCpUser.getStatus().toString());
        }
        return wechatCpUser;
    }
}
