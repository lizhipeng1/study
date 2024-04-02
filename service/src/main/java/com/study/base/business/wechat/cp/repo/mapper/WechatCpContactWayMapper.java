package com.study.base.business.wechat.cp.repo.mapper;

import cn.xluobo.business.wechat.cp.domain.req.ReqSearchWechatCpContactWay;
import cn.xluobo.business.wechat.cp.domain.req.RespWechatCpContactWay;
import cn.xluobo.business.wechat.cp.repo.model.WechatCpContactWay;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 客户联系我方式 Mapper 接口
 * </p>
 *
 * @author xluobo
 * @since 2024-01-26 07:13:01
 */
public interface WechatCpContactWayMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<WechatCpContactWay> {

    List<RespWechatCpContactWay> selectForSearchTable(@Param("reqSearchWechatCpContactWay") ReqSearchWechatCpContactWay reqSearchWechatCpContactWay, Page page);

}
