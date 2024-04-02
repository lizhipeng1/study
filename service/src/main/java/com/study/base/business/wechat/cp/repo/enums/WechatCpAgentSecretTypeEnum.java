package com.study.base.business.wechat.cp.repo.enums;

import lombok.Getter;

/**
 * 企业微信秘钥类型
 * @projectName: qyxt
 * @package: cn.xluobo.business.wechat.cp.repo.enums
 * @className: WechatCpAgentTypeEnum
 * @author: xluobo
 * @description: TODO
 * @date: 2024/1/28 23:12
 */
@Getter
public enum WechatCpAgentSecretTypeEnum {

    ADDRESS_BOOK_SECRET_AGENT(1);

    private final Integer agentId;

    WechatCpAgentSecretTypeEnum(Integer agentId) {
        this.agentId = agentId;
    }
}
