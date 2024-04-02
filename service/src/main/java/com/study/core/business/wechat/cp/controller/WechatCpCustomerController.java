package com.study.core.business.wechat.cp.controller;

import cn.xluobo.business.wechat.cp.domain.req.ReqSearchCpCustomer;
import cn.xluobo.business.wechat.cp.service.BusinessWechatCpCustomerService;
import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: qyxt
 * @package: cn.xluobo.business.wechat.cp.controller
 * @className: WechatCpCustomerController
 * @author: xluobo
 * @description: TODO
 * @date: 2024/1/25 08:13
 */
@RestController
@RequestMapping("/api/wechat/cp/customer")
public class WechatCpCustomerController {

    @Autowired
    private BusinessWechatCpCustomerService businessWechatCpCustomerService;

    @GetMapping("/list/searchList")
    /**
     * 客户列表
     *
     * @param reqSearchWechatCpAccount
     * @return
     */
    public APIResponse searchList(ReqSearchCpCustomer reqSearchCpCustomer) {
        return businessWechatCpCustomerService.searchList(reqSearchCpCustomer);
    }

}
