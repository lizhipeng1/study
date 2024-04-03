package com.study.business.monitor.online.controller;

import com.study.business.monitor.online.service.BusinessOnlineUserService;
import com.study.business.sys.admin.domain.req.ReqSearchOnlineUser;
import com.study.core.api.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 在线用户
 * @author ：zhangbaoyu
 * @date ：Created in 2020-03-03 09:18
 */
@RestController
@RequestMapping("/api/monitor/online")
@Slf4j
public class OnlineUserController {

    @Autowired
    private BusinessOnlineUserService businessOnlineUserService;

    /**
     * 列表
     *
     * @param reqSearchOnlineUser
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchOnlineUser reqSearchOnlineUser) {
        return businessOnlineUserService.searchList(reqSearchOnlineUser);
    }

    /**
     * 列表
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/force/offline/{userId}/{jti}")
    public APIResponse forceOffline(@PathVariable("userId") String userId, @PathVariable("jti") String jti) {
        return businessOnlineUserService.forceOffline(userId,jti);
    }

}
