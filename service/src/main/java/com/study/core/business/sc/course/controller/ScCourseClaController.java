package com.study.core.business.sc.course.controller;

import cn.xluobo.business.sc.course.domain.req.ReqAddScCourseCla;
import cn.xluobo.business.sc.course.domain.req.ReqSearchScCourseCla;
import cn.xluobo.business.sc.course.domain.resp.cla.RespClaAllDetailInfo;
import cn.xluobo.business.sc.course.repo.model.ScCourseCla;
import cn.xluobo.business.sc.course.service.BusinessScCourseClaService;
import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程班级信息 Controller
 * </p>
 *
 * @author zhangby
 * @since 2020-03-17 01:11:06
 */
@RestController
@RequestMapping("/api/sc/course/cla")
public class ScCourseClaController {
    @Autowired
    private BusinessScCourseClaService scCourseClaService;

    /**
     * 列表
     *
     * @param reqSearchScCourseCla
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchScCourseCla reqSearchScCourseCla) {
        return scCourseClaService.searchList(reqSearchScCourseCla);
    }

    /**
     * 详情
     *
     * @param claId
     * @return
     */
    @GetMapping("/info/detailById/{claId}")
    public APIResponse detailById(@PathVariable("claId") Long claId) {
        return scCourseClaService.detailById(claId);
    }

    /**
     * 详情
     * 包含内容较多
     *
     * @param claId
     * @return
     */
    @GetMapping("/info/allDetailInfoById/{claId}")
    public APIResponse allDetailInfoById(@PathVariable("claId") Long claId) {
        RespClaAllDetailInfo allDetailInfo = scCourseClaService.allDetailInfoById(claId);
        return APIResponse.toAPIResponse(allDetailInfo);
    }

    /**
     * 添加
     *
     * @param scCourseCla
     * @return
     */
    @PostMapping("/add/addScCourseCla")
    public APIResponse addScCourseCla(@RequestBody ReqAddScCourseCla scCourseCla) {
        return scCourseClaService.addScCourseCla(scCourseCla);
    }

    /**
     * 修改
     *
     * @param scCourseCla
     * @return
     */
    @PutMapping("/update/updateScCourseCla")
    public APIResponse updateScCourseCla(@RequestBody ScCourseCla scCourseCla) {
        return scCourseClaService.updateScCourseCla(scCourseCla);
    }

    /**
     * 删除
     *
     * @param claIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{claIds}")
    public APIResponse deleteById(@PathVariable("claIds") Long[] claIds) {
        return scCourseClaService.deleteById(claIds);
    }

}
