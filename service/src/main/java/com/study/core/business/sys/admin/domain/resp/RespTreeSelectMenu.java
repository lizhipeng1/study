package com.study.core.business.sys.admin.domain.resp;

import cn.xluobo.business.sys.admin.repo.model.SysMenu;
import com.google.common.collect.Lists;

import java.util.*;

/**
 * 菜单select
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 22:14
 */
public class RespTreeSelectMenu extends RespTreeSelect {

    private static Map<String, List<RespTreeSelect>> subTreeMap = new HashMap<>();

    public RespTreeSelectMenu() {
        super();
    }

    /**
     * 转换菜单为tree select
     * @param sysMenuList
     */
    public void converterMenu(List<SysMenu> sysMenuList){
        subTreeMap.clear();
        for (SysMenu menu : sysMenuList) {
            List<RespTreeSelect> subTreeList = subTreeMap.get(menu.getParentId());
            if (null == subTreeList) {
                subTreeList = new LinkedList<>();
                subTreeMap.put(menu.getParentId(), subTreeList);
            }

            RespTreeSelect item = RespTreeSelect.builder()
                    .id(menu.getMenuId())
                    .label(menu.getMenuName())
                    .children(new ArrayList<>())
                    .build();

            subTreeList.add(item);
        }

        super.setChildren(treeDepth(this).getChildren());
    }

    /**
     * 深度优先 遍历菜单树
     *
     * @param userMenu
     * @return
     */
    private RespTreeSelect treeDepth(RespTreeSelect userMenu) {
        String id = userMenu.getId();
        List<RespTreeSelect> childMenuList = subTreeMap.get(id);
        if (null != childMenuList) {
            List<RespTreeSelect> childList = Lists.newLinkedList();
            for (RespTreeSelect childMenu : childMenuList) {
                RespTreeSelect child = treeDepth(childMenu);
                childList.add(child);
            }
            userMenu.setChildren(childList);
            return userMenu;
        } else {
            return userMenu;
        }
    }
}
