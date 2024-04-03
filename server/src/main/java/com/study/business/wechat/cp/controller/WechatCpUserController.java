package com.study.business.wechat.cp.controller;

import com.study.business.sc.course.domain.req.ReqSelect;
import com.study.business.wechat.cp.service.BusinessWechatCpUserService;
import com.study.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 企业微信通讯录
 * @projectName: qyxt
 * @package: com.study.business.wechat.cp.controller
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
