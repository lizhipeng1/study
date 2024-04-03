package com.study.business.sc.order.domain.resp;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/8 13:47
 */
@Data
public class RespOrder {

    private Long orderId;

    private String orderType;

    private BigDecimal actualTotalFee;

    private BigDecimal receiptFee;

    private BigDecimal balanceFee;

    private String saleStaffName;

    private String orderTag;

    private String handleDeptName;

    private String handleDate;

    private String createUserName;

    private String memo;

    private String studentName;

    private String phone;

    private String orderDetail;

    private String orderStatus;

}
