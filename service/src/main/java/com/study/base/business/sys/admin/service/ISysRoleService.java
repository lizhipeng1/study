package com.study.base.business.sys.admin.service;

import cn.xluobo.business.sys.admin.repo.model.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-30 11:51:28
 */
public interface ISysRoleService extends IService<SysRole> {

    List<SysRole> selectUserRoleList(String userId, String tenantId);

    /**
     * 是否存在子节点
     * @param roleId
     * @return
     */
    boolean hadChild(Long roleId);

    boolean hadUser(Long roleId);



    /**
     * 修改子元素关系
     * @param roleId 被修改的ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    void updateSysRoleChildren(Long roleId, String newAncestors, String oldAncestors);

    /**
     * 用户对应的角色Id列表
     *
     * @param userId
     * @param tenantId
     * @return
     */
    List<String> selectUserRoleTreeIdList(String userId, String tenantId);

    /**
     * 根据编码 获取角色
     * @param roleCode
     * @return
     */
    SysRole selectByRoleCode(String roleCode);
}
