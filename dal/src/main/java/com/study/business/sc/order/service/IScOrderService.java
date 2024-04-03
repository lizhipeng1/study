package com.study.business.sc.order.service;

import com.study.business.sc.order.repo.model.ScOrder;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-08-24 10:22:19
 */
public interface IScOrderService extends com.baomidou.mybatisplus.extension.service.IService<ScOrder> {

    /**
     * 订单作废
     * @param orderId
     * @return
     */
    boolean invalidOrder(Long orderId);

    /**
     * 订单数量
     * @param beginDate 经办日期开始时间
     * @param endDate 经办日期结束时间
     * @param orderType
     * @return
     */
    Integer orderCount(String beginDate, String endDate, String orderType);

    /**
     * 欠费学员数量
     * 收款金额 + 余额支付金额  < 应收金额
     * @return
     */
    Integer arrearsStudentCount();

}
