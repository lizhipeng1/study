package com.study.business.sys.admin.service.impl;

import com.study.business.sys.admin.repo.mapper.SysRoleMenuMapper;
import com.study.business.sys.admin.repo.model.SysRole;
import com.study.business.sys.admin.repo.model.SysRoleMenu;
import com.study.business.sys.admin.service.ISysRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Override
    public void updateRoleMenu(SysRole sysRole,String nowTenantId) {
        //角色菜单关系
        UpdateWrapper uw = new UpdateWrapper<>();
        uw.eq("role_id",sysRole.getRoleId());
        this.remove(uw);
        if(null != sysRole.getMenuIds() && sysRole.getMenuIds().length>0){
            List<SysRoleMenu> sysRoleMenuList = Lists.newArrayList();
            for (String menuId : sysRole.getMenuIds()) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(sysRole.getRoleId());
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setTenantId(nowTenantId);
                sysRoleMenuList.add(sysRoleMenu);
            }
            this.saveBatch(sysRoleMenuList);
        }
    }
}
