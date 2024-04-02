package com.study.base.business.sys.receipt.service.impl;

import cn.xluobo.business.sys.receipt.repo.model.SysReceiptAccount;
import cn.xluobo.business.sys.receipt.repo.mapper.SysReceiptAccountMapper;
import cn.xluobo.business.sys.receipt.service.ISysReceiptAccountService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 收款账户 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-05 05:36:25
 */
@Service
public class SysReceiptAccountServiceImpl extends ServiceImpl<SysReceiptAccountMapper, SysReceiptAccount> implements ISysReceiptAccountService {

    @Override
    public List<SysReceiptAccount> select() {
        QueryWrapper<SysReceiptAccount> qw = new QueryWrapper<>();
        qw.select("account_name", "account_id");
        qw.orderByDesc("account_id");
        return this.list(qw);
    }
}
