package com.study.base.business.sc.order.enums;

/**
 * 订单状态
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/13 21:11
 */
public enum OrderStatusEnum {

    WAIT_PAY("1", "待支付"),
    HAD_PAY("2", "已支付"),
    INVALID("3", "已作废"),
    ;

    private String orderStatus;

    private String statusTxt;

    OrderStatusEnum(String orderStatus, String statusTxt) {
        this.orderStatus = orderStatus;
        this.statusTxt = statusTxt;
    }


    public String getOrderStatus() {
        return orderStatus;
    }

    public String getStatusTxt() {
        return statusTxt;
    }
}
