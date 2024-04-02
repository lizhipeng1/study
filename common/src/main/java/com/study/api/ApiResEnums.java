package com.study.api;

/**
 * @author lzp
 */

public enum ApiResEnums {
    SC_UNAUTHORIZED("0401","401"),
    SC_FORCE_OFFLINE("0511","强制踢出,请重新登录"),
    SC_INVALID_TOKEN("0513","登录信息失效,请重新登录"),
    // token 失效后 刷新
    SC_INVALID_REFRESH_TOKEN("0512","token refresh success"),
    SUCCESS("0000","success"),
    FAILURE("9999","获取相关数据失败,请联系客服"),
    BUSINESS_FAILURE("6666","业务异常"),
    PARAM_FAIL("7777","请求参数错误,请重试"),
    FAIL_WAIT_A_MINUTE("7777","操作失败,请稍后重试"),
    INTF_FAIL("8888","调用接口失败,请联系管理员"),

    // 小程序 接口返回内容
    MINI_APP_UN_BIND_USER("M0001","暂未未绑定账号"),
    MINI_APP_USER_HAD_BIND_BY_OTHER_USER("M0002","用户已被其他微信绑定"),
    MINI_APP_WECHAT_HAD_BIND_USER("M0002","微信已绑定其他用户"),
    ;


    private String respCode;
    private String respMsg;

    ApiResEnums(String respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
}
