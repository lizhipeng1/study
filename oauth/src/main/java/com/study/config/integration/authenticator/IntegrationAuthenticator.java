package com.study.config.integration.authenticator;

import com.study.config.integration.IntegrationAuthentication;
import com.study.config.login.LoginUser;

/**
 * 各项登录方式授权接口
 * @author ：zhangbaoyu
 * @date ：Created in 2020/7/7 09:34
 */
public interface IntegrationAuthenticator {

    /**
     * 处理认证
     * @param integrationAuthentication
     * @return
     */
    LoginUser authenticate(IntegrationAuthentication integrationAuthentication);

    /**
     * 是否支持认证类型
     * @param integrationAuthentication
     * @return
     */
    boolean support(IntegrationAuthentication integrationAuthentication);

    /**
     * 预处理
     * @param integrationAuthentication
     */
    void prepare(IntegrationAuthentication integrationAuthentication);

    /**
     * 认证结束后执行
     * @param integrationAuthentication
     */
    void complete(IntegrationAuthentication integrationAuthentication);
}
