package com.study.business.sys.admin.service;

import com.study.business.sys.admin.repo.model.SysUserTenant;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户所属租户 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
public interface ISysUserTenantService extends IService<SysUserTenant> {

    String selectUserDefaultTenant(@Param("userId") String userId);
}
