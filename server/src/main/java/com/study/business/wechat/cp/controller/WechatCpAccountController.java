package com.study.business.wechat.cp.controller;

import com.study.business.wechat.cp.domain.req.ReqSearchWechatCpAccount;
import com.study.business.wechat.cp.repo.model.WechatCpAccount;
import com.study.business.wechat.cp.service.BusinessWechatCpAccountService;
import com.study.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 企业应用信息 Controller
 * </p>
 *
 * @author zhangby
 * @since 2024-01-23 06:59:58
 */
@RestController
@RequestMapping("/api/wechat/cp/account")
public class WechatCpAccountController {
    @Autowired
    private BusinessWechatCpAccountService wechatCpAccountService;

    /**
     * 列表
     *
     * @param reqSearchWechatCpAccount
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchWechatCpAccount reqSearchWechatCpAccount) {
        return wechatCpAccountService.searchList(reqSearchWechatCpAccount);
    }

    /**
     * 详情
     *
     * @param cpAccountId
     * @return
     */
    @GetMapping("/info/detailById/{cpAccountId}")
    public APIResponse detailById(@PathVariable("cpAccountId") String cpAccountId) {
        return wechatCpAccountService.detailById(cpAccountId);
    }

    /**
     * 添加
     *
     * @param wechatCpAccount
     * @return
     */
    @PostMapping("/add")
    public APIResponse addWechatCpAccount(@RequestBody WechatCpAccount wechatCpAccount) {
        return wechatCpAccountService.addWechatCpAccount(wechatCpAccount);
    }

    /**
     * 修改
     *
     * @param wechatCpAccount
     * @return
     */
    @PutMapping("/update")
    public APIResponse updateWechatCpAccount(@RequestBody WechatCpAccount wechatCpAccount) {
        return wechatCpAccountService.updateWechatCpAccount(wechatCpAccount);
    }

    /**
     * 删除
     *
     * @param cpAccountIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{cpAccountIds}")
    public APIResponse deleteById(@PathVariable("cpAccountIds") String[] cpAccountIds) {
        return wechatCpAccountService.deleteById(cpAccountIds);
    }
}
