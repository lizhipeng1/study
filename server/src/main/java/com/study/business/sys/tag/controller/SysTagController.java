package com.study.business.sys.tag.controller;

import com.study.business.sys.tag.domain.req.ReqSearchSysTag;
import com.study.business.sys.tag.repo.model.SysTag;
import com.study.business.sys.tag.service.BusinessSysTagService;
import com.study.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 标签 Controller
 * </p>
 *
 * @author zhangby
 * @since 2020-09-03 03:08:13
 */
@RestController
@RequestMapping("/api/sys/tag")
public class SysTagController {
    @Autowired
    private BusinessSysTagService sysTagService;

    /**
     * 列表
     *
     * @param reqSearchSysTag
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchSysTag reqSearchSysTag) {
        return sysTagService.searchList(reqSearchSysTag);
    }

    /**
     * 详情
     *
     * @param tagId
     * @return
     */
    @GetMapping("/info/detailById/{tagId}")
    public APIResponse detailById(@PathVariable("tagId") Long tagId) {
        return sysTagService.detailById(tagId);
    }

    /**
     * 添加
     *
     * @param sysTag
     * @return
     */
    @PostMapping("/add/addSysTag")
    public APIResponse addSysTag(@RequestBody SysTag sysTag) {
        return sysTagService.addSysTag(sysTag);
    }

    /**
     * 修改
     *
     * @param sysTag
     * @return
     */
    @PutMapping("/update/updateSysTag")
    public APIResponse updateSysTag(@RequestBody SysTag sysTag) {
        return sysTagService.updateSysTag(sysTag);
    }

    /**
     * 删除
     *
     * @param tagIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{tagIds}")
    public APIResponse deleteById(@PathVariable("tagIds") Long[] tagIds) {
        return sysTagService.deleteById(tagIds);
    }
}
