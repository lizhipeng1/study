package com.study.business.wechat.cp.repo.model;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;
import me.chanjar.weixin.cp.bean.external.contact.FollowedUser;

/**
 * <p>
 * 员工给外部联系人添加的标签
 * </p>
 *
 * @author xluobo
 * @since 2024-01-25 10:46:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wechat_cp_contact_follow_tag")
public class WechatCpContactFollowTag implements Serializable {


    /**
     * 逐渐
     */
    @TableId(value = "follow_tag_id", type = IdType.ASSIGN_ID)
    private Long followTagId;

    /**
     * 外部联系人的userid
     */
    @TableField("external_user_id")
    private String externalUserId;

    /**
     * 添加了此外部联系人的企业成员userid
     */
    @TableField("cp_user_id")
    private String cpUserId;

    /**
     * 该成员添加此外部联系人所打标签的分组名称
     */
    @TableField("group_name")
    private String groupName;

    /**
     * 该成员添加此外部联系人所打标签名称
     */
    @TableField("tag_name")
    private String tagName;

    /**
     * 该成员添加此外部联系人所打标签类型, 1-企业设置，2-用户自定义，3-规则组标签
     */
    @TableField("type")
    private Integer type;

    /**
     * 该成员添加此外部联系人所打企业标签的id，用户自定义类型标签（type=2）不返回
     */
    @TableField("tag_id")
    private String tagId;

    /**
     * 接口返回信息转换为数据库存储信息
     * @param externalUserId
     * @param followedUser
     * @return
     */
    public static List<WechatCpContactFollowTag> transferByCpBean(String externalUserId,
                                                                  FollowedUser followedUser) {
        List<WechatCpContactFollowTag> followTagList = Lists.newArrayList();

        FollowedUser.Tag[] userTags = followedUser.getTags();
        if (null == userTags) {
            return followTagList;
        }
        for (FollowedUser.Tag userTag : userTags) {
            WechatCpContactFollowTag followTag = new WechatCpContactFollowTag();
            followTag.setExternalUserId(externalUserId);
            followTag.setCpUserId(followedUser.getUserId());
            followTag.setGroupName(userTag.getGroupName());
            followTag.setTagName(userTag.getTagName());
            followTag.setType(userTag.getType());
            followTag.setTagId(userTag.getTagId());

            followTagList.add(followTag);
        }


        return followTagList;
    }
}
