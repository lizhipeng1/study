package com.study.config.integration.authenticator;

import cn.xluobo.config.integration.IntegrationAuthentication;
import org.springframework.stereotype.Component;

/**
 * 集成验证码认证
 **/
@Component
public class VerificationCodeIntegrationAuthenticator extends UsernamePasswordAuthenticator {

    private final static String VERIFICATION_CODE_AUTH_TYPE = "vc";

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
        // 验证码token
        String vcToken = integrationAuthentication.getAuthParameter("vc_token");
        // 验证码
        String vcCode = integrationAuthentication.getAuthParameter("vc_code");
        //todo 验证验证码是否正确

        // todo 如果不正确抛出异常
        // throw new OAuth2Exception("验证码错误");
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return VERIFICATION_CODE_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }
}
