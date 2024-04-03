package com.study.business.sys.admin.repo.mapper;

import com.study.business.sys.admin.repo.model.SysUserTenant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户所属租户 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
public interface SysUserTenantMapper extends BaseMapper<SysUserTenant> {

    String selectUserDefaultTenant(@Param("userId") String userId);

}
