package com.study.business.sys.admin.controller;


import com.study.business.sys.admin.domain.req.*;
import com.study.business.sys.admin.domain.resp.RespUserInfo;
import com.study.business.sys.admin.domain.resp.RespUserMenu;
import com.study.business.sys.admin.domain.req.ReqSearchSysUser;
import com.study.business.sys.admin.domain.req.ReqUpdateUserPwd;
import com.study.business.sys.admin.domain.req.ReqUpdateUserRole;
import com.study.business.sys.admin.domain.req.ReqUpdateUserTenant;
import com.study.business.sys.admin.repo.model.SysUser;
import com.study.business.sys.admin.service.BusinessSysUserService;
import com.study.business.sys.oauth.service.Oauth2Service;
import com.study.core.api.APIResponse;
import com.study.core.log.Log;
import com.study.core.log.enums.BusinessType;
import com.study.core.log.enums.OperateModule;
import com.study.utils.LoginUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zhangby
 * @since 2019-11-05
 */
@RestController
@RequestMapping("/api/system/user")
@Slf4j
public class SysUserController {

    @Autowired
    private BusinessSysUserService sysUserService;
    @Autowired
    private Oauth2Service oauth2Service;

    /**
     * 用户信息
     *
     * @return
     */
    @GetMapping("/info")
    public APIResponse userInfo() {
        RespUserInfo userInfo = sysUserService.userInfo();
        return APIResponse.toAPIResponse(userInfo);
    }

    /**
     * 用户菜单
     *
     * @return
     */
    @GetMapping("/menu")
    public APIResponse userMenu() {
        RespUserMenu userMenu = sysUserService.userMenu();
        return APIResponse.toAPIResponse(userMenu.getChildren());
    }

    /**
     * 列表
     *
     * @param reqSearchSysUser
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchSysUser reqSearchSysUser) {
        return sysUserService.searchList(reqSearchSysUser);
    }

    /**
     * 详情
     *
     * @param userId
     * @return
     */
    @GetMapping("/info/detailById/{userId}")
    public APIResponse detailById(@PathVariable("userId") String userId) {
        return sysUserService.detailById(userId);
    }

    /**
     * 添加
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/add/addSysUser")
    public APIResponse addSysUser(@RequestBody SysUser sysUser) {
        return sysUserService.addSysUser(sysUser);
    }

    /**
     * 修改
     *
     * @param sysUser
     * @return
     */
    @PutMapping("/update/updateSysUser")
    @Log(module = OperateModule.USER, businessType = BusinessType.UPDATE)
    public APIResponse updateSysUser(@RequestBody SysUser sysUser) {
        return sysUserService.updateSysUser(sysUser);
    }

    /**
     * 删除
     *
     * @param userIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{userIds}")
    public APIResponse deleteById(@PathVariable("userIds") Long[] userIds) {
        return sysUserService.deleteById(userIds);
    }

    /**
     * 修改
     *
     * @param sysUser
     * @return
     */
    @PutMapping("/update/changeUserEnable")
    public APIResponse changeUserEnable(@RequestBody SysUser sysUser) {
        return sysUserService.changeUserEnable(sysUser);
    }

    /**
     * 修改
     *
     * @param sysUser
     * @return
     */
    @PutMapping("/update/resetUserPwd")
    public APIResponse resetUserPwd(@RequestBody SysUser sysUser) {
        return sysUserService.resetUserPwd(sysUser);
    }

    /**
     * 分配角色
     *
     * @param reqUpdateUserRole
     * @return
     */
    @PutMapping("/update/changeUserRole")
    public APIResponse changeUserRole(@RequestBody ReqUpdateUserRole reqUpdateUserRole) {
        return sysUserService.changeUserRole(reqUpdateUserRole);
    }

    /**
     * 分配角色
     *
     * @param reqUpdateUserTenant
     * @return
     */
    @PutMapping("/update/changeUserTenant")
    public APIResponse changeUserTenant(@RequestBody ReqUpdateUserTenant reqUpdateUserTenant) {
        return sysUserService.changeUserTenant(reqUpdateUserTenant);
    }

    /**
     * 切换当前租户
     *
     * @param tenantId
     * @return
     */
    @GetMapping("/switchNowTenant/{tenantId}")
    public APIResponse switchNowTenant(@PathVariable("tenantId") String tenantId) {
        return sysUserService.switchNowTenant(tenantId);
    }

    /**
     * 用户信息
     *
     * @return
     */
    @GetMapping("/info/getUserProfile")
    public APIResponse profile() {
        String sysUserId = LoginUserUtil.getLoginUserId();
        return sysUserService.profile(sysUserId);
    }

    /**
     * 修改用户基本信息
     *
     * @param sysUser
     * @return
     */
    @PutMapping("/update/updateUserProfile")
    public APIResponse updateUserProfile(@RequestBody SysUser sysUser) {
        return sysUserService.updateUserProfile(sysUser);
    }

    /**
     * 修改用户密码
     *
     * @param reqUpdateUserPwd
     * @return
     */
    @PutMapping("/update/updateUserPwd")
    public APIResponse updateUserPwd(@RequestBody ReqUpdateUserPwd reqUpdateUserPwd) {
        return sysUserService.updateUserPwd(reqUpdateUserPwd);
    }

    /**
     * 修改头像
     *
     * @param reqUploadFile
     * @return
     */
    @PostMapping("/update/uploadAvatar")
    public APIResponse uploadAvatar(ReqUploadFile reqUploadFile) {
        try {
            return sysUserService.uploadAvatar(reqUploadFile);
        } catch (IOException e) {
            log.error("uploadAvatar error",e);
            return APIResponse.toExceptionResponse("上传图片失败");
        }
    }

    /**
     * 校验账号是否已注册
     * @param username
     * @return
     */
    @GetMapping("/info/checkUsernameUnique/{username}")
    public APIResponse checkUsernameUnique(@PathVariable String username){
        boolean usernameUnique = sysUserService.checkUsernameUnique(username);
        return APIResponse.toAPIResponse(usernameUnique);
    }

    /**
     * 退出登录 清除token
     * @return
     */
    // @PostMapping("/removeLoginUserToken")
    public APIResponse removeLoginUserToken() {
        oauth2Service.removeToken();
        return APIResponse.toOkResponse();
    }
}
