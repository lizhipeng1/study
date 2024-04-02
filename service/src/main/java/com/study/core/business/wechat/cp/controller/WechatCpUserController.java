package com.study.core.business.wechat.cp.controller;

import cn.xluobo.business.sc.course.domain.req.ReqSelect;
import cn.xluobo.business.wechat.cp.service.BusinessWechatCpUserService;
import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 企业微信通讯录
 * @projectName: qyxt
 * @package: cn.xluobo.business.wechat.cp.controller
 * @className: WechatCpUserController
 * @author: xluobo
 * @description: TODO
 * @date: 2024/1/30 00:29
 */
@RestController
@RequestMapping("/api/wechat/cp/user")
public class WechatCpUserController {

    @Autowired
    private BusinessWechatCpUserService businessWechatCpUserService;

    /**
     * 前端select
     *
     * @return
     */
    @GetMapping("/list/userSelect")
    public APIResponse cpUserSelect(ReqSelect reqSelect) {
        return businessWechatCpUserService.cpUserSelect(reqSelect);
    }
}
