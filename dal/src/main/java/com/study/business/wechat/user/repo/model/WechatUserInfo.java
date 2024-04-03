package com.study.business.wechat.user.repo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 微信用户信息
 * </p>
 *
 * @author zhangby
 * @since 2020-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wechat_user_info")
public class WechatUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "wechat_user_id", type = IdType.ASSIGN_ID)
    private Long wechatUserId;

    /**
     * 用户openId
     */
    @TableField("open_id")
    private String openId;

    /**
     * 所属app
     */
    @TableField("app_id")
    private String appId;

    /**
     * 是否关注 0 未关注，1 关注
     */
    @TableField("subscribe")
    private String subscribe;

    /**
     * 用户昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 性别 0：未知、1：男、2：女
     */
    @TableField("sex")
    private String sex;

    @TableField("city")
    private String city;

    @TableField("country")
    private String country;

    @TableField("province")
    private String province;

    @TableField("language")
    private String language;

    @TableField("avatar_url")
    private String avatarUrl;

    @TableField("subscribe_time")
    private String subscribeTime;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    @TableField("union_id")
    private String unionId;

    @TableField("remark")
    private String remark;

    @TableField("groupid")
    private String groupid;

    @TableField("tagid_list")
    private String tagidList;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("last_update_time")
    private Date lastUpdateTime;


}
