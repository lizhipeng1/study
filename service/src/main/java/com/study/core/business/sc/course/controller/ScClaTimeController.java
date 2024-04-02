package com.study.core.business.sc.course.controller;

import cn.xluobo.business.sc.course.domain.req.time.ReqSearchClaTime;
import cn.xluobo.business.sc.course.domain.resp.time.RespBusinessClaTimeCalendar;
import cn.xluobo.business.sc.course.domain.resp.time.RespClaTime;
import cn.xluobo.business.sc.course.domain.resp.time.RespClaTimeCalendar;
import cn.xluobo.business.sc.course.repo.model.ScClaTime;
import cn.xluobo.business.sc.course.service.BusinessClaTimeService;
import cn.xluobo.business.sc.student.domain.req.ReqClaTimeAttend;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.page.RespPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/18 14:40
 */
@RestController
@RequestMapping("/api/sc/cla/time")
public class ScClaTimeController {

    @Autowired
    private BusinessClaTimeService claTimeService;

    /**
     * 按周获取课表
     *
     * @param reqSearchClaTime
     * @return 周课表 {@link RespBusinessClaTimeCalendar}
     */
    @GetMapping("/list/searchListForCalendar")
    public APIResponse searchListForCalendar(ReqSearchClaTime reqSearchClaTime) {
        return claTimeService.searchListForCalendar(reqSearchClaTime);
    }

    /**
     * 获取最近几天的排课日程
     *
     * @param reqSearchClaTime
     * @return
     */
    @GetMapping("/list/searchRecentDayTimeList")
    public APIResponse searchRecentDayTimeList(ReqSearchClaTime reqSearchClaTime) {
        List<RespClaTimeCalendar> respClaTimeList = claTimeService.searchRecentDayTimeList(reqSearchClaTime);
        return APIResponse.toAPIResponse(respClaTimeList);
    }

    /**
     * 获取上课记录
     *
     * @param reqSearchClaTime
     * @return
     */
    @GetMapping("/list/selectListForAttend")
    public APIResponse selectListForAttend(ReqSearchClaTime reqSearchClaTime) {
        RespPage<RespClaTime> respPage = claTimeService.selectListForAttend(reqSearchClaTime);
        return APIResponse.toAPIResponse(respPage);
    }

    /**
     * 添加
     *
     * @param scClaTime
     * @return
     */
    @PostMapping("/add/addScClaTime")
    public APIResponse addScClaTime(@RequestBody ScClaTime scClaTime) {
        return claTimeService.addClaTime(scClaTime);
    }

    /**
     * 详情
     *
     * @param courseTimeId
     * @return
     */
    @GetMapping("/info/detailById/{courseTimeId}")
    public APIResponse detailById(@PathVariable("courseTimeId") Long courseTimeId) {
        return claTimeService.detailById(courseTimeId);
    }

    /**
     * 修改
     *
     * @param scClaTime
     * @return
     */
    @PutMapping("/update/updateScClaTime")
    public APIResponse updateScClaTime(@RequestBody ScClaTime scClaTime) {
        return claTimeService.updateClaTime(scClaTime);
    }

    /**
     * 删除
     *
     * @param courseTimeId
     * @return
     */
    @DeleteMapping("/delete/deleteById/{courseTimeId}")
    public APIResponse deleteById(@PathVariable("courseTimeId") Long courseTimeId) {
        return claTimeService.deleteById(courseTimeId);
    }

    /**
     * 变更 已记上课 信息
     * @param reqClaTimeAttend
     * @return
     */
    @PostMapping("/update/changeHadClaTimeAttend")
    public APIResponse changeHadClaTimeAttend(@RequestBody ReqClaTimeAttend reqClaTimeAttend) {
        return claTimeService.changeHadClaTimeAttend(reqClaTimeAttend);
    }

    /**
     * 删除 已记上课 信息
     * @return
     */
    @DeleteMapping("/delete/deleteHadClaTimeAttend/{courseTimeId}")
    public APIResponse deleteHadClaTimeAttend(@PathVariable("courseTimeId") Long courseTimeId) {
        return claTimeService.deleteHadClaTimeAttend(courseTimeId);
    }
}
