package com.study.business.sc.order.service;

import com.study.business.sc.order.repo.model.ScOrderAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单收款账户 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
public interface IScOrderAccountService extends IService<ScOrderAccount> {

    /**
     * 根据订单 获取收款账户
     * @param orderId
     * @return
     */
    List<ScOrderAccount> getOrderAccountList(Long orderId);

}
