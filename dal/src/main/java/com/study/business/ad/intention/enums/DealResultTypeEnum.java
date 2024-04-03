package com.study.business.ad.intention.enums;

/**
 * 试用申请处理结果类型
 * 2022/3/21 12:23 下午
 *
 * @author zhangby
 **/
public enum DealResultTypeEnum {

    OPEN_ACCOUNT("01", "开通试用账号"),
    WAIT("02", "犹豫"),
    NOT_TRIAL("03", "产品不适用"),
    OTHER("99", "其他"),
    ;

    private String value;

    private String text;

    DealResultTypeEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }
}
