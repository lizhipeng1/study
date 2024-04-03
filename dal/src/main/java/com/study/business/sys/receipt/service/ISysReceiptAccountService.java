package com.study.business.sys.receipt.service;

import com.study.business.sys.receipt.repo.model.SysReceiptAccount;

import java.util.List;

/**
 * <p>
 * 收款账户 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-05 05:36:25
 */
public interface ISysReceiptAccountService extends com.baomidou.mybatisplus.extension.service.IService<SysReceiptAccount> {

    List<SysReceiptAccount> select();

}
