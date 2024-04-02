package com.study.base.business.sys.admin.service.impl;

import cn.xluobo.business.sys.admin.repo.mapper.SysTenantMapper;
import cn.xluobo.business.sys.admin.repo.model.SysTenant;
import cn.xluobo.business.sys.admin.service.ISysTenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 租户信息 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
@Service
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements ISysTenantService {

    @Override
    public List<SysTenant> selectUserTenantList(String userId, String limitTenantUserId) {
        return baseMapper.selectUserTenantList(userId, limitTenantUserId);
    }

    @Override
    public boolean checkUserTenantInUse(String userId, String tenantId) {
        if (StringUtils.isAnyEmpty(userId, tenantId)) {
            return false;
        }
        SysTenant sysTenant = baseMapper.selectInUseUserTenant(userId, tenantId);
        return sysTenant != null;
    }
}
