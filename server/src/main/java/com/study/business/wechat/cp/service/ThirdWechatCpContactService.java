package com.study.business.wechat.cp.service;

import com.study.business.wechat.cp.domain.cp.ReqCpExternalContact;
import com.study.business.wechat.cp.repo.model.WechatCpAccount;
import com.study.config.exception.BusinessException;
import com.study.config.wechat.cp.WechatCpConfiguration;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpExternalContactService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.bean.external.WxCpContactWayInfo;
import me.chanjar.weixin.cp.bean.external.WxCpContactWayResult;
import me.chanjar.weixin.cp.bean.external.WxCpUserExternalTagGroupList;
import me.chanjar.weixin.cp.bean.external.contact.WxCpExternalContactBatchInfo;
import me.chanjar.weixin.cp.bean.external.contact.WxCpExternalContactInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 企业微信 客户管理服务类
 * @author zhangbaoyu
 */
@Service
@Transactional
public class ThirdWechatCpContactService {

    @Autowired
    private IWechatCpAccountService cpAccountService;

    /**
     * 获取配置了客户联系功能的成员列表
     * @param corpId
     * @param agentId
     * @return
     * @throws WxErrorException
     */
    public List<String> getFlowUserList(String corpId, Integer agentId) throws WxErrorException {
        WxCpService cpService = WechatCpConfiguration.getCpService(corpId, agentId);
        WxCpExternalContactService externalContactService = cpService.getExternalContactService();
        return externalContactService.listFollowers();
    }

    /**
     * 获取企业微信客户信息
     * @param corpId
     * @param agentId
     * @param externalUserId
     * @return
     * @throws WxErrorException
     */
    public WxCpExternalContactInfo getContactDetail(String corpId, Integer agentId, String externalUserId) throws WxErrorException {
        WxCpService cpService = WechatCpConfiguration.getCpService(corpId, agentId);
        WxCpExternalContactService externalContactService = cpService.getExternalContactService();

        return externalContactService.getContactDetail(externalUserId,null);
    }

    /**
     * 批量获取企业微信客户信息
     * @param reqCpExternalContact
     * @return
     * @throws WxErrorException
     */
    public WxCpExternalContactBatchInfo getContactDetailBatch(ReqCpExternalContact reqCpExternalContact) throws WxErrorException {
        WechatCpAccount wechatCpAccount = cpAccountService.selectDefaultCpAccount();
        if (wechatCpAccount == null) {
            throw new BusinessException("can not find default cp account config");
        }
        WxCpService cpService = WechatCpConfiguration.getCpService(wechatCpAccount.getCorpId(), wechatCpAccount.getAgentId());
        WxCpExternalContactService externalContactService = cpService.getExternalContactService();

        List<String> userIdList = reqCpExternalContact.getUserIdList();
        String[] userIds = userIdList.toArray(new String[0]);
        return externalContactService.getContactDetailBatch(userIds, reqCpExternalContact.getCursor(), reqCpExternalContact.getLimit());
    }


    /**
     * 编辑客户企业标签
     * @param corpId
     * @param agentId
     * @param userid
     * @param externalUserid
     * @param addTag
     * @param removeTag
     * @return
     * @throws WxErrorException
     */
    public WxCpBaseResp externalContactMarkTag(String corpId,
                                               Integer agentId,
                                               String userid,
                                               String externalUserid,
                                               String[] addTag,
                                               String[] removeTag) throws WxErrorException {
        WxCpService cpService = WechatCpConfiguration.getCpService(corpId, agentId);
        WxCpExternalContactService externalContactService = cpService.getExternalContactService();
        return externalContactService.markTag(userid, externalUserid, addTag, removeTag);

    }

    /**
     * 获取客户 标签 tag
     * @param corpId
     * @param agentId
     * @param tagId
     * @param groupId
     * @throws WxErrorException
     */
    public WxCpUserExternalTagGroupList getExternalTagGroupList(String corpId, Integer agentId,
                                                   String[] tagId, String[] groupId) throws WxErrorException {
        WxCpService cpService = WechatCpConfiguration.getCpService(corpId, agentId);
        WxCpExternalContactService externalContactService = cpService.getExternalContactService();
        return externalContactService.getCorpTagList(tagId, groupId);
    }

    /**
     * 配置客户联系「联系我」方式
     * @param corpId
     * @param agentId
     * @param contactWayInfo
     * @return
     * @throws WxErrorException
     */
    public WxCpContactWayResult addContactWay(String corpId, Integer agentId, WxCpContactWayInfo contactWayInfo) throws WxErrorException {
        WxCpService cpService = WechatCpConfiguration.getCpService(corpId, agentId);
        WxCpExternalContactService externalContactService = cpService.getExternalContactService();
        return externalContactService.addContactWay(contactWayInfo);
    }

    /**
     * 更新客户联系我方式
     * @param corpId
     * @param agentId
     * @param contactWayInfo
     * @return
     * @throws WxErrorException
     */
    public WxCpBaseResp updateContactWay(String corpId, Integer agentId, WxCpContactWayInfo contactWayInfo) throws WxErrorException {
        WxCpService cpService = WechatCpConfiguration.getCpService(corpId, agentId);
        WxCpExternalContactService externalContactService = cpService.getExternalContactService();
        return externalContactService.updateContactWay(contactWayInfo);
    }

    /**
     * 删除客户联系我方式
     * @param corpId
     * @param agentId
     * @param configId
     * @return
     * @throws WxErrorException
     */
    public WxCpBaseResp deleteContactWay(String corpId, Integer agentId, String configId) throws WxErrorException {
        WxCpService cpService = WechatCpConfiguration.getCpService(corpId, agentId);
        WxCpExternalContactService externalContactService = cpService.getExternalContactService();
        return externalContactService.deleteContactWay(configId);
    }



}
