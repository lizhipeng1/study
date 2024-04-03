package com.study.business.sys.admin.domain;

import com.study.business.sys.admin.domain.resp.RespUserMenu;
import com.study.business.sys.admin.repo.model.SysMenu;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 13:16
 */
public class RespUserMenuTest {

    @Test
    public void converterMenu() {

        List<SysMenu> sysMenuList = Lists.newLinkedList();

        SysMenu menu1 = new SysMenu();
        menu1.setMenuId("1");
        menu1.setParentId("-1");
        menu1.setMenuName("菜单1");
        sysMenuList.add(menu1);

        SysMenu menu2 = new SysMenu();
        menu2.setMenuId("2");
        menu2.setParentId("-1");
        menu2.setMenuName("菜单2");
        sysMenuList.add(menu2);


        SysMenu menu11 = new SysMenu();
        menu11.setMenuId("11");
        menu11.setParentId("1");
        menu11.setMenuName("菜单11");
        sysMenuList.add(menu11);

        SysMenu menu12 = new SysMenu();
        menu12.setMenuId("12");
        menu12.setParentId("1");
        menu12.setMenuName("菜单12");
        sysMenuList.add(menu12);

        SysMenu menu111 = new SysMenu();
        menu111.setMenuId("111");
        menu111.setParentId("11");
        menu111.setMenuName("菜单111");
        sysMenuList.add(menu111);

        RespUserMenu userMenu = RespUserMenu.builder().id("-1").build();
        userMenu.converterMenu(sysMenuList);
        System.out.println(JSON.toJSONString(userMenu.getChildren()));
    }
}
