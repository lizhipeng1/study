package com.study.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-07 15:19
 */
@ConfigurationProperties(prefix = "security.oauth2.client")
public class OAuth2ClientDetail {

    private String clientId;

    private String clientSecret;

    private String accessTokenUri;

    private String redirectUri;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
