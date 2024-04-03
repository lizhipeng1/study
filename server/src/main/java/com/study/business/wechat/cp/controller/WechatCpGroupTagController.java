package com.study.business.wechat.cp.controller;

import com.study.business.wechat.cp.domain.req.ReqSearchWechatCpGroupTag;
import com.study.business.wechat.cp.repo.model.WechatCpGroupTag;
import com.study.business.wechat.cp.service.BusinessWechatCpGroupTagService;
import com.study.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 企业微信标签 Controller
 * </p>
 *
 * @author xluobo
 * @since 2024-01-25 05:55:40
 */
@RestController
@RequestMapping("/api/wechat/cp/tag")
public class WechatCpGroupTagController {
    @Autowired
    private BusinessWechatCpGroupTagService wechatCpGroupTagService;

    /**
     * 列表
     *
     * @param reqSearchWechatCpGroupTag
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchWechatCpGroupTag reqSearchWechatCpGroupTag) {
        return wechatCpGroupTagService.searchList(reqSearchWechatCpGroupTag);
    }

    /**
     * group select
     *
     * @return
     */
    @GetMapping("/list/groupSelect")
    public APIResponse groupSelect() {
        return wechatCpGroupTagService.groupSelect();
    }

    /**
     * 详情
     *
     * @param tagId
     * @return
     */
    @GetMapping("/info/detailById/{tagId}")
    public APIResponse detailById(@PathVariable("tagId") String tagId) {
        return wechatCpGroupTagService.detailById(tagId);
    }

    /**
     * 添加
     *
     * @param wechatCpGroupTag
     * @return
     */
    @PostMapping("/add")
    public APIResponse addWechatCpGroupTag(@RequestBody WechatCpGroupTag wechatCpGroupTag) {
        return wechatCpGroupTagService.addWechatCpGroupTag(wechatCpGroupTag);
    }

    /**
     * 修改
     *
     * @param wechatCpGroupTag
     * @return
     */
    @PutMapping("/update")
    public APIResponse updateWechatCpGroupTag(@RequestBody WechatCpGroupTag wechatCpGroupTag) {
        return wechatCpGroupTagService.updateWechatCpGroupTag(wechatCpGroupTag);
    }

    /**
     * 删除
     *
     * @param tagIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{tagIds}")
    public APIResponse deleteById(@PathVariable("tagIds") String[] tagIds) {
        return wechatCpGroupTagService.deleteById(tagIds);
    }
}
