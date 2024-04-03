package com.study.business.sys.admin.service.impl;

import com.study.business.sys.admin.domain.req.ReqSearchSysUser;
import com.study.business.sys.admin.repo.mapper.SysUserMapper;
import com.study.business.sys.admin.repo.model.SysUser;
import com.study.business.sys.admin.service.ISysMenuService;
import com.study.business.sys.admin.service.ISysUserService;
import com.study.business.sys.admin.service.ISysUserTenantService;
import com.study.config.login.LoginUser;
import com.study.core.page.RespPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private ISysUserTenantService userTenantService;
    @Autowired
    private ISysMenuService menuService;

    @Override
    public UserDetails loadUserDetailByUserName(String userName) {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("username",userName);
        SysUser sysUser = baseMapper.selectOne(qw);
        if(null == sysUser){
            throw new UsernameNotFoundException("username: " + userName + " do not exist!");
        }
        String tenantId = userTenantService.selectUserDefaultTenant(sysUser.getUserId());
        if(StringUtils.isAnyEmpty(tenantId)) {
            throw new AccountExpiredException("账户无关联机构或机构已过期!");
        }
        LoginUser loginUser = LoginUser.buildBySysUser(sysUser);
        loginUser.setAuthorities(new ArrayList<>());
        return loginUser;
//        return loadUserExtendInfo(sysUser,null);
    }

    @Override
    public List<SysUser> selectUserList(ReqSearchSysUser reqSearchSysUser, RespPage page) {
        return baseMapper.selectUserList(reqSearchSysUser, page);
    }

    @Override
    public boolean checkUsernameUnique(String username) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("username",username);
        int count = this.count(qw);
        if(count == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public SysUser getByBindByWechatUserId(Long wechatUserId) {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("wechat_user_id",wechatUserId);
        return this.getOne(qw);
    }

    @Override
    public SysUser getByUserName(String username) {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("username",username);
        SysUser sysUser = baseMapper.selectOne(qw);
        return sysUser;
    }

    @Override
    public boolean checkSuperTenantUserHasRole(String userId, String roleCode) {
        int userRoleCount = baseMapper.selectSuperTenantUserRoleCount(userId, roleCode);
        return userRoleCount > 0;
    }

    @Override
    public boolean checkUserHasAnyRole(String userId, String tenantId, String[] roleCodes) {
        int hasAnyRoleCount = baseMapper.selectUserHasAnyRoleCount(userId, tenantId, roleCodes);
        return hasAnyRoleCount > 0;
    }

    private LoginUser loadUserExtendInfo(LoginUser user,String tenantId){
        if(null == tenantId){
            tenantId = userTenantService.selectUserDefaultTenant(user.getUserId());
        }
        //未设置租户 或者租户到期
        if(null == tenantId){
            throw new BadCredentialsException("tenant not set up,or tenant expired");
        }
        List<String> sysMenuList = menuService.selectUserPermissions(user.getUserId(), tenantId);
        List<GrantedAuthority> grantedAuthorities = sysMenuList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        user.setAuthorities(grantedAuthorities);
        user.setNowTenantId(tenantId);
        return user;
    }
}
