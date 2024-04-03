package com.study.business.sys.admin.service;

import com.study.business.sys.admin.repo.model.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-18
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 是否存在子部门
     * @param departId
     * @return
     */
    boolean hadChild(Long departId);

    /**
     * 是否存在用户
     * @param departId
     * @return
     */
    boolean hadUser(Long departId);

    /**
     * 修改子元素关系
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors);

    /**
     * 获取用户有权限的校区列表
     * @return
     */
    List<SysDept> selectUserCampusList(String userId);

    /**
     * 校验 用户dept 是否在用
     * 如校区
     *
     * @param userId
     * @param deptId
     * @return
     */
    boolean checkUserDeptInUse(String userId, Long deptId);
}
