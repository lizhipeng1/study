package com.study.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-07 15:19
 */
@ConfigurationProperties(prefix = "security.oauth2.sso")
public class OAuth2SsoDetail {
    private String ssoRedirectUrl;

    public String getSsoRedirectUrl() {
        return ssoRedirectUrl;
    }

    public void setSsoRedirectUrl(String ssoRedirectUrl) {
        this.ssoRedirectUrl = ssoRedirectUrl;
    }
}
