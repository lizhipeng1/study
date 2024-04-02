package com.study.core.business.sys.admin.controller;

import cn.xluobo.business.sys.admin.domain.req.ReqSearchSysRole;
import cn.xluobo.business.sys.admin.repo.model.SysRole;
import cn.xluobo.business.sys.admin.service.BusinessSysRoleService;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色表 Controller
 * </p>
 *
 * @author zhangby
 * @since 2020-01-30 11:51:28
 */
@RestController
@RequestMapping("/api/system/role")
public class SysRoleController {
    @Autowired
    private BusinessSysRoleService sysRoleService;

    /**
     * 列表
     *
     * @param reqSearchSysRole
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchSysRole reqSearchSysRole) {
        return sysRoleService.searchList(reqSearchSysRole);
    }

    /**
     * 前端select
     *
     * @return
     */
    @GetMapping("/list/treeSelect")
    public APIResponse treeSelect() {
        return sysRoleService.treeSelect();
    }

    /**
     * 前端select
     *
     * @return
     */
    @GetMapping("/list/treeSelectLimitUserHasRole")
    public APIResponse treeSelectLimitUserHasRole() {
        return sysRoleService.treeSelectLimitUserHasRole();
    }

    /**
     * 详情
     *
     * @param roleId
     * @return
     */
    @GetMapping("/info/detailById/{roleId}")
    public APIResponse detailById(@PathVariable("roleId") Long roleId) {
        return sysRoleService.detailById(roleId);
    }

    /**
     * 添加
     *
     * @param sysRole
     * @return
     */
    @PostMapping("/add/addSysRole")
    public APIResponse addSysRole(@RequestBody SysRole sysRole) {
        return sysRoleService.addSysRole(sysRole);
    }

    /**
     * 修改
     *
     * @param sysRole
     * @return
     */
    @PutMapping("/update/updateSysRole")
    public APIResponse updateSysRole(@RequestBody SysRole sysRole) {
        return sysRoleService.updateSysRole(sysRole);
    }

    /**
     * 删除
     *
     * @param roleIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{roleIds}")
    public APIResponse deleteById(@PathVariable("roleIds") Long[] roleIds) {
        return sysRoleService.deleteById(roleIds);
    }

    /**
     * 用户对应的角色Id列表
     * @return
     */
    @GetMapping("/list/userRoleIdList/{userId}/{tenantId}")
    public APIResponse userRoleIdList(@PathVariable("userId") String userId,@PathVariable("tenantId") String tenantId){
        List<String> userRoleIdList = sysRoleService.selectUserRoleIdList(userId,tenantId);
        return APIResponse.toAPIResponse(userRoleIdList);
    }

    /**
     * 用户对应的角色Id列表
     * @return
     */
    @GetMapping("/list/userRoleIdList/{userId}")
    public APIResponse userRoleIdListWithNowTenant(@PathVariable("userId") String userId){
        String nowTenant = LoginUserUtil.getNowTenant();
        List<String> userRoleIdList = sysRoleService.selectUserRoleIdList(userId,nowTenant);
        return APIResponse.toAPIResponse(userRoleIdList);
    }
}
