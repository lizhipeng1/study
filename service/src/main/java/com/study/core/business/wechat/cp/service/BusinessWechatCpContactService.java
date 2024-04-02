package com.study.core.business.wechat.cp.service;

import cn.xluobo.business.wechat.cp.domain.req.ReqSearchWechatCpContact;
import cn.xluobo.business.wechat.cp.repo.model.WechatCpContact;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.api.ApiResEnums;
import cn.xluobo.core.page.RespPage;
import cn.xluobo.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessWechatCpContactService {

    @Autowired
    private IWechatCpContactService wechatCpContactService;

    /**
     * 查询
     *
     * @param reqSearchWechatCpContact
     * @return
     */
    public APIResponse searchList(ReqSearchWechatCpContact reqSearchWechatCpContact) {
        QueryWrapper qw = new QueryWrapper();
        RespPage<WechatCpContact> page = new RespPage(reqSearchWechatCpContact.getPageNum(), reqSearchWechatCpContact.getPageSize());
        RespPage<WechatCpContact> listPage = wechatCpContactService.page(page, qw);
        return APIResponse.toAPIResponse(listPage);
    }

    /**
     * 详情
     *
     * @param externalUserId
     * @return
     */
    public APIResponse detailById(String externalUserId) {
        if (null == externalUserId) {
            return APIResponse.toAPIResponse(null);
        }
        WechatCpContact detailInfo = wechatCpContactService.getById(externalUserId);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param wechatCpContact
     * @return
     */
    public APIResponse addWechatCpContact(WechatCpContact wechatCpContact) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        wechatCpContact.setCreateUser(loginUser.getUserId());
        boolean addWechatCpContact = wechatCpContactService.save(wechatCpContact);
        if (addWechatCpContact) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param wechatCpContact
     * @return
     */
    public APIResponse updateWechatCpContact(WechatCpContact wechatCpContact) {
        if (null == wechatCpContact.getExternalUserId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        wechatCpContact.setLastUpdateUser(loginUser.getUserId());
        wechatCpContact.setLastUpdateTime(new Date());
        boolean updateWechatCpContact = wechatCpContactService.updateById(wechatCpContact);
        if (updateWechatCpContact) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param externalUserIds
     * @return
     */
    public APIResponse deleteById(String[] externalUserIds) {
        if (null == externalUserIds || externalUserIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        boolean deleteWechatCpContact = wechatCpContactService.removeByIds(Arrays.asList(externalUserIds));
        if (deleteWechatCpContact) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }
}
