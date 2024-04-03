package com.study.business.wechat.cp.domain.req;

import com.study.business.wechat.cp.repo.model.WechatCpContactWay;
import lombok.Data;

/**
 * @projectName: qyxt
 * @package: com.study.business.wechat.cp.domain.req
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
