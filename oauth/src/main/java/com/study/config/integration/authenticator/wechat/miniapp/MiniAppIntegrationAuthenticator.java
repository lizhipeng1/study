package com.study.config.integration.authenticator.wechat.miniapp;

import com.study.business.sys.admin.repo.model.SysUser;
import com.study.business.wechat.user.service.IWechatUserInfoService;
import com.study.config.integration.IntegrationAuthentication;
import com.study.config.integration.authenticator.IntegrationAuthenticator;
import com.study.config.login.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 小程序集成认证
 *
 * @author LIQIU
 * @date 2018-3-31
 **/
@Service
@Slf4j
public class MiniAppIntegrationAuthenticator implements IntegrationAuthenticator {

    public final static String SOCIAL_TYPE_WECHAT_MINIAP = "wechatMiniApp";

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IWechatUserInfoService wechatUserInfoService;

    @Override
    public LoginUser authenticate(IntegrationAuthentication integrationAuthentication) {
        // 获取请求参数
        String appId = integrationAuthentication.getAuthParameter("appId");
        String openid = integrationAuthentication.getAuthParameter("openid");
        // 用于校验 必填此请求参数 AbstractUserDetailsAuthenticationProvider.additionalAuthenticationChecks
        String password = integrationAuthentication.getAuthParameter("password");
//        String openid = "1";
        SysUser sysUser = wechatUserInfoService.getSysUser(appId,openid);
        if(null == sysUser) {
            return null;
        }

        LoginUser loginUser = LoginUser.buildBySysUser(sysUser);
        // 用于校验 AbstractUserDetailsAuthenticationProvider.additionalAuthenticationChecks
        loginUser.setPassword(passwordEncoder.encode(password));
        loginUser.setAuthorities(new ArrayList<>());
        return loginUser;
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {

    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return SOCIAL_TYPE_WECHAT_MINIAP.equals(integrationAuthentication.getAuthType());
    }

    @Override
    public void complete(IntegrationAuthentication integrationAuthentication) {

    }
}
