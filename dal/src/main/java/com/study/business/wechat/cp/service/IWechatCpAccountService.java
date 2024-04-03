package com.study.business.wechat.cp.service;

import com.study.business.wechat.cp.repo.model.WechatCpAccount;

import java.util.List;

/**
 * <p>
 * 企业应用信息 服务类
 * </p>
 *
 * @author zhangby
 * @since 2024-01-23 06:59:58
 */
public interface IWechatCpAccountService extends com.baomidou.mybatisplus.extension.service.IService<WechatCpAccount> {

    List<WechatCpAccount> selectAllInUseCpAccount();

    /**
     * 获取租户 应用信息
     * @param agentId
     * @return
     */
    WechatCpAccount selectByAgentId(Integer agentId);

    /**
     * 获取租户 应用信息
     *
     * @param corpId
     * @return
     */
    WechatCpAccount selectByCropId(String corpId);

    /**
     * 获取租户 应用信息
     *
     * @param corpId
     * @return
     */
    WechatCpAccount selectByCropIdAgentIdIgnoreTenant(String corpId, String agentId);

    /**
     * 获取租户默认应用信息
     * @return
     */
    WechatCpAccount selectDefaultCpAccount();

}
