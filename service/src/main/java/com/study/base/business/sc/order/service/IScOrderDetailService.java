package com.study.base.business.sc.order.service;

import cn.xluobo.business.sc.order.repo.model.ScOrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单详情 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
public interface IScOrderDetailService extends IService<ScOrderDetail> {

    /**
     * 根据单号获取 订单明细
     * @param orderId
     * @return
     */
    List<ScOrderDetail> getByOrderDetail(Long orderId);

    /**
     * 获取订单明细
     * @param orderId
     * @param notEqOrderStatus 不包含的状态
     * @return
     */
    List<ScOrderDetail> getByOrderDetail(Long orderId, String[] notEqOrderStatus);

    /**
     * 获取订单明细
     * @param orderId
     * @param orderStatus
     * @return
     */
    List<ScOrderDetail> getByOrderDetail(Long orderId, String orderStatus);

    /**
     * 作废订单明细
     * @param orderId
     * @param orderDetailId
     * @return
     */
    boolean invalidOrder(Long orderId, List<Long> orderDetailId);

}
