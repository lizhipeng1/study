package com.study.core.business.wechat.cp.service;

import cn.xluobo.business.wechat.cp.domain.cp.ReqCpExternalContact;
import cn.xluobo.business.wechat.cp.domain.cp.ReqCpUserDetail;
import cn.xluobo.business.wechat.cp.domain.cp.ReqCpUserList;
import cn.xluobo.business.wechat.cp.repo.model.*;
import cn.xluobo.config.exception.BusinessException;
import cn.xluobo.config.tenant.TenantContextHolder;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.bean.external.WxCpUserExternalTagGroupList;
import me.chanjar.weixin.cp.bean.external.contact.ExternalContact;
import me.chanjar.weixin.cp.bean.external.contact.FollowedUser;
import me.chanjar.weixin.cp.bean.external.contact.WxCpExternalContactBatchInfo;
import me.chanjar.weixin.cp.bean.external.contact.WxCpExternalContactInfo;
import me.chanjar.weixin.cp.bean.user.WxCpDeptUserResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 企业微信数据同步服务
 *
 * @projectName: qyxt
 * @package: cn.xluobo.business.wechat.cp.service
 * @className: WechatCpSyncService
 * @author: xluobo
 * @description: 企业微信数据同步服务
 * @date: 2024/1/25 18:22
 */
@Service
@Transactional
public class WechatCpSyncService {

    @Autowired
    private ThirdWechatCpContactService thirdWechatCpContactService;
    @Autowired
    private ThirdWechatCpUserService thirdWechatCpUserService;
    @Autowired
    private IWechatCpAccountService dbCpAccountService;
    @Autowired
    private IWechatCpContactService dbCpContactService;
    @Autowired
    private IWechatCpContactFollowService dbCpContactFollowService;
    @Autowired
    private IWechatCpContactFollowTagService dbCpContactFollowTagService;
    @Autowired
    private IWechatCpGroupService groupService;
    @Autowired
    private IWechatCpGroupTagService groupTagService;
    @Autowired
    private IWechatCpUserService dbCpUserService;
    @Autowired
    private IWechatCpContactWayService dbCpContactWayService;


    /**
     * 内部应用同步联系人信息
     */
    @Async
    public void syncSelfWechatCpContact(String tenantId) {
        try {
            TenantContextHolder.setTenant(tenantId);

            WechatCpAccount wechatCpAccount = dbCpAccountService.selectDefaultCpAccount();
            if (null == wechatCpAccount) {
                return;
            }

            List<String> userList = thirdWechatCpContactService.getFlowUserList(wechatCpAccount.getCorpId(), wechatCpAccount.getAgentId());

            ReqCpExternalContact reqCpExternalContact = new ReqCpExternalContact();
            reqCpExternalContact.setUserIdList(userList);
            reqCpExternalContact.setCursor("");
            reqCpExternalContact.setLimit(100);
            reqCpExternalContact.setCorpId(wechatCpAccount.getCorpId());
            reqCpExternalContact.setAgentId(wechatCpAccount.getAgentId());
            this.getContactAndSave(reqCpExternalContact);
        } catch (WxErrorException e) {
            throw new BusinessException(e.getMessage());
        } finally {
            TenantContextHolder.remove();
        }
    }

    /**
     * 内部应用同步企业标签信息
     */
    @Async
    public void syncSelfWechatCpTag(String tenantId) {
        try {
            TenantContextHolder.setTenant(tenantId);

            WechatCpAccount wechatCpAccount = dbCpAccountService.selectDefaultCpAccount();
            if (null == wechatCpAccount) {
                return;
            }

            WxCpUserExternalTagGroupList externalTagGroupList = thirdWechatCpContactService.getExternalTagGroupList(wechatCpAccount.getCorpId(), wechatCpAccount.getAgentId(), null, null);
            List<WxCpUserExternalTagGroupList.TagGroup> tagGroupList = externalTagGroupList.getTagGroupList();

            List<WechatCpGroup> groupList = Lists.newArrayList();
            List<WechatCpGroupTag> groupTagList = Lists.newArrayList();

            for (WxCpUserExternalTagGroupList.TagGroup tagGroup : tagGroupList) {
                groupList.add(WechatCpGroup.transferByCpBean(tagGroup));

                List<WxCpUserExternalTagGroupList.TagGroup.Tag> tagList = tagGroup.getTag();
                List<WechatCpGroupTag> list = tagList.stream().map(item -> WechatCpGroupTag.transferByCpBean(tagGroup.getGroupId(), item)).collect(Collectors.toList());
                groupTagList.addAll(list);
            }
            if (!groupList.isEmpty()) {
                groupService.saveOrUpdateBatch(groupList);
            }
            if (!groupTagList.isEmpty()) {
                groupTagService.saveOrUpdateBatch(groupTagList);
            }
        } catch (WxErrorException e) {
            throw new BusinessException(e.getMessage());
        } finally {
            TenantContextHolder.remove();
        }
    }

