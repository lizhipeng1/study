package com.study.core.business.sc.order.domain.req;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 新报收款信息
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/4 16:46
 */
@Data
public class ReqBusinessSignUpReceipt {

    private Long accountId;

    // 收款金额
    private BigDecimal receiptMoney;

}
