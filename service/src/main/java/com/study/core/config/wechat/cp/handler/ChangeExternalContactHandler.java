package com.study.core.config.wechat.cp.handler;

import cn.xluobo.business.wechat.cp.service.WechatCpSyncService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.constant.WxCpConsts;
import me.chanjar.weixin.cp.message.WxCpMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 新增外部联系人
 *
 * @projectName: qyxt
 * @package: cn.xluobo.config.wechat.cp.handler
 * @className: AddExternalContactHandler
 * @author: xluobo
 * @description: 配置了客户联系功能的成员添加外部联系人时，回调该事件
 * @date: 2024/1/31 18:30
 */
@Component
@Slf4j
public class ChangeExternalContactHandler implements WxCpMessageHandler {

    @Autowired
    private WechatCpSyncService cpSyncService;

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService, WxSessionManager sessionManager) throws WxErrorException {

        // 企业微信CorpID
        String cropId = wxMessage.getToUserName();
        String agentId = wxMessage.getAgentId();
        // 企业服务人员的UserID
        String userId = wxMessage.getUserId();
        // 外部联系人的userid，注意不是企业成员的账号
        String externalUserId = wxMessage.getExternalUserId();
        // 添加此用户的「联系我」方式配置的state参数，或在获客链接中指定的customer_channel参数，可用于识别添加此用户的渠道
        String state = wxMessage.getState();

        String changeType = wxMessage.getChangeType();


        if (changeType.equals(WxCpConsts.ExternalContactChangeType.ADD_EXTERNAL_CONTACT)
                || changeType.equals(WxCpConsts.ExternalContactChangeType.EDIT_EXTERNAL_CONTACT)
                || changeType.equals(WxCpConsts.ExternalContactChangeType.ADD_HALF_EXTERNAL_CONTACT)
        ) {
            // 添加企业客户事件、编辑企业客户事件、外部联系人免验证添加成员事件
            cpSyncService.saveExternalUserAndMargTag(cropId, agentId, externalUserId, userId, state);
        } else if (changeType.equals(WxCpConsts.ExternalContactChangeType.DEL_FOLLOW_USER)) {
            // 配置了客户联系功能的成员被外部联系人删除时，回调该事件
            cpSyncService.delExternalFollowUser(cropId, agentId, externalUserId, userId);
        } else if (changeType.equals(WxCpConsts.ExternalContactChangeType.DEL_EXTERNAL_CONTACT)) {
            // 配置了客户联系功能的成员删除外部联系人时，回调该事件
            cpSyncService.delExternalContact(cropId, agentId, externalUserId);
        }

        return null;
    }


}