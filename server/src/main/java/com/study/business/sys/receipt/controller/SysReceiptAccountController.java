package com.study.business.sys.receipt.controller;

import com.study.business.sys.receipt.domain.req.ReqSearchSysReceiptAccount;
import com.study.business.sys.receipt.repo.model.SysReceiptAccount;
import com.study.business.sys.receipt.service.BusinessSysReceiptAccountService;
import com.study.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 收款账户 Controller
 * </p>
 *
 * @author zhangby
 * @since 2020-09-05 05:36:25
 */
@RestController
@RequestMapping("/api/sys/receipt")
public class SysReceiptAccountController {
    @Autowired
    private BusinessSysReceiptAccountService sysReceiptAccountService;

    /**
     * 列表
     *
     * @param reqSearchSysReceiptAccount
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchSysReceiptAccount reqSearchSysReceiptAccount) {
        return sysReceiptAccountService.searchList(reqSearchSysReceiptAccount);
    }

    /**
     * 列表
     *
     * @return
     */
    @GetMapping("/list/select")
    public APIResponse select() {
        return sysReceiptAccountService.select();
    }

    /**
     * 详情
     *
     * @param accountId
     * @return
     */
    @GetMapping("/info/detailById/{accountId}")
    public APIResponse detailById(@PathVariable("accountId") Long accountId) {
        return sysReceiptAccountService.detailById(accountId);
    }

    /**
     * 添加
     *
     * @param sysReceiptAccount
     * @return
     */
    @PostMapping("/add/addSysReceiptAccount")
    public APIResponse addSysReceiptAccount(@RequestBody SysReceiptAccount sysReceiptAccount) {
        return sysReceiptAccountService.addSysReceiptAccount(sysReceiptAccount);
    }

    /**
     * 修改
     *
     * @param sysReceiptAccount
     * @return
     */
    @PutMapping("/update/updateSysReceiptAccount")
    public APIResponse updateSysReceiptAccount(@RequestBody SysReceiptAccount sysReceiptAccount) {
        return sysReceiptAccountService.updateSysReceiptAccount(sysReceiptAccount);
    }

    /**
     * 删除
     *
     * @param accountIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{accountIds}")
    public APIResponse deleteById(@PathVariable("accountIds") Long[] accountIds) {
        return sysReceiptAccountService.deleteById(accountIds);
    }
}
