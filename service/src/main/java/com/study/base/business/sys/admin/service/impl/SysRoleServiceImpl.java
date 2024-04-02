package com.study.base.business.sys.admin.service.impl;

import cn.xluobo.business.sys.admin.repo.model.SysRole;
import cn.xluobo.business.sys.admin.repo.mapper.SysRoleMapper;
import cn.xluobo.business.sys.admin.service.ISysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-30 11:51:28
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Override
    public List<SysRole> selectUserRoleList(String userId, String tenantId) {
        return baseMapper.selectUserRoleList(userId, tenantId);
    }

    /**
     * 是否存在子节点
     *
     * @param roleId
     * @return
     */
    @Override
    public boolean hadChild(Long roleId) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("parent_id", roleId);
        List list = list(qw);
        if (list.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean hadUser(Long roleId) {
        return false;
    }

    @Override
    public void updateSysRoleChildren(Long roleId, String newAncestors, String oldAncestors) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("role_id", roleId);
        List<SysRole> childrenList = list(qw);
        if (!childrenList.isEmpty()) {
            for (SysRole children : childrenList) {
                children.setAncestors(children.getAncestors().replace(oldAncestors, newAncestors));
            }
            updateBatchById(childrenList);
        }
    }

    @Override
    public List<String> selectUserRoleTreeIdList(String userId, String tenantId) {
        return baseMapper.selectUserRoleTreeIdList(userId, tenantId);
    }

    @Override
    public SysRole selectByRoleCode(String roleCode) {
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        qw.eq("role_code", roleCode);
        return this.getOne(qw);
    }
}
