package com.study.business.sc.order.enums;

/**
 * 订单类型
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/12 15:13
 */
public enum OrderTypeEnum {

    SIGN_UP("1", "报名"),
    ;

    private String orderType;
    private String orderTypeName;

    OrderTypeEnum(String orderType, String orderTypeName) {
        this.orderType = orderType;
        this.orderTypeName = orderTypeName;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }
}
