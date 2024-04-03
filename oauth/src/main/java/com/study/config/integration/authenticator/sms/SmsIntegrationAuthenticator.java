package com.study.config.integration.authenticator.sms;

import com.study.config.integration.IntegrationAuthentication;
import com.study.config.integration.authenticator.AbstractPreparedIntegrationAuthenticator;
import com.study.config.integration.authenticator.sms.event.SmsAuthenticateBeforeEvent;
import com.study.config.integration.authenticator.sms.event.SmsAuthenticateSuccessEvent;
import com.study.config.login.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 短信验证码集成认证
 *
 **/
@Component
public class SmsIntegrationAuthenticator extends AbstractPreparedIntegrationAuthenticator implements ApplicationEventPublisherAware {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ApplicationEventPublisher applicationEventPublisher;

    private final static String SMS_AUTH_TYPE = "sms";

    @Override
    public LoginUser authenticate(IntegrationAuthentication integrationAuthentication) {

        //获取密码，实际值是验证码
        String password = integrationAuthentication.getAuthParameter("password");
        //获取用户名，实际值是手机号
        String username = integrationAuthentication.getUsername();
        //发布事件，可以监听事件进行自动注册用户
        this.applicationEventPublisher.publishEvent(new SmsAuthenticateBeforeEvent(integrationAuthentication));
        // todo 通过手机号码查询用户
        LoginUser loginUser = null;// 获取用户
        if (loginUser != null) {
            //将密码设置为验证码
            loginUser.setPassword(passwordEncoder.encode(password));
            //发布事件，可以监听事件进行消息通知
            this.applicationEventPublisher.publishEvent(new SmsAuthenticateSuccessEvent(integrationAuthentication));
        }
        return loginUser;
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
        // 验证码token
        String smsToken = integrationAuthentication.getAuthParameter("sms_token");
        // 短信验证码
        String smsCode = integrationAuthentication.getAuthParameter("password");
        // 手机号码
        String username = integrationAuthentication.getAuthParameter("username");
        // todo 验证是否正确
        // todo 不正确则抛出异常
        // throw new OAuth2Exception("验证码错误或已过期");
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return SMS_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
