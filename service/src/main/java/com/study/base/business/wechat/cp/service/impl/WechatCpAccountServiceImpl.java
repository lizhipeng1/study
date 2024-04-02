package com.study.base.business.wechat.cp.service.impl;

import cn.xluobo.business.wechat.cp.repo.model.WechatCpAccount;
import cn.xluobo.business.wechat.cp.repo.mapper.WechatCpAccountMapper;
import cn.xluobo.business.wechat.cp.service.IWechatCpAccountService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 企业应用信息 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2024-01-23 06:59:58
 */
@Service
public class WechatCpAccountServiceImpl extends ServiceImpl<WechatCpAccountMapper, WechatCpAccount> implements IWechatCpAccountService {

    @Override
    public List<WechatCpAccount> selectAllInUseCpAccount() {
        return baseMapper.selectAllInUserCpAccount();
    }

    @Override
    public WechatCpAccount selectByAgentId(Integer agentId) {
        QueryWrapper<WechatCpAccount> qw = new QueryWrapper<>();
        qw.eq("agent_id", agentId).last("limit 1");
        return this.getOne(qw);
    }

    @Override
    public WechatCpAccount selectByCropId(String corpId) {
        QueryWrapper<WechatCpAccount> qw = new QueryWrapper<>();
        qw.eq("corp_id", corpId).last("limit 1");
        return this.getOne(qw);
    }

    @Override
    public WechatCpAccount selectByCropIdAgentIdIgnoreTenant(String corpId, String agentId) {
        return baseMapper.selectByCropIdAgentIdIgnoreTenant(corpId, agentId);
    }

    @Override
    public WechatCpAccount selectDefaultCpAccount() {
        QueryWrapper<WechatCpAccount> qw = new QueryWrapper<>();
        qw.orderByAsc("create_time").last("limit 1");
        return this.getOne(qw);
    }
}
