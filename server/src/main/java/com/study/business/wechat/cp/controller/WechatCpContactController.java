package com.study.business.wechat.cp.controller;

import com.study.business.wechat.cp.domain.req.ReqSearchWechatCpContact;
import com.study.business.wechat.cp.repo.model.WechatCpContact;
import com.study.business.wechat.cp.service.BusinessWechatCpContactService;
import com.study.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 企业微信客户信息 Controller
 * </p>
 *
 * @author xluobo
 * @since 2024-01-25 05:53:20
 */
@RestController
@RequestMapping("/api/wechat/cp/contact")
public class WechatCpContactController {
    @Autowired
    private BusinessWechatCpContactService wechatCpContactService;

    /**
     * 列表
     *
     * @param reqSearchWechatCpContact
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchWechatCpContact reqSearchWechatCpContact) {
        return wechatCpContactService.searchList(reqSearchWechatCpContact);
    }

    /**
     * 详情
     *
     * @param externalUserId
     * @return
     */
    @GetMapping("/info/detailById/{externalUserId}")
    public APIResponse detailById(@PathVariable("externalUserId") String externalUserId) {
        return wechatCpContactService.detailById(externalUserId);
    }

    /**
     * 添加
     *
     * @param wechatCpContact
     * @return
     */
    @PostMapping("/add")
    public APIResponse addWechatCpContact(@RequestBody WechatCpContact wechatCpContact) {
        return wechatCpContactService.addWechatCpContact(wechatCpContact);
    }

    /**
     * 修改
     *
     * @param wechatCpContact
     * @return
     */
    @PutMapping("/update")
    public APIResponse updateWechatCpContact(@RequestBody WechatCpContact wechatCpContact) {
        return wechatCpContactService.updateWechatCpContact(wechatCpContact);
    }

    /**
     * 删除
     *
     * @param externalUserIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{externalUserIds}")
    public APIResponse deleteById(@PathVariable("externalUserIds") String[] externalUserIds) {
        return wechatCpContactService.deleteById(externalUserIds);
    }
}
