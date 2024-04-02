package com.study.base.business.wechat.cp.domain.req;

import cn.xluobo.business.wechat.cp.repo.model.WechatCpContactWay;
import lombok.Data;

/**
 * @projectName: qyxt
 * @package: cn.xluobo.business.wechat.cp.domain.req
 * @className: RespWechatCpContactWay
 * @author: xluobo
 * @description: TODO
 * @date: 2024/1/31 13:37
 */
@Data
public class RespWechatCpContactWay extends WechatCpContactWay {

    private String userNames;

    private String addTagNames;

}
