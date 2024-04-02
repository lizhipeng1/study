package com.study.config.integration.authenticator;

import cn.xluobo.business.sys.admin.service.ISysUserService;
import cn.xluobo.config.integration.IntegrationAuthentication;
import cn.xluobo.config.login.LoginUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 默认登录处理
 * @author ：zhangbaoyu
 * @date ：Created in 2020/7/7 10:08
 */
@Component
@Primary
public class UsernamePasswordAuthenticator extends AbstractPreparedIntegrationAuthenticator{

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public LoginUser authenticate(IntegrationAuthentication integrationAuthentication) {
        return (LoginUser)sysUserService.loadUserDetailByUserName(integrationAuthentication.getUsername());
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return StringUtils.isEmpty(integrationAuthentication.getAuthType());
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {

    }
}
