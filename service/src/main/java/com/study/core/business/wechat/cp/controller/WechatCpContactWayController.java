package com.study.core.business.wechat.cp.controller;

import cn.xluobo.business.wechat.cp.domain.req.ReqSearchWechatCpContactWay;
import cn.xluobo.business.wechat.cp.repo.model.WechatCpContactWay;
import cn.xluobo.business.wechat.cp.service.BusinessWechatCpContactWayService;
import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 客户联系我方式 Controller
 * </p>
 *
 * @author xluobo
 * @since 2024-01-26 07:13:01
 */
@RestController
@RequestMapping("/api/wechat/cp/contactWay")
public class WechatCpContactWayController {
    @Autowired
    private BusinessWechatCpContactWayService wechatCpContactWayService;

    /**
     * 列表
     *
     * @param reqSearchWechatCpContactWay
     * @return
     */
     @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchWechatCpContactWay reqSearchWechatCpContactWay) {
        return wechatCpContactWayService.searchList(reqSearchWechatCpContactWay);
    }

    /**
     * 详情
     *
     * @param configId
     * @return
     */
     @GetMapping("/info/detailById/{configId}")
    public APIResponse detailById(@PathVariable("configId") String configId) {
        return wechatCpContactWayService.detailById(configId);
    }

    /**
     * 添加
     *
     * @param wechatCpContactWay
     * @return
     */
     @PostMapping("/add")
    public APIResponse addWechatCpContactWay(@RequestBody WechatCpContactWay wechatCpContactWay) {
        return wechatCpContactWayService.addWechatCpContactWay(wechatCpContactWay);
    }

    /**
     * 修改
     *
     * @param wechatCpContactWay
     * @return
     */
     @PutMapping("/update")
    public APIResponse updateWechatCpContactWay(@RequestBody WechatCpContactWay wechatCpContactWay) {
        return wechatCpContactWayService.updateWechatCpContactWay(wechatCpContactWay);
    }

    /**
     * 删除
     *
     * @param configIds
     * @return
     */
     @DeleteMapping("/delete/deleteById/{configIds}")
    public APIResponse deleteById(@PathVariable("configIds") String[] configIds) {
        return wechatCpContactWayService.deleteById(configIds);
    }
}
