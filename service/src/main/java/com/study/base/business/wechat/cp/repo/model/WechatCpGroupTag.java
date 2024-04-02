package com.study.base.business.wechat.cp.repo.model;

import cn.xluobo.core.domain.select.GroupSelect;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.chanjar.weixin.cp.bean.external.WxCpUserExternalTagGroupList;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 企业微信标签
 * </p>
 *
 * @author xluobo
 * @since 2024-01-25 05:55:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wechat_cp_group_tag")
public class WechatCpGroupTag implements Serializable {


    /**
     * 微信端返回的id
     */
    @TableId(value = "tag_id", type = IdType.INPUT)
    private String tagId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 标签组id
     */
    @TableField("group_id")
    private String groupId;

    /**
     * 标签名
     */
    @TableField("name")
    private String name;

    /**
     * 标签组排序的次序值，order值大的排序靠前,有效的值范围是[0, 2^32)
     */
    @TableField("tag_order")
    private Long tagOrder;

    /**
     * 标签组是否已经被删除，只在指定tag_id进行查询时返回
     */
    @TableField("delete_flag")
    @TableLogic
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
    private Long createTime;

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
     *
     * @param tag
     * @return
     */
    public static WechatCpGroupTag transferByCpBean(String groupId, WxCpUserExternalTagGroupList.TagGroup.Tag tag) {
        WechatCpGroupTag groupTag = new WechatCpGroupTag();
        groupTag.setTagId(tag.getId());
        groupTag.setGroupId(groupId);
        groupTag.setName(tag.getName());
        groupTag.setTagOrder(tag.getOrder());
        groupTag.setDeleteFlag((null != tag.getDeleted() && tag.getDeleted()) ? "1" : "0");
        groupTag.setCreateTime(tag.getCreateTime());
        return groupTag;
    }

    /**
     * 转换为group select item
     * @return GroupSelect.GroupItem
     */
    public GroupSelect.GroupItem transferGroupSelectItem() {
        GroupSelect.GroupItem groupItem = new GroupSelect.GroupItem();
        groupItem.setItemId(this.tagId);
        groupItem.setItemName(this.name);
        return groupItem;
    }
}
