package com.study.core.config.oauth2;

import cn.xluobo.business.sys.admin.enums.RoleEnum;
import cn.xluobo.business.sys.admin.service.ISysMenuService;
import cn.xluobo.business.sys.admin.service.ISysUserService;
import cn.xluobo.business.sys.admin.service.ISysUserTenantService;
import cn.xluobo.config.exception.NoTokenInRedisException;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.config.security.CustomUserService;
import cn.xluobo.redis.service.CacheService;
import org.apache.commons.collections.MapUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-02-11 12:10
 */
public class CustomUserAuthenticationConverter implements UserAuthenticationConverter {

    private CacheService cacheService;
    private ISysUserTenantService userTenantService;
    private ISysMenuService menuService;
    private CustomUserService customUserService;
    private ISysUserService sysUserService;

    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public void setUserTenantService(ISysUserTenantService userTenantService) {
        this.userTenantService = userTenantService;
    }

    public void setMenuService(ISysMenuService menuService) {
        this.menuService = menuService;
    }

    public void setCustomUserService(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    public void setSysUserService(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put(USERNAME, authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        String userId = MapUtils.getString(map, "userId");
        String jti = MapUtils.getString(map, "jti");

        LoginUser loginUser = cacheService.getOnlineUser(userId, jti);

        if (null == loginUser) {
            throw new NoTokenInRedisException("");
        }

        List<String> sysMenuList = null;
        boolean hasAdminRole = sysUserService.checkUserHasAnyRole(loginUser.getUserId(), loginUser.getNowTenantId(), new String[]{RoleEnum.ADMIN.getRoleCode()});
        if (hasAdminRole) {
            sysMenuList = menuService.selectUserPermissions("", "");
        } else {
            sysMenuList = menuService.selectUserPermissions(userId, loginUser.getNowTenantId());
        }

        List<GrantedAuthority> grantedAuthorities = sysMenuList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        loginUser.setAuthorities(grantedAuthorities);

        Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
        grantedAuthorities.addAll(authorities);
        return new UsernamePasswordAuthenticationToken(loginUser, "N/A", grantedAuthorities);
    }

    /**
     * jwt中基本权限信息
     *
     * @param map
     * @return
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        Object authorities = map.get(AUTHORITIES);
        if (null == authorities) {
            return new ArrayList<>();
        }
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }
}
