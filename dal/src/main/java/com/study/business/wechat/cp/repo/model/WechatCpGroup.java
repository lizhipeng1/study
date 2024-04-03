package com.study.business.wechat.cp.repo.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.chanjar.weixin.cp.bean.external.WxCpUserExternalTagGroupList;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 企业微信标签组
 * </p>
 *
 * @author xluobo
 * @since 2024-01-25 05:53:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wechat_cp_group")
public class WechatCpGroup implements Serializable {


    /**
     * 标签组id
     */
    @TableId(value = "group_id", type = IdType.INPUT)
    private String groupId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 标签组名
     */
    @TableField("group_name")
    private String groupName;

    /**
     * 标签分组类型(1:客户企业标签;2:群标签;3:客户个人标签)
     */
    @TableField("group_tag_type")
    private Integer groupTagType;

    /**
     * 标签组排序的次序值，order值大的排序靠前,有效的值范围是[0, 2^32)
     */
    @TableField("group_order")
    private Long groupOrder;

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
     * @param tagGroup
     * @return
     */
    public static WechatCpGroup transferByCpBean(WxCpUserExternalTagGroupList.TagGroup tagGroup) {
        WechatCpGroup wxCpGroup = new WechatCpGroup();
        wxCpGroup.setGroupId(tagGroup.getGroupId());
        wxCpGroup.setGroupName(tagGroup.getGroupName());
        wxCpGroup.setGroupOrder(tagGroup.getOrder());
        wxCpGroup.setDeleteFlag((null != tagGroup.getDeleted() && tagGroup.getDeleted()) ? "1" : "0");
        wxCpGroup.setCreateTime(tagGroup.getCreateTime());

        return wxCpGroup;
    }
}
