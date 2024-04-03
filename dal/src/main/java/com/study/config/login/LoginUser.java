package com.study.config.login;

import com.study.business.sys.admin.repo.model.SysUser;
import com.study.core.utils.ServletUtils;
import com.study.core.utils.ip.IpUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 登录用户信息
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020-03-02 13:10
 */
public class LoginUser implements UserDetails {

    private String userId;

    private Long staffId;

    private String name;

    private String username;

    private String password;

    // 当前租户
    private String nowTenantId;

    // 当前deptId
    private Long deptId;

    private String loginIp;

    private String loginLocation;

    private Date loginTime;

    private String os;

    private String browser;

    /**
     * 登录方式 'Pc'、'WechatMiniApp'
     */
    private String loginType;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private List<GrantedAuthority> authorities;

    private String accessToken;
    private String refreshToken;
    private String jti;

    private Object extendOjb;

    public LoginUser() {
        super();
    }

    public static LoginUser buildBySysUser(SysUser sysUser) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(sysUser.getUserId());
        loginUser.setName(sysUser.getName());
        loginUser.setUsername(sysUser.getUsername());
        loginUser.setPassword(sysUser.getPassword());
        loginUser.setAccountNonExpired("0".equals(sysUser.getAccountExpired()));
        loginUser.setAccountNonLocked("0".equals(sysUser.getLocked()));
        loginUser.setCredentialsNonExpired("0".equals(sysUser.getPwdExpired()));
        loginUser.setEnabled("1".equals(sysUser.getEnable()));

        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
//        String realAddressByIP = AddressUtils.getRealAddressByIP(ip);
        String realAddressByIP = "无";

        loginUser.setLoginIp(ip);
        loginUser.setLoginLocation(realAddressByIP);
        loginUser.setLoginTime(new Date());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
        loginUser.setBrowser(userAgent.getBrowser().getName());

        return loginUser;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return enabled;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNowTenantId() {
        return nowTenantId;
    }

    public void setNowTenantId(String nowTenantId) {
        this.nowTenantId = nowTenantId;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public Object getExtendOjb() {
        return extendOjb;
    }

    public void setExtendOjb(Object extendOjb) {
        this.extendOjb = extendOjb;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
