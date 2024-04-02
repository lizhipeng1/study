package com.study.config.security;

import cn.xluobo.business.sys.admin.service.ISysUserService;
import cn.xluobo.config.integration.IntegrationAuthentication;
import cn.xluobo.config.integration.IntegrationAuthenticationContext;
import cn.xluobo.config.integration.authenticator.IntegrationAuthenticator;
import cn.xluobo.config.login.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by zhangbaoyu on 18/1/9.
 */
public class CustomUserService implements UserDetailsService {

    @Autowired
    private ISysUserService sysUserService;

    private List<IntegrationAuthenticator> authenticators;

    @Autowired(required = false)
    public void setIntegrationAuthenticators(List<IntegrationAuthenticator> authenticators) {
        this.authenticators = authenticators;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        IntegrationAuthentication integrationAuthentication = IntegrationAuthenticationContext.get();
        if (integrationAuthentication == null) {
            integrationAuthentication = new IntegrationAuthentication();
        }
        integrationAuthentication.setUsername(username);
        LoginUser authenticate = this.authenticate(integrationAuthentication);
        if(authenticate == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return authenticate;
    }

    private LoginUser authenticate(IntegrationAuthentication integrationAuthentication) {
        if (this.authenticators != null) {
            for (IntegrationAuthenticator authenticator : authenticators) {
                if (authenticator.support(integrationAuthentication)) {
                    return authenticator.authenticate(integrationAuthentication);
                }
            }
        }
        return null;
    }
}
