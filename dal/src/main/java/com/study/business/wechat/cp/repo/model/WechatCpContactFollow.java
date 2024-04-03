package com.study.business.wechat.cp.repo.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;
import me.chanjar.weixin.cp.bean.external.contact.FollowedUser;

/**
 * <p>
 * 外部联系人对应大员工信息
 * </p>
 *
 * @author xluobo
 * @since 2024-01-25 05:53:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wechat_cp_contact_follow")
public class WechatCpContactFollow implements Serializable {


    /**
     * 外部联系人的userid
     */
    @TableId(value = "external_user_id", type = IdType.INPUT)
    private String externalUserId;

    /**
     * 添加了此外部联系人的企业成员userid
     */
    @TableId(value = "cp_user_id", type = IdType.INPUT)
    private String cpUserId;

    /**
     * 此外部联系人的备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 外部联系人的描述
     */
    @TableField("description")
    private String description;

    /**
     * 外部联系人的描述
     */
    @TableField("create_time")
    private Long createTime;

    /**
     * 该成员对此微信客户备注的企业名称（仅微信客户有该字段）
     */
    @TableField("remark_corp_name")
    private String remarkCorpName;

    /**
     * 该成员对此客户备注的手机号码
     */
    @TableField("remark_mobiles")
    private String remarkMobiles;

    /**
     * 该成员添加此客户的来源
     */
    @TableField("add_way")
    private String addWay;

    /**
     * 发起添加的userid，如果成员主动添加，为成员的userid；如果是客户主动添加，则为客户的外部联系人userid；如果是内部成员共享/管理员分配，则为对应的成员/管理员userid
     */
    @TableField("oper_userid")
    private String operUserid;

    /**
     * 企业自定义的state参数，用于区分客户具体是通过哪个「联系我」或获客链接添加
     */
    @TableField("state")
    private String state;

    /**
     * 接口返回信息转换为数据库存储信息
     * @param externalUserId
     * @param followedUser
     * @return
     */
    public static WechatCpContactFollow transferByCpBean(String externalUserId,
                                                         FollowedUser followedUser) {
        WechatCpContactFollow wechatCpContactFollow = new WechatCpContactFollow();
        wechatCpContactFollow.setExternalUserId(externalUserId);
        wechatCpContactFollow.setCpUserId(followedUser.getUserId());
        wechatCpContactFollow.setRemark(followedUser.getRemark());
        wechatCpContactFollow.setDescription(followedUser.getDescription());
        wechatCpContactFollow.setCreateTime(followedUser.getCreateTime());
        wechatCpContactFollow.setRemarkCorpName(followedUser.getRemarkCorpName());
        wechatCpContactFollow.setRemarkMobiles(String.join(",",followedUser.getRemarkMobiles()));
        wechatCpContactFollow.setAddWay(followedUser.getAddWay());
        wechatCpContactFollow.setOperUserid(followedUser.getOperatorUserId());
        wechatCpContactFollow.setState(followedUser.getState());
        return wechatCpContactFollow;
    }
}
