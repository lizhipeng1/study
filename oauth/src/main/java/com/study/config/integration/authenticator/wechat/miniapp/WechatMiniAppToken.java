package com.study.config.integration.authenticator.wechat.miniapp;

import lombok.Data;

@Data
public class WechatMiniAppToken {

    private String openId;
    private String unionId;
    private String sessionKey;
    private String username;
    private String encryptedData;
    private String iv;

    public WechatMiniAppToken(String openId, String unionId, String sessionKey) {
        this.openId = openId;
        this.unionId = unionId;
        this.sessionKey = sessionKey;
    }
}
