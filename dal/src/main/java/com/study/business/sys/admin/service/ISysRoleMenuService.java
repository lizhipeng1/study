package com.study.business.sys.admin.service;

import com.study.business.sys.admin.repo.model.SysRole;
import com.study.business.sys.admin.repo.model.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.CacheEvict;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {


    /**
     * 更新角色对应的菜单
     *
     * @param sysRole
     * @param nowTenantId
     * @return
     */
    @CacheEvict(value = "USER_PERMISSION_META", allEntries = true)
    void updateRoleMenu(SysRole sysRole, String nowTenantId);

}
