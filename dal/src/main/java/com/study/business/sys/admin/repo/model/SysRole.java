package com.study.business.sys.admin.repo.model;

import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import java.util.*;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author zhangby
 * @since 2020-01-30 11:15:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role")
public class SysRole implements Serializable {


    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;

    @TableField("parent_id")
    private Long parentId;

    /**
     * 祖级列表
     */
    @TableField("ancestors")
    private String ancestors;

    /**
     * 角色编码
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 显示顺序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 状态（1正常 0停用）
     */
    @TableField("in_use")
    private String inUse;

    /**
     * 删除标志（1删除 0在用）
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


    @TableField(exist = false)
    private List<SysRole> children;

    /**
     * 菜单Id
     */
    @TableField(exist = false)
    private String[] menuIds;

    private static Map<Long, List<SysRole>> subTreeMap = new HashMap<>();

    /**
     * 树转换
     * @param sysRoleList
     */
    public void converterTree(List<SysRole> sysRoleList) {
        subTreeMap.clear();
        for (SysRole menu : sysRoleList) {
            List<SysRole> subTreeList = subTreeMap.get(menu.getParentId());
            if (null == subTreeList) {
                subTreeList = new LinkedList<>();
                subTreeMap.put(menu.getParentId(),subTreeList);
            }
            subTreeList.add(menu);
        }

        this.children = treeRole(this).getChildren();
        if(this.children.isEmpty()){
            this.children = sysRoleList;
        }
    }

    /**
     * 深度优先 遍历树
     * @param sysRole
     * @return
     */
    private SysRole treeRole(SysRole sysRole) {
        Long deptId = sysRole.getRoleId();
        List<SysRole> childMenuList = subTreeMap.get(deptId);
        if(null != childMenuList){
            List<SysRole> childList = Lists.newLinkedList();
            for (SysRole childRole : childMenuList) {
                SysRole child = treeRole(childRole);
                childList.add(child);
            }
            sysRole.setChildren(childList);
            return sysRole;
        }else{
            sysRole.setChildren(Lists.newLinkedList());
            return sysRole;
        }
    }
}
