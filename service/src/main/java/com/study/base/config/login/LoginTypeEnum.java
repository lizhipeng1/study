package com.study.base.config.login;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/15 12:38
 */
public enum LoginTypeEnum {
    PC("Pc", "电脑端"),
    WECHAT_MINI_APP("WechatMiniApp", "小程序端"),
    ;
    private String loginType;
    private String loginTypeName;

    LoginTypeEnum(String loginType, String loginTypeName) {
        this.loginType = loginType;
        this.loginTypeName = loginTypeName;
    }

    public String getLoginType() {
        return loginType;
    }

    public String getLoginTypeName() {
        return loginTypeName;
    }
}
