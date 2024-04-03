package com.study.business.sys.admin.service;

import com.study.business.sys.admin.domain.req.ReqSearchSysRole;
import com.study.business.sys.admin.domain.resp.RespTreeSelect;
import com.study.business.sys.admin.repo.model.SysRole;
import com.study.business.sys.admin.repo.model.SysRoleMenu;
import com.study.config.login.LoginUser;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessSysRoleService {

    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    /**
     * 查询树
     *
     * @param reqSearchSysRole
     * @return
     */
    public APIResponse searchList(ReqSearchSysRole reqSearchSysRole) {
        QueryWrapper qw = new QueryWrapper();
        if (StringUtils.isNotEmpty(reqSearchSysRole.getRoleName())) {
            qw.like("role_name", reqSearchSysRole.getRoleName());
        }
        if (StringUtils.isNotEmpty(reqSearchSysRole.getInUse())) {
            qw.eq("in_use", reqSearchSysRole.getInUse());
        }
        List<SysRole> list = sysRoleService.list(qw);
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(-1L);
        sysRole.converterTree(list);
        List<SysRole> respPage = sysRole.getChildren();
        return APIResponse.toAPIResponse(respPage);
    }

    /**
     * 前端select
     *
     * @return
     */
    public APIResponse treeSelect() {
        SysRole sysRole = new SysRole();
        sysRole.setInUse("1");
        sysRole.setDeleteFlag("0");
        QueryWrapper<SysRole> qw = new QueryWrapper(sysRole);
        qw.orderByAsc("sort");
        List<SysRole> list = sysRoleService.list(qw);
        SysRole tree = new SysRole();
        tree.setRoleId(-1L);
        tree.converterTree(list);
        List<SysRole> children = tree.getChildren();
        List<RespTreeSelect> respTreeSelects =
                children.stream().map(RespTreeSelect::new).collect(Collectors.toList());
        return APIResponse.toAPIResponse(respTreeSelects);
    }

    /**
     * 前端select
     *
     * @return
     */
    public APIResponse treeSelectLimitUserHasRole() {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        List<SysRole> roleList = sysRoleService.selectUserRoleList(loginUser.getUserId(), loginUser.getNowTenantId());
        SysRole tree = new SysRole();
        tree.setRoleId(-1L);
        tree.converterTree(roleList);
        List<SysRole> children = tree.getChildren();
        List<RespTreeSelect> respTreeSelects =
                children.stream().map(RespTreeSelect::new).collect(Collectors.toList());
        return APIResponse.toAPIResponse(respTreeSelects);
    }

    /**
     * 详情
     *
     * @param roleId
     * @return
     */
    public APIResponse detailById(Long roleId) {
        if (null == roleId) {
            return APIResponse.toAPIResponse(null);
        }
        SysRole detailInfo = sysRoleService.getById(roleId);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param sysRole
     * @return
     */
    public APIResponse addSysRole(SysRole sysRole) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        sysRole.setCreateUser(loginUser.getUserId());
        SysRole parentSysRole = sysRoleService.getById(sysRole.getParentId());
        if (null != parentSysRole) {
            if ("0".equals(parentSysRole.getInUse())) {
                return APIResponse.toExceptionResponse("父级已停用,不允许新增");
            }
            sysRole.setAncestors(parentSysRole.getAncestors() + "," + sysRole.getParentId());
        } else {
            sysRole.setAncestors(String.valueOf(sysRole.getParentId()));
        }

        boolean addSysRole = sysRoleService.save(sysRole);
        //角色菜单关系
        if(null != sysRole.getMenuIds() && sysRole.getMenuIds().length>0){
            List<SysRoleMenu> sysRoleMenuList = Lists.newArrayList();
            for (String menuId : sysRole.getMenuIds()) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(sysRole.getRoleId());
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setTenantId(loginUser.getNowTenantId());
                sysRoleMenuList.add(sysRoleMenu);
            }
            sysRoleMenuService.saveBatch(sysRoleMenuList);
        }
        if (addSysRole) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param sysRole
     * @return
     */
    public APIResponse updateSysRole(SysRole sysRole) {
        if (null == sysRole.getRoleId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        if (sysRole.getParentId().compareTo(sysRole.getRoleId()) == 0) {
            return APIResponse.toExceptionResponse("父级不能是自己");
        }
        //修改子元素 关系
        SysRole newParentSysRole = sysRoleService.getById(sysRole.getParentId());
        SysRole oldSysRole = sysRoleService.getById(sysRole.getRoleId());
        if (null != newParentSysRole && null != oldSysRole) {
            String newAncestors = newParentSysRole.getAncestors() + "," + newParentSysRole.getRoleId();
            String oldAncestors = oldSysRole.getAncestors();
            sysRole.setAncestors(newAncestors);
            sysRoleService.updateSysRoleChildren(sysRole.getRoleId(), newAncestors, oldAncestors);
        }

        LoginUser loginUser = LoginUserUtil.getLoginUser();
        sysRole.setLastUpdateUser(loginUser.getUserId());
        sysRole.setLastUpdateTime(new Date());
        boolean updateSysRole = sysRoleService.updateById(sysRole);
        sysRoleMenuService.updateRoleMenu(sysRole,loginUser.getNowTenantId());
        if (updateSysRole) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param roleIds
     * @return
     */
    public APIResponse deleteById(Long[] roleIds) {
        if (null == roleIds || roleIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }

        for (Long roleId : roleIds) {
            if (sysRoleService.hadChild(roleId)) {
                return APIResponse.toExceptionResponse("存在子节点不能删除");
            }
            if (sysRoleService.hadUser(roleId)) {
                return APIResponse.toExceptionResponse("存在用户不能删除");
            }
        }

        boolean deleteSysRole = sysRoleService.removeByIds(Arrays.asList(roleIds));
        //角色菜单关系
        UpdateWrapper uw = new UpdateWrapper<>();
        uw.in("role_id",Arrays.asList(roleIds));
        sysRoleMenuService.remove(uw);
        if (deleteSysRole) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 用户对角色Id列表
     * @param userId
     * @return
     */
    public List<String> selectUserRoleIdList(String userId,String tenantId){
        if(StringUtils.isEmpty(userId)){
            return Lists.newArrayList();
        }
        return sysRoleService.selectUserRoleTreeIdList(userId, tenantId);
    }
}
