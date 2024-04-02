package com.study.base.business.sys.admin.repo.mapper;

import cn.xluobo.business.sys.admin.repo.model.SysTenant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 租户信息 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
public interface SysTenantMapper extends BaseMapper<SysTenant> {

    /**
     * 用户租户列表
     *
     * @param userId
     * @return
     */
    List<SysTenant> selectUserTenantList(@Param("userId")String userId, @Param("limitTenantUserId")String limitTenantUserId);

    /**
     * 在用用户所属的租户
     *
     * @param userId
     * @param tenantId
     * @return
     */
    SysTenant selectInUseUserTenant(@Param("userId")String userId, @Param("tenantId")String tenantId);
}
