package com.study.base.business.sys.admin.repo.mapper;

import cn.xluobo.business.sys.admin.repo.model.SysDept;
import cn.xluobo.business.sys.admin.repo.model.SysTenant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-01-18
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 获取用户 有权限的校区列表
     *
     * @param userId
     * @param deptType
     * @return
     */
    List<SysDept> selectUserCampusList(@Param("userId") String userId, @Param("deptType") String deptType);

    /**
     * 用户对应的校区是否在用
     *
     * @param userId
     * @param deptId
     * @return
     */
    SysDept selectUserDeptInUse(@Param("userId") String userId, @Param("deptId") Long deptId);

}
