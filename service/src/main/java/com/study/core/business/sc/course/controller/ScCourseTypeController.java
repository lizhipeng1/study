package com.study.core.business.sc.course.controller;

import cn.xluobo.business.sc.course.domain.req.ReqSearchScCourseType;
import cn.xluobo.business.sc.course.repo.model.ScCourseType;
import cn.xluobo.business.sc.course.service.BusinessScCourseTypeService;
import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程类型 Controller
 * </p>
 *
 * @author zhangby
 * @since 2020-07-09 08:10:25
 */
@RestController
@RequestMapping("/api/sc/course/type")
public class ScCourseTypeController {
    @Autowired
    private BusinessScCourseTypeService scCourseTypeService;

    /**
     * 列表
     *
     * @param reqSearchScCourseType
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchScCourseType reqSearchScCourseType) {
        return scCourseTypeService.searchList(reqSearchScCourseType);
    }

    /**
     * select
     *
     * @param reqSearchScCourseType
     * @return
     */
    @GetMapping("/list/select")
    public APIResponse select(ReqSearchScCourseType reqSearchScCourseType) {
        return scCourseTypeService.select(reqSearchScCourseType);
    }

    /**
     * 详情
     *
     * @param courseTypeId
     * @return
     */
    @GetMapping("/info/detailById/{courseTypeId}")
    public APIResponse detailById(@PathVariable("courseTypeId") Long courseTypeId) {
        return scCourseTypeService.detailById(courseTypeId);
    }

    /**
     * 添加
     *
     * @param scCourseType
     * @return
     */
    @PostMapping("/add/addScCourseType")
    public APIResponse addScCourseType(@RequestBody ScCourseType scCourseType) {
        return scCourseTypeService.addScCourseType(scCourseType);
    }

    /**
     * 修改
     *
     * @param scCourseType
     * @return
     */
    @PutMapping("/update/updateScCourseType")
    public APIResponse updateScCourseType(@RequestBody ScCourseType scCourseType) {
        return scCourseTypeService.updateScCourseType(scCourseType);
    }

    /**
     * 删除
     *
     * @param courseTypeIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{courseTypeIds}")
    public APIResponse deleteById(@PathVariable("courseTypeIds") Long[] courseTypeIds) {
        return scCourseTypeService.deleteById(courseTypeIds);
    }
}
