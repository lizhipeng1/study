package com.study.base.business.sc.order.service.impl;

import cn.xluobo.business.sc.order.repo.model.ScOrderAccount;
import cn.xluobo.business.sc.order.repo.mapper.ScOrderAccountMapper;
import cn.xluobo.business.sc.order.service.IScOrderAccountService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单收款账户 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
@Service
public class ScOrderAccountServiceImpl extends ServiceImpl<ScOrderAccountMapper, ScOrderAccount> implements IScOrderAccountService {

    @Override
    public List<ScOrderAccount> getOrderAccountList(Long orderId) {
        QueryWrapper<ScOrderAccount> qw = new QueryWrapper<>();
        qw.select("account_name", "fee");
        qw.eq("order_id", orderId);
        return this.list(qw);
    }
}
