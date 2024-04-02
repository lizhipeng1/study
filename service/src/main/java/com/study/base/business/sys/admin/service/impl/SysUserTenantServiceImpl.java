package com.study.base.business.sys.admin.service.impl;

import cn.xluobo.business.sys.admin.repo.mapper.SysUserTenantMapper;
import cn.xluobo.business.sys.admin.repo.model.SysUserTenant;
import cn.xluobo.business.sys.admin.service.ISysUserTenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户所属租户 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
@Service
public class SysUserTenantServiceImpl extends ServiceImpl<SysUserTenantMapper, SysUserTenant> implements ISysUserTenantService {

    @Override
    public String selectUserDefaultTenant(String userId) {
        return baseMapper.selectUserDefaultTenant(userId);
    }
}
