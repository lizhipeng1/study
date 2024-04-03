package com.study.business.wechat.cp.service;

import com.study.business.wechat.cp.domain.req.ReqSearchWechatCpAccount;
import com.study.business.wechat.cp.repo.model.WechatCpAccount;
import com.study.config.login.LoginUser;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.study.core.page.RespPage;

import java.util.Arrays;
import java.util.Date;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessWechatCpAccountService {

    @Autowired
    private IWechatCpAccountService wechatCpAccountService;

    /**
     * 查询
     *
     * @param reqSearchWechatCpAccount
     * @return
     */
    public APIResponse searchList(ReqSearchWechatCpAccount reqSearchWechatCpAccount) {
        QueryWrapper qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(reqSearchWechatCpAccount.getCompanyName())){
            qw.like("company_name",reqSearchWechatCpAccount.getCompanyName());
        }
        if(StringUtils.isNotEmpty(reqSearchWechatCpAccount.getCorpId())){
            qw.eq("corp_id",reqSearchWechatCpAccount.getCorpId());
        }
        if(StringUtils.isNotEmpty(reqSearchWechatCpAccount.getAgentId())){
            qw.eq("agent_id",reqSearchWechatCpAccount.getAgentId());
        }
        RespPage<WechatCpAccount> page = new RespPage(reqSearchWechatCpAccount.getPageNum(), reqSearchWechatCpAccount.getPageSize());
        RespPage<WechatCpAccount> listPage = wechatCpAccountService.page(page, qw);
        return APIResponse.toAPIResponse(listPage);
    }

    /**
     * 详情
     *
     * @param cpAccountId
     * @return
     */
    public APIResponse detailById(String cpAccountId) {
        if (null == cpAccountId) {
            return APIResponse.toAPIResponse(null);
        }
        WechatCpAccount detailInfo = wechatCpAccountService.getById(cpAccountId);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param wechatCpAccount
     * @return
     */
    public APIResponse addWechatCpAccount(WechatCpAccount wechatCpAccount) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        wechatCpAccount.setCreateUser(loginUser.getUserId());
        boolean addWechatCpAccount = wechatCpAccountService.save(wechatCpAccount);
        if (addWechatCpAccount) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param wechatCpAccount
     * @return
     */
    public APIResponse updateWechatCpAccount(WechatCpAccount wechatCpAccount) {
        if (null == wechatCpAccount.getCpAccountId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        wechatCpAccount.setLastUpdateUser(loginUser.getUserId());
        wechatCpAccount.setLastUpdateTime(new Date());
        boolean updateWechatCpAccount = wechatCpAccountService.updateById(wechatCpAccount);
        if (updateWechatCpAccount) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param cpAccountIds
     * @return
     */
    public APIResponse deleteById(String[] cpAccountIds) {
        if (null == cpAccountIds || cpAccountIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        boolean deleteWechatCpAccount = wechatCpAccountService.removeByIds(Arrays.asList(cpAccountIds));
        if (deleteWechatCpAccount) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }
}
