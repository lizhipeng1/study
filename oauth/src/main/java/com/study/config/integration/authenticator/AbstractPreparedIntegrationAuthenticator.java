package com.study.config.integration.authenticator;

import cn.xluobo.config.integration.IntegrationAuthentication;
import cn.xluobo.config.login.LoginUser;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/7/7 09:49
 */
public abstract class AbstractPreparedIntegrationAuthenticator implements IntegrationAuthenticator {

    @Override
    public abstract LoginUser authenticate(IntegrationAuthentication integrationAuthentication);

    @Override
    public abstract boolean support(IntegrationAuthentication integrationAuthentication);

    @Override
    public abstract void prepare(IntegrationAuthentication integrationAuthentication);

    @Override
    public void complete(IntegrationAuthentication integrationAuthentication) {

    }
}
