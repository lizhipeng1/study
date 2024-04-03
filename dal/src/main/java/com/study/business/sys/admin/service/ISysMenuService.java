package com.study.business.sys.admin.service;

import com.study.business.sys.admin.repo.model.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 用户所有权限
     *
     * @param userId
     * @param tenantId
     * @return
     */
    @Cacheable(value = "USER_PERMISSION_META", key = "#userId+':'+#tenantId", sync = true)
    List<String> selectUserPermissions(String userId, String tenantId);

    /**
     * 系统所有需限制的请求路径
     *
     * @return
     */
    List<SysMenu> selectAllPermissionUrl();

    /**
     * 用户菜单
     *
     * @param userId
     * @param tenantId
     * @return
     */
    List<SysMenu> selectUserMenuList(String userId, String tenantId);

    /**
     * 超级用户菜单
     * @return
     */
    List<SysMenu> selectSuperUserMenuList();

    /**
     * 角色对应的菜单Id列表
     *
     * @param roleId
     * @return
     */
    List<String> selectRoleMenuTreeIdList(Long roleId);
}
