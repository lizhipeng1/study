package com.study.business.wechat.cp.service;

import com.study.business.wechat.cp.domain.req.ReqSearchWechatCpContactWay;
import com.study.business.wechat.cp.domain.req.RespWechatCpContactWay;
import com.study.business.wechat.cp.repo.model.WechatCpContactWay;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 客户联系我方式 服务类
 * </p>
 *
 * @author xluobo
 * @since 2024-01-26 07:13:01
 */
public interface IWechatCpContactWayService extends com.baomidou.mybatisplus.extension.service.IService<WechatCpContactWay> {

    List<RespWechatCpContactWay> selectForSearchTable(ReqSearchWechatCpContactWay reqSearchWechatCpContactWay, Page page);

    WechatCpContactWay selectByState(String state);
}
