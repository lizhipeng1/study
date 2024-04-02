package com.study.core.business.sc.base.controller;

import cn.xluobo.business.sc.base.domain.req.ReqSchoolSelect;
import cn.xluobo.business.sc.base.domain.req.ReqSearchScSchool;
import cn.xluobo.business.sc.base.repo.model.ScSchool;
import cn.xluobo.business.sc.base.service.BusinessScSchoolService;
import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 学校信息 Controller
 * </p>
 *
 * @author zhangby
 * @since 2020-04-27 07:13:36
 */
@RestController
@RequestMapping("/api/sc/school")
public class ScSchoolController {
    @Autowired
    private BusinessScSchoolService scSchoolService;

    /**
     * 列表
     *
     * @param reqSearchScSchool
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchScSchool reqSearchScSchool) {
        return scSchoolService.searchList(reqSearchScSchool);
    }

    /**
     * 前端select
     *
     * @return
     */
    @GetMapping("/list/select")
    public APIResponse select(ReqSchoolSelect schoolSelect) {
        return scSchoolService.select(schoolSelect);
    }

    /**
     * 详情
     *
     * @param schoolId
     * @return
     */
    @GetMapping("/info/detailById/{schoolId}")
    public APIResponse detailById(@PathVariable("schoolId") Long schoolId) {
        return scSchoolService.detailById(schoolId);
    }

    /**
     * 添加
     *
     * @param scSchool
     * @return
     */
    @PostMapping("/add/addScSchool")
    public APIResponse addScSchool(@RequestBody ScSchool scSchool) {
        return scSchoolService.addScSchool(scSchool);
    }

    /**
     * 修改
     *
     * @param scSchool
     * @return
     */
    @PutMapping("/update/updateScSchool")
    public APIResponse updateScSchool(@RequestBody ScSchool scSchool) {
        return scSchoolService.updateScSchool(scSchool);
    }

    /**
     * 删除
     *
     * @param schoolIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{schoolIds}")
    public APIResponse deleteById(@PathVariable("schoolIds") Long[] schoolIds) {
        return scSchoolService.deleteById(schoolIds);
    }
}
