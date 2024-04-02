package com.study.base.business.sys.admin.service.impl;

import cn.xluobo.business.sys.admin.repo.mapper.SysMenuMapper;
import cn.xluobo.business.sys.admin.repo.model.SysMenu;
import cn.xluobo.business.sys.admin.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {


    @Override
    public List<String> selectUserPermissions(String userId, String tenantId) {
        return baseMapper.selectUserMenuPermissionMeta(userId, tenantId);
    }

    @Override
    public List<SysMenu> selectAllPermissionUrl() {
        return baseMapper.selectAllMenuPermissionUrl();
    }

    @Override
    public List<SysMenu> selectUserMenuList(String userId, String tenantId) {
        return baseMapper.selectUserMenuList(userId, tenantId);
    }

    @Override
    public List<SysMenu> selectSuperUserMenuList() {
        return baseMapper.selectSuperUserMenuList();
    }

    @Override
    public List<String> selectRoleMenuTreeIdList(Long roleId) {
        return baseMapper.selectRoleMenuTreeIdList(roleId);
    }
}
