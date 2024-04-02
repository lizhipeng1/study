package com.study.core.business.sys.admin.service;

import cn.xluobo.business.sys.admin.domain.req.*;
import cn.xluobo.business.sys.admin.domain.resp.RespUserInfo;
import cn.xluobo.business.sys.admin.domain.resp.RespUserMenu;
import cn.xluobo.business.sys.admin.enums.RoleEnum;
import cn.xluobo.business.sys.admin.repo.model.*;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.api.ApiResEnums;
import cn.xluobo.core.page.RespPage;
import cn.xluobo.redis.service.CacheService;
import cn.xluobo.utils.JwtUtils;
import cn.xluobo.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessSysUserService {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysUserRoleService userRoleService;

    @Autowired
    private ISysUserTenantService userTenantService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private ISysTenantService tenantService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysFileService fileService;

    @Autowired
    private BusinessSysFileService sysFileService;

    /**
     * 用户信息
     *
     * @return
     */
    public RespUserInfo userInfo() {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        List<SysRole> roleList = roleService.selectUserRoleList(loginUser.getUserId(), loginUser.getNowTenantId());

        List<String> permissions = null;
        boolean hasAdminRole = sysUserService.checkUserHasAnyRole(loginUser.getUserId(), loginUser.getNowTenantId(), new String[]{RoleEnum.ADMIN.getRoleCode()});
        if (hasAdminRole) {
            permissions = Lists.newArrayList("*:*:*");
        } else {
            permissions = menuService.selectUserPermissions(loginUser.getUserId(), loginUser.getNowTenantId());
        }

        SysUser userDetail = sysUserService.getById(loginUser.getUserId());

        String avatar = "";
        if (StringUtils.isNotEmpty(userDetail.getAvatarImg())) {
            String avatarImg = userDetail.getAvatarImg();
            SysFile sysFile = fileService.getById(avatarImg);
            if(null!=sysFile) {
                avatar = sysFile.getPrePath() + sysFile.getFileName();
            }

        }


        List<String> roles = roleList.stream().map(SysRole::getRoleCode).collect(Collectors.toList());
        return RespUserInfo.builder()
                .name(loginUser.getName())
                .username(loginUser.getUsername())
                .avatar(avatar)
                .introduction("")
                .roles(roles)
                .permissions(permissions)
                .build();
    }

    /**
     * 用户菜单
     *
     * @return
     */
    public RespUserMenu userMenu() {

        LoginUser loginUser = LoginUserUtil.getLoginUser();
        boolean hasAdminRole = sysUserService.checkUserHasAnyRole(loginUser.getUserId(), loginUser.getNowTenantId(), new String[]{RoleEnum.ADMIN.getRoleCode()});
        List<SysMenu> sysMenuList = null;
        if (hasAdminRole) {
            sysMenuList = menuService.selectSuperUserMenuList();
        } else {
            sysMenuList = menuService.selectUserMenuList(loginUser.getUserId(), loginUser.getNowTenantId());
        }

        RespUserMenu userMenu = RespUserMenu.builder().id("-1").build();
        userMenu.converterMenu(sysMenuList);
        return userMenu;
    }

    /**
     * 查询
     *
     * @param reqSearchSysUser
     * @return
     */
    public APIResponse searchList(ReqSearchSysUser reqSearchSysUser) {
        RespPage<SysUser> page = new RespPage(reqSearchSysUser.getPageNum(), reqSearchSysUser.getPageSize());
        List<SysUser> userList = sysUserService.selectUserList(reqSearchSysUser, page);
        page.setRows(userList);
        return APIResponse.toAPIResponse(page);
    }

    /**
     * 详情
     *
     * @param userId
     * @return
     */
    public APIResponse detailById(String userId) {
        if (null == userId) {
            return APIResponse.toAPIResponse(null);
        }
        QueryWrapper qw = new QueryWrapper();
        qw.eq("user_id", userId);
        qw.select("user_id", "username", "name", "phone", "email_address", "enable", "locked", "account_expired", "pwd_expired", "dept_id");
        SysUser detailInfo = sysUserService.getOne(qw);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param sysUser
     * @return
     */
    public APIResponse addSysUser(SysUser sysUser) {
        String password = sysUser.getPassword();
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        String encodePass = encode.encode(password);
        sysUser.setPassword(encodePass);
        boolean addSysUser = sysUserService.save(sysUser);
        if (addSysUser) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新用户基本信息
     *
     * @param sysUser
     * @return
     */
    public APIResponse updateSysUser(SysUser sysUser) {
        if (null == sysUser.getUserId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        UpdateWrapper uw = new UpdateWrapper();
        uw.eq("user_id", sysUser.getUserId());
        uw.set("name", sysUser.getName());
        uw.set("phone", sysUser.getPhone());
        uw.set("email_address", sysUser.getEmailAddress());
        uw.set("dept_id", sysUser.getDeptId());
        if (StringUtils.isNotEmpty(sysUser.getLocked())) {
            uw.set("locked", sysUser.getLocked());
        }
        boolean updateSysUser = sysUserService.update(uw);
        if (updateSysUser) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param userIds
     * @return
     */
    public APIResponse deleteById(Long[] userIds) {
        if (null == userIds || userIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        boolean deleteSysUser = sysUserService.removeByIds(Arrays.asList(userIds));
        if (deleteSysUser) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param sysUser
     * @return
     */
    public APIResponse changeUserEnable(SysUser sysUser) {
        if (null == sysUser.getUserId() || StringUtils.isEmpty(sysUser.getEnable())) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        UpdateWrapper uw = new UpdateWrapper();
        uw.set("enable", sysUser.getEnable());
        uw.eq("user_id", sysUser.getUserId());
        boolean updateSysUser = sysUserService.update(uw);
        if (updateSysUser) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 重置密码
     *
     * @param sysUser
     * @return
     */
    public APIResponse resetUserPwd(SysUser sysUser) {
        if (StringUtils.isAnyEmpty(sysUser.getUserId(), sysUser.getPassword())) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        String encodePass = encode.encode(sysUser.getPassword());
        UpdateWrapper uw = new UpdateWrapper();
        uw.set("password", encodePass);
        uw.eq("user_id", sysUser.getUserId());
        boolean updateSysUser = sysUserService.update(uw);
        if (updateSysUser) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 修改用户角色
     *
     * @param reqUpdateUserRole
     * @return
     */
    @CacheEvict(value = "USER_PERMISSION_META", key = "#reqUpdateUserRole.userId+':'+#reqUpdateUserRole.tenantId")
    public APIResponse changeUserRole(ReqUpdateUserRole reqUpdateUserRole) {
        if (StringUtils.isAnyEmpty(reqUpdateUserRole.getUserId())) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        UpdateWrapper uw = new UpdateWrapper<>();
        uw.eq("user_id", reqUpdateUserRole.getUserId());
        uw.eq("tenant_id", reqUpdateUserRole.getTenantId());
        userRoleService.remove(uw);

        Long[] roleIds = reqUpdateUserRole.getRoleIds();
        if (null != roleIds) {
            List<SysUserRole> sysUserRoleList = Lists.newArrayList();
            for (Long roleId : reqUpdateUserRole.getRoleIds()) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(roleId);
                sysUserRole.setUserId(reqUpdateUserRole.getUserId());
                sysUserRole.setTenantId(reqUpdateUserRole.getTenantId());
                sysUserRoleList.add(sysUserRole);
            }
            userRoleService.saveBatch(sysUserRoleList);
        }
        return APIResponse.toOkResponse();
    }

    /**
     * 修改用户租户
     *
     * @param reqUpdateUserTenant
     * @return
     */
    public APIResponse changeUserTenant(ReqUpdateUserTenant reqUpdateUserTenant) {
        if (StringUtils.isAnyEmpty(reqUpdateUserTenant.getUserId())) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        UpdateWrapper uw = new UpdateWrapper<>();
        uw.eq("user_id", reqUpdateUserTenant.getUserId());
        userTenantService.remove(uw);
        List<SysUserTenant> sysUserTenantList = Lists.newArrayList();
        for (String tenantId : reqUpdateUserTenant.getTenantIds()) {
            SysUserTenant sysUserTenant = new SysUserTenant();
            sysUserTenant.setUserId(reqUpdateUserTenant.getUserId());
            sysUserTenant.setTenantId(tenantId);
            sysUserTenantList.add(sysUserTenant);
        }
        userTenantService.saveBatch(sysUserTenantList);
        return APIResponse.toOkResponse();
    }

    /**
     * 切换用户当前租户
     *
     * @param tenantId
     * @return
     */
    public APIResponse switchNowTenant(String tenantId) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        boolean userTenantInUse = tenantService.checkUserTenantInUse(loginUser.getUserId(), tenantId);
        if (!userTenantInUse) {
            APIResponse.toExceptionResponse("租户非在用或用户未分配此租户");
        }

        String jti = JwtUtils.getJtiByRequest();
        if (null != jti) {
            // 获取 refreshToken
            loginUser = cacheService.getOnlineUser(loginUser.getUserId(), jti);

            loginUser.setNowTenantId(tenantId);
            cacheService.putOnlineUser(loginUser.getUserId(), jti, loginUser);

            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toAPIResponse("无法获取当前登录信息,请稍后重试");
        }
    }

    /**
     * 切换用户选择 的 deptId
     * 例如校区
     *
     * @param deptId
     * @return
     */
    public APIResponse switchNowDeptId(Long deptId) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();

        if(null != deptId) {
            boolean userDeptInUse = deptService.checkUserDeptInUse(loginUser.getUserId(), deptId);
            if (!userDeptInUse) {
                APIResponse.toExceptionResponse("无校区权限,或校区当前非在用");
            }
        }

        String jti = JwtUtils.getJtiByRequest();
        if (null != jti) {
            // 获取 refreshToken
            loginUser = cacheService.getOnlineUser(loginUser.getUserId(), jti);

            loginUser.setDeptId(deptId);
            cacheService.putOnlineUser(loginUser.getUserId(), jti, loginUser);

            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toAPIResponse("无法获取当前登录信息,请稍后重试");
        }
    }

    /**
     * 用户详细信息
     *
     * @param userId
     * @return
     */
    public APIResponse profile(String userId) {
        SysUser sysUser = sysUserService.getById(userId);
        sysUser.setPassword(null);
        if (null != sysUser.getDeptId()) {
            SysDept sysDept = deptService.getById(sysUser.getDeptId());
            if (null != sysDept) {
                sysUser.setDeptName(sysDept.getDeptName());
            }
        }
        return APIResponse.toAPIResponse(sysUser);
    }

    /**
     * 更新用户基本信息
     *
     * @param sysUser
     * @return
     */
    public APIResponse updateUserProfile(SysUser sysUser) {
        UpdateWrapper up = new UpdateWrapper();
        up.eq("user_id", sysUser.getUserId());
        up.set("name", sysUser.getName());
        up.set("phone", sysUser.getPhone());
        up.set("email_address", sysUser.getEmailAddress());
        boolean update = sysUserService.update(up);
        if (update) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新用户密码
     *
     * @param updateUserPwd
     * @return
     */
    public APIResponse updateUserPwd(ReqUpdateUserPwd updateUserPwd) {
        String sysUserId = LoginUserUtil.getLoginUserId();
        if (StringUtils.isAnyEmpty(updateUserPwd.getOldPassword(), updateUserPwd.getNewPassword(), updateUserPwd.getConfirmPassword())) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }
        SysUser sysUser = sysUserService.getById(sysUserId);
        // 对比旧密码
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        boolean matches = encode.matches(updateUserPwd.getOldPassword(), sysUser.getPassword());
        if (!matches) {
            return APIResponse.toExceptionResponse("旧密码输入错误,请重试");
        }

        if (!updateUserPwd.getNewPassword().equals(updateUserPwd.getConfirmPassword())) {
            return APIResponse.toExceptionResponse("新密码与确认密码不一致,请重试");
        }

        // 更新新密码
        String encodeNewPwd = encode.encode(updateUserPwd.getNewPassword());
        UpdateWrapper up = new UpdateWrapper();
        up.eq("user_id", sysUser.getUserId());
        up.set("password", encodeNewPwd);
        boolean update = sysUserService.update(up);
        if (update) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 上传头像
     *
     * @param reqUploadFile
     * @return
     */
    public APIResponse uploadAvatar(ReqUploadFile reqUploadFile) throws IOException {
        APIResponse uploadImg = sysFileService.uploadImg(reqUploadFile);
        if (uploadImg.isSuccess()) {
            APIResponse.SuccessAPIResponse successAPIResponse = (APIResponse.SuccessAPIResponse) uploadImg;
            SysFile sysFile = (SysFile) successAPIResponse.getData();
            String fileId = sysFile.getFileId();

            String sysUserId = LoginUserUtil.getLoginUserId();
            UpdateWrapper up = new UpdateWrapper();
            up.eq("user_id", sysUserId);
            up.set("avatar_img", fileId);
            sysUserService.update(up);
            return uploadImg;
        } else {
            return uploadImg;
        }
    }

    /**
     * 校验用户名是否唯一
     *
     * @param username
     * @return
     */
    public boolean checkUsernameUnique(String username) {
        return sysUserService.checkUsernameUnique(username);
    }



    /**
     * 用户校区列表
     * @return 校区id、校区名称
     */
    public List<SysDept> userCampusList() {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        List<SysDept> deptList = deptService.selectUserCampusList(loginUser.getUserId());
        List<SysDept> userCampusList = deptList.stream().map(item -> new SysDept(item.getDeptId(), item.getDeptName())).collect(Collectors.toList());

        List<SysDept> resultList = Lists.newArrayList();
        resultList.add(new SysDept(null,"全部校区"));
        resultList.addAll(userCampusList);

        return resultList;
    }
}
