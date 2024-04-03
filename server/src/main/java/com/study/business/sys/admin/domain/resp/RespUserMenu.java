package com.study.business.sys.admin.domain.resp;

import com.study.business.sys.admin.repo.model.SysMenu;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * 用户菜单
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 10:35
 */
@Data
@Builder
public class RespUserMenu implements Serializable {

    private static Map<String, List<RespUserMenu>> subTreeMap = new HashMap<>();

    @JsonIgnore
    private String id;

    @JsonIgnore
    private String parentId;

    private boolean hidden;

    private String path;
    private String component;
    private String name;
    private Meta meta;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<RespUserMenu> children;

    @Data
    class Meta implements Serializable {
        private String title;
        private String icon;
    }

    /**
     * 菜单转换
     *
     * @param sysMenuList 菜单列表
     */
    public void converterMenu(List<SysMenu> sysMenuList) {
        subTreeMap.clear();
        for (SysMenu menu : sysMenuList) {
            List<RespUserMenu> subTreeList = subTreeMap.get(menu.getParentId());
            if (null == subTreeList) {
                subTreeList = new LinkedList<>();
                subTreeMap.put(menu.getParentId(), subTreeList);
            }

            Meta meta = new Meta();
            meta.setTitle(menu.getMenuName());
            meta.setIcon(menu.getIcon());

            if (menu.getParentId().equals("-1")) {
                menu.setComponent("Layout");
                menu.setRouterPath("/" + menu.getRouterPath());
            }

            RespUserMenu item = RespUserMenu.builder()
                    .id(menu.getMenuId())
                    .parentId(menu.getParentId())
                    .path(menu.getRouterPath())
                    .component(menu.getComponent())
                    .name(menu.getMenuName())
                    .meta(meta)
                    .hidden("0".equals(menu.getIsShow()))
                    .children(new ArrayList<>())
                    .build();

            subTreeList.add(item);
        }

        this.children = treeDepth(this).getChildren();
    }

    /**
     * 深度优先 遍历菜单树
     *
     * @param userMenu
     * @return
     */
    private RespUserMenu treeDepth(RespUserMenu userMenu) {
        String id = userMenu.getId();
        List<RespUserMenu> childMenuList = subTreeMap.get(id);
        if (null != childMenuList) {
            List<RespUserMenu> childList = Lists.newLinkedList();
            for (RespUserMenu childMenu : childMenuList) {
                RespUserMenu child = treeDepth(childMenu);
                childList.add(child);
            }
            userMenu.setChildren(childList);
            return userMenu;
        } else {
            return userMenu;
        }
    }


}
