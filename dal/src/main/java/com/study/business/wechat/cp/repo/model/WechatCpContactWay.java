package com.study.business.wechat.cp.repo.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import com.study.core.api.APIResponse;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;
import me.chanjar.weixin.cp.bean.external.WxCpContactWayInfo;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 客户联系我方式
 * </p>
 *
 * @author xluobo
 * @since 2024-01-26 07:13:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wechat_cp_contact_way")
public class WechatCpContactWay implements Serializable {


    /**
     * 新增联系方式的配置id
     */
    @TableId(value = "config_id", type = IdType.INPUT)
    private String configId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 联系我二维码链接，仅在scene为2时返回
     */
    @TableField("qr_code")
    private String qrCode;

    /**
     * 名称
     */
    @TableField("contact_way_name")
    private String contactWayName;

    /**
     * 联系方式类型,1-单人, 2-多人
     */
    @TableField("type")
    private Integer type;

    /**
     * 场景，1-在小程序中联系，2-通过二维码联系
     */
    @TableField("scene")
    private Integer scene;

    /**
     * 在小程序中联系时使用的控件样式
     */
    @TableField("style")
    private Integer style;

    /**
     * 联系方式的备注信息，用于助记，不超过30个字符
     */
    @TableField("remark")
    private String remark;

    /**
     * 外部客户添加时是否无需验证，默认为true
     */
    @TableField("skip_verify")
    private Boolean skipVerify;

    /**
     * 企业自定义的state参数，用于区分不同的添加渠道
     */
    @TableField("state")
    private String state;

    /**
     * 使用该联系方式的用户userID列表，在type为1时为必填，且只能有一个
     */
    @TableField("user")
    private String user;

    /**
     * 使用该联系方式的部门id列表，只在type为2时有效
     */
    @TableField("party")
    private String party;

    /**
     * 需要自动添加的标签id
     */
    @TableField("add_tag")
    private String addTag;

    /**
     * 是否临时会话模式，true表示使用临时会话模式，默认为false
     */
    @TableField("is_temp")
    private Boolean isTemp;

    /**
     * 临时会话二维码有效期，以秒为单位。该参数仅在is_temp为true时有效，默认7天，最多为14天
     */
    @TableField("expires_in")
    private Integer expiresIn;

    /**
     * 临时会话有效期，以秒为单位。该参数仅在is_temp为true时有效，默认为添加好友后24小时，最多为14天
     */
    @TableField("chat_expires_in")
    private Integer chatExpiresIn;

    /**
     * 可进行临时会话的客户unionid，该参数仅在is_temp为true时有效，如不指定则不进行限制
     */
    @TableField("unionid")
    private String unionid;

    /**
     * 是否开启同一外部企业客户只能添加同一个员工，默认为否，开启后，同一个企业的客户会优先添加到同一个跟进人
     */
    @TableField("unionidis_exclusive")
    private Boolean unionidisExclusive;

    /**
     * 结束语，会话结束时自动发送给客户，可参考“结束语定义”，仅在is_temp为true时有效
     */
    @TableField("conclusions")
    private String conclusions;

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

    public void setAddTags(String[] addTag) {
        if (null != addTag && addTag.length > 0) {
            this.addTag = String.join(",", addTag);
        }
    }

    public void setUsers(String[] user) {
        if (null != user && user.length > 0) {
            this.user = String.join(",", user);
        }
    }

    public String[] getUsers() {
        if(StringUtils.isNotEmpty(user)) {
            return user.split(",");
        }
        return new String[]{};
    }

    public String[] getAddTags() {
        if(StringUtils.isNotEmpty(addTag)) {
            return addTag.split(",");
        }
        return new String[]{};
    }

    /**
     * 校验参数
     *
     * @return
     */
    public APIResponse checkParam() {
        if (null == type) {
            return APIResponse.toExceptionResponse("请选择联系方式类型");
        } else if (null == scene) {
            return APIResponse.toExceptionResponse("请选择联系场景");
        } else if (StringUtils.isAnyEmpty(remark, state)) {
            return APIResponse.toExceptionResponse("请填写必要参数");
        } else if (StringUtils.isAllEmpty(user, party)) {
            return APIResponse.toExceptionResponse("请选择可添加的联系人或部门");
        }
        return APIResponse.toOkResponse();
    }

    /**
     * db实体类 转换为 企业微信接口
     *
     * @return
     */
    public WxCpContactWayInfo transferWxCpContactWay() {
        WxCpContactWayInfo wxCpContactWayInfo = new WxCpContactWayInfo();
        WxCpContactWayInfo.ContactWay contactWay = new WxCpContactWayInfo.ContactWay();
        contactWay.setConfigId(this.configId);
        if (1 == this.type) {
            contactWay.setType(WxCpContactWayInfo.TYPE.SINGLE);
        } else if (2 == this.type) {
            contactWay.setType(WxCpContactWayInfo.TYPE.MULTI);
        }
        if (1 == this.scene) {
            contactWay.setScene(WxCpContactWayInfo.SCENE.MINIPROGRAM);
        } else if (2 == this.scene) {
            contactWay.setScene(WxCpContactWayInfo.SCENE.QRCODE);
        }
        contactWay.setStyle(this.type);
        contactWay.setRemark(this.contactWayName);
        contactWay.setSkipVerify(this.skipVerify);
        contactWay.setState(this.state);
        if (StringUtils.isNotEmpty(this.user)) {
            contactWay.setUsers(Arrays.asList(this.user.split(",")));
        }
        if (StringUtils.isNotEmpty(this.party)) {
            contactWay.setParties(Arrays.asList(this.party.split(",")));
        }

        contactWay.setIsTemp(this.isTemp);
        if (this.isTemp) {
            contactWay.setExpiresIn(this.expiresIn);
            contactWay.setChatExpiresIn(this.chatExpiresIn);
            contactWay.setUnionId(this.unionid);
            if (StringUtils.isNotEmpty(this.conclusions)) {
                contactWay.setConclusions(JSONObject.parseObject(this.conclusions, WxCpContactWayInfo.ContactWay.Conclusion.class));
            }
        }

        wxCpContactWayInfo.setContactWay(contactWay);
        return wxCpContactWayInfo;
    }
}