    /**
     * 同步企业成员
     * @param tenantId
     */
    @Async
    public void syncSelfWechatCpUser(String tenantId) {
        try {
            TenantContextHolder.setTenant(tenantId);

            WechatCpAccount wechatCpAccount = dbCpAccountService.selectDefaultCpAccount();
            if (null == wechatCpAccount) {
                return;
            }

            ReqCpUserList reqCpUserList = new ReqCpUserList();
            reqCpUserList.setCorpId(wechatCpAccount.getCorpId());
            reqCpUserList.setAgentId(wechatCpAccount.getAgentId());
            reqCpUserList.setCursor("");
            reqCpUserList.setLimit(100);
            this.getUserAndSave(reqCpUserList);
        } catch (WxErrorException e) {
            throw new BusinessException(e.getMessage());
        } finally {
            TenantContextHolder.remove();
        }
    }

    /**
     * 获取并存储联系人信息
     *
     * @param reqCpExternalContact
     * @throws WxErrorException
     */
    private void getContactAndSave(ReqCpExternalContact reqCpExternalContact) throws WxErrorException {
        WxCpExternalContactBatchInfo contactDetailBatch = thirdWechatCpContactService.getContactDetailBatch(reqCpExternalContact);
        List<WxCpExternalContactBatchInfo.ExternalContactInfo> externalContactList = contactDetailBatch.getExternalContactList();

        List<WechatCpContact> wechatCpContactList = Lists.newArrayList();
        List<WechatCpContactFollow> contactFollowList = Lists.newArrayList();

        // 批量获取联系人详情时，不返回个人标签信息
        // List<WechatCpContactFollowTag> followTagList = Lists.newArrayList();

        for (WxCpExternalContactBatchInfo.ExternalContactInfo externalContactInfo : externalContactList) {
            ExternalContact externalContact = externalContactInfo.getExternalContact();
            FollowedUser followInfo = externalContactInfo.getFollowInfo();

            wechatCpContactList.add(WechatCpContact.transferByCpBean(externalContact));
            contactFollowList.add(WechatCpContactFollow.transferByCpBean(externalContact.getExternalUserId(), followInfo));
            // followTagList.addAll(WechatCpContactFollowTag.transferByCpBean(externalContact.getExternalUserId(), followInfo));

            dbCpContactFollowTagService.deleteTag(externalContact.getExternalUserId(), followInfo.getUserId());
        }
        if (!wechatCpContactList.isEmpty()) {
            dbCpContactService.saveOrUpdateBatch(wechatCpContactList);
        }

        if (!contactFollowList.isEmpty()) {
            dbCpContactFollowService.saveOrUpdateBatch(contactFollowList);
        }

        /*if (!followTagList.isEmpty()) {
            dbCpContactFollowTagService.saveOrUpdateBatch(followTagList);
        }*/

        String nextCursor = contactDetailBatch.getNextCursor();
        if (StringUtils.isNotEmpty(nextCursor)) {
            reqCpExternalContact.setCursor(nextCursor);
            this.getContactAndSave(reqCpExternalContact);
        }
    }

    /**
     * 获取并存储用户信息
     * @param reqCpUserList
     * @throws WxErrorException
     */
    private void getUserAndSave(ReqCpUserList reqCpUserList) throws WxErrorException {
        WxCpDeptUserResult wxCpDeptUserResult = thirdWechatCpUserService.getUserListId(reqCpUserList);
        String nextCursor = wxCpDeptUserResult.getNextCursor();

        List<WxCpDeptUserResult.DeptUserList> deptUserList = wxCpDeptUserResult.getDeptUser();
        Set<String> userIdSet = deptUserList.stream().map(WxCpDeptUserResult.DeptUserList::getUserId).collect(Collectors.toSet());

        List<WechatCpUser> cpUserList = Lists.newArrayList();

        // 获取详情，并转换为 WechatCpUser
        ReqCpUserDetail reqCpUserDetail = new ReqCpUserDetail();
        reqCpUserDetail.setCorpId(reqCpUserList.getCorpId());
        reqCpUserDetail.setAgentId(reqCpUserList.getAgentId());
        for (String userId : userIdSet) {
            reqCpUserDetail.setUserId(userId);
            WxCpUser wxCpUser = thirdWechatCpUserService.getById(reqCpUserDetail);
            WechatCpUser wechatCpUser = WechatCpUser.transferByCpBean(wxCpUser);
            cpUserList.add(wechatCpUser);
        }

        // 存储
        dbCpUserService.saveOrUpdateBatch(cpUserList);

        if (StringUtils.isNotEmpty(nextCursor)) {
            reqCpUserList.setCursor(nextCursor);
            this.getUserAndSave(reqCpUserList);
        }
    }

