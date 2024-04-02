package com.study.core.business.sys.receipt.service;

import cn.xluobo.business.sys.receipt.domain.req.ReqSearchSysReceiptAccount;
import cn.xluobo.business.sys.receipt.repo.model.SysReceiptAccount;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.api.ApiResEnums;
import cn.xluobo.core.page.RespPage;
import cn.xluobo.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
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
public class BusinessSysReceiptAccountService {

    @Autowired
    private ISysReceiptAccountService sysReceiptAccountService;

    /**
     * 查询
     *
     * @param reqSearchSysReceiptAccount
     * @return
     */
    public APIResponse searchList(ReqSearchSysReceiptAccount reqSearchSysReceiptAccount) {
        QueryWrapper qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(reqSearchSysReceiptAccount.getAccountName())){
            qw.like("account_name",reqSearchSysReceiptAccount.getAccountName());
        }
        if(StringUtils.isNotEmpty(reqSearchSysReceiptAccount.getMemo())){
            qw.eq("memo",reqSearchSysReceiptAccount.getMemo());
        }
        RespPage<SysReceiptAccount> page = new RespPage(reqSearchSysReceiptAccount.getPageNum(), reqSearchSysReceiptAccount.getPageSize());
        RespPage<SysReceiptAccount> listPage = sysReceiptAccountService.page(page, qw);
        return APIResponse.toAPIResponse(listPage);
    }

    /**
     * 前端select
     *
     * @return
     */
    public APIResponse select() {
        List<SysReceiptAccount> receiptAccounts = sysReceiptAccountService.select();
        return APIResponse.toAPIResponse(receiptAccounts);
    }


    /**
     * 详情
     *
     * @param accountId
     * @return
     */
    public APIResponse detailById(Long accountId) {
        if (null == accountId) {
            return APIResponse.toAPIResponse(null);
        }
        SysReceiptAccount detailInfo = sysReceiptAccountService.getById(accountId);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param sysReceiptAccount
     * @return
     */
    public APIResponse addSysReceiptAccount(SysReceiptAccount sysReceiptAccount) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        sysReceiptAccount.setCreateUser(loginUser.getUserId());
        boolean addSysReceiptAccount = sysReceiptAccountService.save(sysReceiptAccount);
        if (addSysReceiptAccount) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param sysReceiptAccount
     * @return
     */
    public APIResponse updateSysReceiptAccount(SysReceiptAccount sysReceiptAccount) {
        if (null == sysReceiptAccount.getAccountId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        sysReceiptAccount.setLastUpdateUser(loginUser.getUserId());
        sysReceiptAccount.setLastUpdateTime(new Date());
        boolean updateSysReceiptAccount = sysReceiptAccountService.updateById(sysReceiptAccount);
        if (updateSysReceiptAccount) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param accountIds
     * @return
     */
    public APIResponse deleteById(Long[] accountIds) {
        if (null == accountIds || accountIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        boolean deleteSysReceiptAccount = sysReceiptAccountService.removeByIds(Arrays.asList(accountIds));
        if (deleteSysReceiptAccount) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }
}
