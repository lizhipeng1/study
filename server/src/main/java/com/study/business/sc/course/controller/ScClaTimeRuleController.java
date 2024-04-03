package com.study.business.sc.course.controller;

import com.study.business.sc.course.domain.req.time.ReqSearchScClaTimeRule;
import com.study.business.sc.course.repo.model.ScClaTimeRule;
import com.study.business.sc.course.service.BusinessClaTimeRuleService;
import com.study.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 上课时间配置规则 Controller
 * </p>
 *
 * @author zhangby
 * @since 2020-09-18 04:18:54
 */
@RestController
@RequestMapping("/api/sc/cla/time/rule")
public class ScClaTimeRuleController {

    @Autowired
    private BusinessClaTimeRuleService scClaTimeRuleService;

    /**
     * 列表
     *
     * @param reqSearchScClaTimeRule
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchScClaTimeRule reqSearchScClaTimeRule) {
        return scClaTimeRuleService.searchList(reqSearchScClaTimeRule);
    }

    /**
     * 详情
     *
     * @param ruleId
     * @return
     */
    @GetMapping("/info/detailById/{ruleId}")
    public APIResponse detailById(@PathVariable("ruleId") Long ruleId) {
        return scClaTimeRuleService.detailById(ruleId);
    }

    /**
     * 添加
     *
     * @param scClaTimeRule
     * @return
     */
    @PostMapping("/add/addScClaTimeRule")
    public APIResponse addScClaTimeRule(@RequestBody ScClaTimeRule scClaTimeRule) {
        return scClaTimeRuleService.addClaTimeRule(scClaTimeRule);
    }

    /**
     * 修改
     *
     * @param scClaTimeRule
     * @return
     */
    @PutMapping("/update/updateScClaTimeRule")
    public APIResponse updateScClaTimeRule(@RequestBody ScClaTimeRule scClaTimeRule) {
        return scClaTimeRuleService.updateClaTimeRule(scClaTimeRule);
    }

    /**
     * 删除
     *
     * @param ruleId
     * @return
     */
    @DeleteMapping("/delete/deleteById/{ruleId}")
    public APIResponse deleteById(@PathVariable("ruleId") Long ruleId) {
        return scClaTimeRuleService.deleteById(ruleId);
    }
}
