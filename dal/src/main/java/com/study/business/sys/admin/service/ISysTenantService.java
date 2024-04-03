package com.study.business.sys.admin.service;

import com.study.business.sys.admin.repo.model.SysTenant;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 租户信息 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
public interface ISysTenantService extends IService<SysTenant> {

    /**
     * 用户租户列表
     * @param userId
     * @param limitTenantUserId
     * @return
     */
    List<SysTenant> selectUserTenantList(String userId,String limitTenantUserId);

    /**
     * 校验用户所属租户是否在用生效
     * @param userId
     * @param tenantId
     * @return
     */
    boolean checkUserTenantInUse(String userId,String tenantId);
}
