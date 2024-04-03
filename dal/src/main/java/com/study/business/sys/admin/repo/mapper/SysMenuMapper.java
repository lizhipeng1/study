package com.study.business.sys.admin.repo.mapper;

import com.study.business.sys.admin.repo.model.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 用户 权限标识
     *
     * 角色对应菜单固定 未通过租户进行区分
     * @param userId
     * @param tenantId
     * @return
     */
    List<String> selectUserMenuPermissionMeta(@Param("userId") String userId, @Param("tenantId") String tenantId);

    /**
     * 系统所有需鉴权url
     * @return
     */
    List<SysMenu> selectAllMenuPermissionUrl();

    /**
     * 用户菜单
     *
     * 角色对应菜单固定 未通过租户进行区分
     * @param userId
     * @param tenantId
     * @return
     */
    List<SysMenu> selectUserMenuList(@Param("userId") String userId, @Param("tenantId") String tenantId);

    /**
     * 超级管理员用户菜单
     * @return
     */
    List<SysMenu> selectSuperUserMenuList();

    /**
     * 角色对应的菜单Id列表
     *
     * @param roleId
     * @return
     */
    List<String> selectRoleMenuTreeIdList(@Param("roleId")Long roleId);

}
