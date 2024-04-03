package com.study.business.sys.admin.repo.mapper;

import com.study.business.sys.admin.domain.req.ReqSearchSysUser;
import com.study.business.sys.admin.repo.model.SysUser;
import com.study.core.page.RespPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUser> selectUserList(@Param("reqSearchSysUser") ReqSearchSysUser reqSearchSysUser, @Param("page") RespPage page);

    /**
     * 用户角色count
     *
     * @param userId
     * @param roleCode
     * @return
     */
    int selectSuperTenantUserRoleCount(@Param("userId") String userId, @Param("roleCode") String roleCode);

    /**
     * 用户角色count
     * @param userId
     * @param tenantId
     * @param roleCodes
     * @return
     */
    int selectUserHasAnyRoleCount(@Param("userId") String userId, @Param("tenantId") String tenantId, @Param("roleCodes") String[] roleCodes);
}
