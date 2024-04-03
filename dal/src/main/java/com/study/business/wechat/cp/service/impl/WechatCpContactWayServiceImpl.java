package com.study.business.wechat.cp.service.impl;

import com.study.business.wechat.cp.domain.req.ReqSearchWechatCpContactWay;
import com.study.business.wechat.cp.domain.req.RespWechatCpContactWay;
import com.study.business.wechat.cp.repo.mapper.WechatCpContactWayMapper;
import com.study.business.wechat.cp.repo.model.WechatCpContactWay;
import com.study.business.wechat.cp.service.IWechatCpContactWayService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 客户联系我方式 服务实现类
 * </p>
 *
 * @author xluobo
 * @since 2024-01-26 07:13:01
 */
@Service
public class WechatCpContactWayServiceImpl extends ServiceImpl<WechatCpContactWayMapper, WechatCpContactWay> implements IWechatCpContactWayService {

    @Override
    public List<RespWechatCpContactWay> selectForSearchTable(ReqSearchWechatCpContactWay reqSearchWechatCpContactWay, Page page) {
        return baseMapper.selectForSearchTable(reqSearchWechatCpContactWay, page);
    }

    @Override
    public WechatCpContactWay selectByState(String state) {
        QueryWrapper<WechatCpContactWay> qw = new QueryWrapper<>();
        qw.eq("state", state).last("limit 1");
        return this.getOne(qw);
    }
}
