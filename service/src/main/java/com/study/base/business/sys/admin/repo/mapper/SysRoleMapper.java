package com.study.base.business.sys.admin.repo.mapper;

import cn.xluobo.business.sys.admin.repo.model.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-01-30 11:15:50
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectUserRoleList(@Param("userId")String userId, @Param("tenantId")String tenantId);

    /**
     * 角色对应的菜单Id列表
     *
     * @param userId
     * @param tenantId
     * @return
     */
    List<String> selectUserRoleTreeIdList(@Param("userId")String userId, @Param("tenantId")String tenantId);
}
