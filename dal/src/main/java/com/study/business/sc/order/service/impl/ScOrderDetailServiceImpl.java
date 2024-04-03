package com.study.business.sc.order.service.impl;

import com.study.business.sc.order.enums.OrderStatusEnum;
import com.study.business.sc.order.repo.model.ScOrderDetail;
import com.study.business.sc.order.repo.mapper.ScOrderDetailMapper;
import com.study.business.sc.order.service.IScOrderDetailService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单详情 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
@Service
public class ScOrderDetailServiceImpl extends ServiceImpl<ScOrderDetailMapper, ScOrderDetail> implements IScOrderDetailService {

    @Override
    public List<ScOrderDetail> getByOrderDetail(Long orderId) {
        QueryWrapper<ScOrderDetail> qw = new QueryWrapper<>();
        qw.eq("order_id", orderId);
        return this.list(qw);
    }

    @Override
    public List<ScOrderDetail> getByOrderDetail(Long orderId, String[] notEqOrderStatus) {
        QueryWrapper<ScOrderDetail> qw = new QueryWrapper<>();
        qw.eq("order_id", orderId);
        qw.notIn("order_detail_status", notEqOrderStatus);
        return this.list(qw);
    }

    @Override
    public List<ScOrderDetail> getByOrderDetail(Long orderId, String orderStatus) {
        QueryWrapper<ScOrderDetail> qw = new QueryWrapper<>();
        qw.eq("order_id", orderId);
        qw.eq("order_detail_status", orderStatus);
        return this.list(qw);
    }

    @Override
    public boolean invalidOrder(Long orderId, List<Long> orderDetailId) {
        UpdateWrapper<ScOrderDetail> uw = new UpdateWrapper<>();
        uw.eq("order_id", orderId);
        uw.in("order_detail_id", orderDetailId);
        uw.set("order_detail_status", OrderStatusEnum.INVALID.getOrderStatus());
        return this.update(uw);
    }
}
