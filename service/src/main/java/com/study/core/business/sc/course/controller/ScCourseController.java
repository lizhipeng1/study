package com.study.core.business.sc.course.controller;

import cn.xluobo.business.sc.course.domain.req.ReqBusinessOrderCourseDetail;
import cn.xluobo.business.sc.course.domain.req.ReqSearchScCourse;
import cn.xluobo.business.sc.course.domain.req.ReqSelect;
import cn.xluobo.business.sc.course.domain.req.course.ReqAddScCourse;
import cn.xluobo.business.sc.course.domain.req.course.ReqChangeScCourse;
import cn.xluobo.business.sc.course.repo.model.ScCourse;
import cn.xluobo.business.sc.course.service.BusinessScCourseService;
import cn.xluobo.business.sc.course.service.IScCourseService;
import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程信息 Controller
 * </p>
 *
 * @author zhangby
 * @since 2020-03-17 11:25:55
 */
@RestController
@RequestMapping("/api/sc/course")
public class ScCourseController {

    @Autowired
    private BusinessScCourseService scCourseService;

    @Autowired
    private IScCourseService courseService;

    /**
     * 列表
     *
     * @param reqSearchScCourse
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchScCourse reqSearchScCourse) {
        return scCourseService.searchList(reqSearchScCourse);
    }

    /**
     * 查询课程列表
     * 含有学生报读状态
     *
     * @param reqSearchScCourse
     * @return
     */
    @GetMapping("/list/selectCourseListWithStudentCourse")
    public APIResponse selectCourseListWithStudentCourse(ReqSearchScCourse reqSearchScCourse) {
        return scCourseService.selectCourseListWithStudentCourse(reqSearchScCourse);
    }

    /**
     * 前端select
     *
     * @return
     */
    @GetMapping("/list/select")
    public APIResponse select(ReqSelect reqSelect) {
        return scCourseService.select(reqSelect);
    }

    /**
     * 详情
     *
     * @param courseId
     * @return
     */
    @GetMapping("/info/detailById/{courseId}")
    public APIResponse detailById(@PathVariable("courseId") Long courseId) {
        return scCourseService.detailById(courseId);
    }

    /**
     * 添加
     *
     * @param reqAddScCourse
     * @return
     */
    @PostMapping("/add/addScCourse")
    public APIResponse addScCourse(@RequestBody ReqAddScCourse reqAddScCourse) {
        return scCourseService.addScCourse(reqAddScCourse);
    }

    /**
     * 修改
     *
     * @param reqChangeScCourse
     * @return
     */
    @PutMapping("/update/updateScCourse")
    public APIResponse updateScCourse(@RequestBody ReqChangeScCourse reqChangeScCourse) {
        return scCourseService.updateScCourse(reqChangeScCourse);
    }

    /**
     * 删除
     *
     * @param courseIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{courseIds}")
    public APIResponse deleteById(@PathVariable("courseIds") Long[] courseIds) {
        return scCourseService.deleteById(courseIds);
    }

    /**
     * 变更是否开售
     *
     * @param scCourse
     * @return
     */
    @PutMapping("/update/changeCourseSale")
    public APIResponse changeCourseSale(@RequestBody ScCourse scCourse) {
        return scCourseService.changeCourseSale(scCourse);
    }

    /**
     * 导出
     *
     * @param reqSearchScCourse
     */
    @GetMapping("/export/exportCourse")
    public APIResponse exportCourse(ReqSearchScCourse reqSearchScCourse) {
        return scCourseService.exportCourse(reqSearchScCourse);
    }

    /**
     * 课程详情
     * 报名选择课程后获取课程详情
     *
     * @param orderCourseDetail
     * @return
     */
    @GetMapping("/info/orderCourseDetail")
    public APIResponse orderCourseDetail(ReqBusinessOrderCourseDetail orderCourseDetail) {
        return scCourseService.orderCourseDetail(orderCourseDetail);
    }

    /**
     * 学生是否可报读课程
     *
     * @param orderCourseDetail
     * @return
     */
    @GetMapping("/info/studentCanSignUpCourse")
    public APIResponse studentCanSignUpCourse(ReqBusinessOrderCourseDetail orderCourseDetail) {
        return scCourseService.studentCanSignUpCourse(orderCourseDetail);
    }
}
