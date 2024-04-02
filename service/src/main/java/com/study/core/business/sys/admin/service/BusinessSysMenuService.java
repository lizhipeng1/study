package com.study.core.business.sys.admin.service;

import cn.xluobo.business.sys.admin.domain.req.ReqSearchSysMenu;
import cn.xluobo.business.sys.admin.domain.resp.RespTreeSelect;
import cn.xluobo.business.sys.admin.domain.resp.RespTreeSelectMenu;
import cn.xluobo.business.sys.admin.repo.model.SysMenu;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessSysMenuService {

    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * 查询菜单树
     * @param reqSearchMenu
     * @return
     */
    public List<SysMenu> searchList(ReqSearchSysMenu reqSearchMenu){
        QueryWrapper qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(reqSearchMenu.getMenuName())){
            qw.like("menu_name",reqSearchMenu.getMenuName());
        }
        qw.orderByAsc("sort");
        List<SysMenu> list = sysMenuService.list(qw);
        SysMenu userMenu = new SysMenu();
        userMenu.setMenuId("-1");
        userMenu.converterMenu(list);
        return userMenu.getChildren();
    }

    /**
     * 前端select
     * @return
     */
    public List<RespTreeSelect> treeSelect(){
        SysMenu sysMenu = new SysMenu();
        sysMenu.setIsShow("1");
        QueryWrapper<SysMenu> qw = new QueryWrapper(sysMenu);
        List<SysMenu> list = sysMenuService.list(qw);
        RespTreeSelectMenu selectMenu =  new RespTreeSelectMenu();
        selectMenu.setId("-1");
        selectMenu.converterMenu(list);
        return selectMenu.getChildren();
    }

    /**
     * 前端select
     * @return
     */
    public List<RespTreeSelect> treeSelectIncludeHide(){
        SysMenu sysMenu = new SysMenu();
        QueryWrapper<SysMenu> qw = new QueryWrapper(sysMenu);
        List<SysMenu> list = sysMenuService.list(qw);
        RespTreeSelectMenu selectMenu =  new RespTreeSelectMenu();
        selectMenu.setId("-1");
        selectMenu.converterMenu(list);
        return selectMenu.getChildren();
    }

    /**
     * 菜单详情
     * @param menuId
     * @return
     */
    public SysMenu detailById(String menuId){
        if(StringUtils.isEmpty(menuId)){
            return null;
        }
        return sysMenuService.getById(menuId);
    }

    /**
     * 添加菜单
     * @param sysMenu
     * @return
     */
    public boolean addMenu(SysMenu sysMenu){
        return sysMenuService.save(sysMenu);
    }

    /**
     * 更新菜单
     * @param sysMenu
     * @return
     */
    @CacheEvict(value = "USER_PERMISSION_META", allEntries = true)
    public boolean updateMenu(SysMenu sysMenu){
        if(StringUtils.isEmpty(sysMenu.getMenuId())){
            return false;
        }
        return sysMenuService.updateById(sysMenu);
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    public boolean deleteById(String menuId){
        if(StringUtils.isEmpty(menuId)){
            return false;
        }
        return sysMenuService.removeById(menuId);
    }



    /**
     * 角色对应的菜单Id列表
     * @param roleId
     * @return
     */
    public List<String> selectRoleMenuTreeIdList(Long roleId){
        if(roleId == null){
            return Lists.newArrayList();
        }
        return sysMenuService.selectRoleMenuTreeIdList(roleId);
    }
}