    /**
     * 事件处理
     * 添加企业客户事件、编辑企业客户事件、外部联系人免验证添加成员事件
     * 存储客户信息并打标签
     *
     * @param cropId
     * @param externalUserId
     * @param userId
     * @param state
     */
    public void saveExternalUserAndMargTag(String cropId,
                                           String agentId,
                                           String externalUserId,
                                           String userId,
                                           String state) throws WxErrorException {
        WechatCpAccount wechatCpAccount = dbCpAccountService.selectByCropIdAgentIdIgnoreTenant(cropId, agentId);
        if (null == wechatCpAccount) {
            return;
        }
        try {
            TenantContextHolder.setTenant(wechatCpAccount.getTenantId());

            // 存储客户信息
            WxCpExternalContactInfo externalContactInfo = thirdWechatCpContactService.getContactDetail(wechatCpAccount.getCorpId(), wechatCpAccount.getAgentId(), externalUserId);
            WechatCpContact wechatCpContact = WechatCpContact.transferByCpBean(externalContactInfo.getExternalContact());

            List<FollowedUser> followedUserList = externalContactInfo.getFollowedUsers();
            List<WechatCpContactFollow> cpContactFollowList = followedUserList.stream().map(item -> WechatCpContactFollow.transferByCpBean(wechatCpContact.getExternalUserId(), item)).collect(Collectors.toList());

            List<WechatCpContactFollowTag> followTagList = Lists.newArrayList();
            for (FollowedUser followedUser : followedUserList) {
                List<WechatCpContactFollowTag> itemList = WechatCpContactFollowTag.transferByCpBean(wechatCpContact.getExternalUserId(), followedUser);
                followTagList.addAll(itemList);

                dbCpContactFollowTagService.deleteTag(wechatCpContact.getExternalUserId(), followedUser.getUserId());
            }

            dbCpContactService.saveOrUpdate(wechatCpContact);
            dbCpContactFollowService.saveOrUpdateBatch(cpContactFollowList);
            dbCpContactFollowTagService.saveBatch(followTagList);


            if (StringUtils.isEmpty(state)) {
                return;
            }
            // 获取需要打哪些标签
            WechatCpContactWay wechatCpContactWay = dbCpContactWayService.selectByState(state);
            if (null == wechatCpContactWay) {
                return;
            }
            // 打标签
            String[] addTags = wechatCpContactWay.getAddTags();
            if (null == addTags || addTags.length == 0) {
                return;
            }
            thirdWechatCpContactService.externalContactMarkTag(cropId,
                    wechatCpAccount.getAgentId(),
                    userId,
                    externalUserId,
                    addTags,
                    null);

        } finally {
            TenantContextHolder.remove();
        }
    }

    /**
     * 配置了客户联系功能的成员被外部联系人删除时
     * @param cropId
     * @param agentId
     * @param externalUserId
     * @param userId
     */
    public void delExternalFollowUser(String cropId,
                                      String agentId,
                                      String externalUserId,
                                      String userId) {
        WechatCpAccount wechatCpAccount = dbCpAccountService.selectByCropIdAgentIdIgnoreTenant(cropId, agentId);
        if (null == wechatCpAccount) {
            return;
        }
        try {
            TenantContextHolder.setTenant(wechatCpAccount.getTenantId());

            dbCpContactFollowService.deleteContactFollow(externalUserId, userId);
        } finally {
            TenantContextHolder.remove();
        }
    }

    /**
     * 配置了客户联系功能的成员删除外部联系人时
     * @param cropId
     * @param agentId
     * @param externalUserId
     */
    public void delExternalContact(String cropId,
                                   String agentId,
                                   String externalUserId) {
        WechatCpAccount wechatCpAccount = dbCpAccountService.selectByCropIdAgentIdIgnoreTenant(cropId, agentId);
        if (null == wechatCpAccount) {
            return;
        }
        try {
            TenantContextHolder.setTenant(wechatCpAccount.getTenantId());
            UpdateWrapper<WechatCpContact> uw = new UpdateWrapper<>();
            uw.eq("external_user_id", externalUserId).set("delete_flag", "1");
            dbCpContactService.update(uw);
        } finally {
            TenantContextHolder.remove();
        }
    }

}
