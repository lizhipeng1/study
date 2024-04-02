package com.study.core.business.sc.course.controller;

import cn.xluobo.business.sc.course.domain.req.time.ReqSearchScClaTimeAttend;
import cn.xluobo.business.sc.course.domain.resp.time.RespClaTimeAttend;
import cn.xluobo.business.sc.course.repo.model.ScClaTimeAttend;
import cn.xluobo.business.sc.course.service.BusinessScClaTimeAttendService;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.page.RespPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 上课出勤表 Controller
 * </p>
 *
 * @author zhangby
 * @since 2020-09-30 02:33:26
 */
@RestController
@RequestMapping("/api/sc/cla/time/attend")
public class ScClaTimeAttendController {
    @Autowired
    private BusinessScClaTimeAttendService scClaTimeAttendService;

    /**
     * 列表
     *
     * @param reqSearchScClaTimeAttend
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchScClaTimeAttend reqSearchScClaTimeAttend) {
        RespPage<RespClaTimeAttend> respPage = scClaTimeAttendService.searchList(reqSearchScClaTimeAttend);
        return APIResponse.toAPIResponse(respPage);
    }

    /**
     * 上课 出席详情
     * @param courseTimeId
     * @return
     */
    @GetMapping("/info/hadClaTimeAttendDetail/{courseTimeId}")
    public APIResponse hadClaTimeAttendDetail(@PathVariable("courseTimeId") Long courseTimeId) {
        return scClaTimeAttendService.hadClaTimeAttendDetail(courseTimeId);
    }

    /**
     * 详情
     *
     * @param attendId
     * @return
     */
//    @GetMapping("/info/detailById/{attendId}")
    public APIResponse detailById(@PathVariable("attendId") Long attendId) {
        return scClaTimeAttendService.detailById(attendId);
    }

    /**
     * 添加
     *
     * @param scClaTimeAttend
     * @return
     */
//    @PostMapping("/add/addScClaTimeAttend")
    public APIResponse addScClaTimeAttend(@RequestBody ScClaTimeAttend scClaTimeAttend) {
        return scClaTimeAttendService.addScClaTimeAttend(scClaTimeAttend);
    }

    /**
     * 修改
     *
     * @param scClaTimeAttend
     * @return
     */
//    @PutMapping("/update/updateScClaTimeAttend")
    public APIResponse updateScClaTimeAttend(@RequestBody ScClaTimeAttend scClaTimeAttend) {
        return scClaTimeAttendService.updateScClaTimeAttend(scClaTimeAttend);
    }

    /**
     * 删除
     *
     * @param attendIds
     * @return
     */
//    @DeleteMapping("/delete/deleteById/{attendIds}")
    public APIResponse deleteById(@PathVariable("attendIds") Long[] attendIds) {
        return scClaTimeAttendService.deleteById(attendIds);
    }
}
