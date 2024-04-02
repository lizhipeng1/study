package com.study.base.business.sc.order.service.impl;

import cn.xluobo.business.sc.order.enums.OrderStatusEnum;
import cn.xluobo.business.sc.order.repo.model.ScOrder;
import cn.xluobo.business.sc.order.repo.mapper.ScOrderMapper;
import cn.xluobo.business.sc.order.service.IScOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-08-24 10:22:19
 */
@Service
public class ScOrderServiceImpl extends ServiceImpl<ScOrderMapper, ScOrder> implements IScOrderService {

    @Override
    public boolean invalidOrder(Long orderId) {
        UpdateWrapper<ScOrder> uw = new UpdateWrapper<>();
        uw.eq("order_id", orderId);
        uw.set("order_status", OrderStatusEnum.INVALID.getOrderStatus());
        return this.update(uw);
    }

    @Override
    public Integer orderCount(String beginDate, String endDate, String orderType) {
        QueryWrapper<ScOrder> qw = new QueryWrapper<>();
        qw.eq("order_type", orderType);
        qw.between("handle_date", beginDate, endDate);
        return this.count(qw);
    }

    @Override
    public Integer arrearsStudentCount() {
        return baseMapper.selectArrearsStudentCount();
    }
}
