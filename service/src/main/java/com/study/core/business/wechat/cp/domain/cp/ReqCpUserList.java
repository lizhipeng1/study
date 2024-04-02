package com.study.core.business.wechat.cp.domain.cp;

import lombok.Data;

import static cn.xluobo.business.wechat.cp.repo.enums.WechatCpAgentSecretTypeEnum.ADDRESS_BOOK_SECRET_AGENT;

/**
 * @projectName: qyxt
 * @package: cn.xluobo.business.wechat.cp.domain.cp
 * @className: ReqCpUser
 * @author: xluobo
 * @description: TODO
 * @date: 2024/1/27 18:41
 */
@Data
public class ReqCpUserList extends ReqCpBase {

    /**
     * 企业通讯录应用id
     */
    private Integer addressBookAgentId = ADDRESS_BOOK_SECRET_AGENT.getAgentId();

    private String cursor;

    private Integer limit;

}
