package com.study.base.business.sys.admin.repo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.*;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @TableId(value = "menu_id")
    private String menuId;

    /**
     * 父菜单ID
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 菜单类型（dir menu button）
     */
    @TableField("menu_type")
    private String menuType;

    /**
     * 路由地址
     */
    @TableField("router_path")
    private String routerPath;

    /**
     * 组件路径
     */
    @TableField("component")
    private String component;
    /**
     * 是否为外链（0否 1是）
     */
    @TableField("out_url")
    private String outUrl;

    /**
     * 请求路径
     */
    @TableField("request_url")
    private String requestUrl;

    /**
     * 是否显示（1显示 0隐藏）
     */
    @TableField("is_show")
    private String isShow;

    /**
     * 权限标识
     */
    @TableField("permission_meta")
    private String permissionMeta;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 显示顺序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 是否在用（1在用 0非在用）
     */
    @TableField("enable")
    private String enable;

    @TableField(exist = false)
    private List<SysMenu> children;

    private static Map<String, List<SysMenu>> subTreeMap = new HashMap<>();

    /**
     * 菜单转换
     * @param sysMenuList 菜单列表
     */
    public void converterMenu(List<SysMenu> sysMenuList) {
        subTreeMap.clear();
        for (SysMenu menu : sysMenuList) {
            List<SysMenu> subTreeList = subTreeMap.get(menu.getParentId());
            if (null == subTreeList) {
                subTreeList = new LinkedList<>();
                subTreeMap.put(menu.getParentId(),subTreeList);
            }
            subTreeList.add(menu);
        }

        this.children = treeDepth(this).getChildren();
        if(this.children.isEmpty()){
            this.children = sysMenuList;
        }
    }

    /**
     * 深度优先 遍历菜单树
     * @param userMenu
     * @return
     */
    private SysMenu treeDepth(SysMenu userMenu) {
        String id = userMenu.getMenuId();
        List<SysMenu> childMenuList = subTreeMap.get(id);
        if(null != childMenuList){
            List<SysMenu> childList = Lists.newLinkedList();
            for (SysMenu childMenu : childMenuList) {
                SysMenu child = treeDepth(childMenu);
                childList.add(child);
            }
            userMenu.setChildren(childList);
            return userMenu;
        }else{
            userMenu.setChildren(Lists.newLinkedList());
            return userMenu;
        }
    }


}
