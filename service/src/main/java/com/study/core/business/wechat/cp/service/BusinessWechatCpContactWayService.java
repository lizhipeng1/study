package com.study.core.business.wechat.cp.service;

import cn.xluobo.business.wechat.cp.domain.req.ReqSearchWechatCpContactWay;
import cn.xluobo.business.wechat.cp.domain.req.RespWechatCpContactWay;
import cn.xluobo.business.wechat.cp.repo.model.WechatCpAccount;
import cn.xluobo.business.wechat.cp.repo.model.WechatCpContactWay;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.api.ApiResEnums;
import cn.xluobo.core.page.RespPage;
import cn.xluobo.utils.LoginUserUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.external.WxCpContactWayInfo;
import me.chanjar.weixin.cp.bean.external.WxCpContactWayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessWechatCpContactWayService {

    @Autowired
    private IWechatCpContactWayService wechatCpContactWayService;
    @Autowired
    private ThirdWechatCpContactService cpContactService;
    @Autowired
    private IWechatCpAccountService wechatCpAccountService;

    /**
     * 查询
     *
     * @param reqSearchWechatCpContactWay
     * @return
     */
    public APIResponse searchList(ReqSearchWechatCpContactWay reqSearchWechatCpContactWay) {
        RespPage<RespWechatCpContactWay> page = new RespPage(reqSearchWechatCpContactWay.getPageNum(), reqSearchWechatCpContactWay.getPageSize());
        List<RespWechatCpContactWay> listPage = wechatCpContactWayService.selectForSearchTable(reqSearchWechatCpContactWay, page);
        page.setRows(listPage);
        return APIResponse.toAPIResponse(page);
    }

    /**
     * 详情
     *
     * @param configId
     * @return
     */
    public APIResponse detailById(String configId) {
        if (null == configId) {
            return APIResponse.toAPIResponse(null);
        }
        WechatCpContactWay detailInfo = wechatCpContactWayService.getById(configId);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param wechatCpContactWay
     * @return
     */
    public APIResponse addWechatCpContactWay(WechatCpContactWay wechatCpContactWay) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        wechatCpContactWay.setCreateUser(loginUser.getUserId());
        wechatCpContactWay.setRemark(wechatCpContactWay.getContactWayName());

        WxCpContactWayInfo wxCpContactWayInfo = wechatCpContactWay.transferWxCpContactWay();
        WechatCpAccount wechatCpAccount = wechatCpAccountService.selectDefaultCpAccount();
        WxCpContactWayResult wxCpContactWayResult = null;
        try {
            wxCpContactWayResult = cpContactService.addContactWay(wechatCpAccount.getCorpId(), wechatCpAccount.getAgentId(), wxCpContactWayInfo);
        } catch (WxErrorException e) {
            return APIResponse.toExceptionResponse(e.toString());
        }

        wechatCpContactWay.setConfigId(wxCpContactWayResult.getConfigId());
        wechatCpContactWay.setQrCode(wxCpContactWayResult.getQrCode());
        boolean addWechatCpContactWay = wechatCpContactWayService.save(wechatCpContactWay);
        if (addWechatCpContactWay) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param wechatCpContactWay
     * @return
     */
    public APIResponse updateWechatCpContactWay(WechatCpContactWay wechatCpContactWay) {
        if (null == wechatCpContactWay.getConfigId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        wechatCpContactWay.setLastUpdateUser(loginUser.getUserId());
        wechatCpContactWay.setLastUpdateTime(new Date());
        wechatCpContactWay.setRemark(wechatCpContactWay.getContactWayName());

        WxCpContactWayInfo wxCpContactWayInfo = wechatCpContactWay.transferWxCpContactWay();
        WechatCpAccount wechatCpAccount = wechatCpAccountService.selectDefaultCpAccount();
        try {
            cpContactService.updateContactWay(wechatCpAccount.getCorpId(), wechatCpAccount.getAgentId(), wxCpContactWayInfo);
        } catch (WxErrorException e) {
            return APIResponse.toExceptionResponse(e.toString());
        }

        boolean updateWechatCpContactWay = wechatCpContactWayService.updateById(wechatCpContactWay);
        if (updateWechatCpContactWay) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param configIds
     * @return
     */
    public APIResponse deleteById(String[] configIds) {
        if (null == configIds || configIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        WechatCpAccount wechatCpAccount = wechatCpAccountService.selectDefaultCpAccount();
        for (String configId : configIds) {
            try {
                cpContactService.deleteContactWay(wechatCpAccount.getCorpId(), wechatCpAccount.getAgentId(), configId);
            } catch (WxErrorException e) {
                return APIResponse.toExceptionResponse(e.toString());
            }
            wechatCpContactWayService.removeByIds(Arrays.asList(configIds));
        }
        return APIResponse.toOkResponse();
    }
}
