package com.study.business.sc.student.controller;

import com.study.business.sc.student.domain.req.*;
import com.study.business.sc.student.domain.req.ReqSearchStuCourseSignUp;
import com.study.business.sc.student.domain.req.ReqSearchStudentCourse;
import com.study.business.sc.student.domain.req.ReqSearchStudentCourseCla;
import com.study.business.sc.student.domain.req.ReqStudentCourseChooseCla;
import com.study.business.sc.student.domain.resp.RespCourseClaStudent;
import com.study.business.sc.student.domain.resp.RespStuCourseSignUpStudent;
import com.study.business.sc.student.domain.resp.RespStudentCourse;
import com.study.business.sc.student.service.BusinessScStudentCourseService;
import com.study.core.api.APIResponse;
import com.study.core.page.RespPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生课程
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/2 15:05
 */
@RestController
@RequestMapping("/api/sc/studentCourse")
public class ScStudentCourseController {

    @Autowired
    private BusinessScStudentCourseService studentCourseService;

    /**
     * 学生课程相关信息
     * @param studentId
     * @return 报读课程、校区、联系人信息
     */
    @GetMapping("/info/studentCourseInfo/{studentId}")
    public APIResponse studentCourseInfo(@PathVariable("studentId") Long studentId){
        return studentCourseService.studentCourseInfo(studentId);
    }

    /**
     * 课程、班级 学生列表
     * @param reqSearchStudentCourseCla
     * @return
     */
    @GetMapping("/list/searchCourseClaStudent")
    public APIResponse searchCourseClaStudent(ReqSearchStudentCourseCla reqSearchStudentCourseCla){
        RespPage<RespCourseClaStudent> respPage = studentCourseService.searchCourseClaStudent(reqSearchStudentCourseCla);
        return APIResponse.toAPIResponse(respPage);
    }

    /**
     * 已报名未选择班级 选择班级
     * @return
     */
    @PostMapping("/update/studentCourseChooseCla")
    public APIResponse studentCourseChooseCla(@RequestBody ReqStudentCourseChooseCla studentCourseChooseCla){
        return studentCourseService.studentCourseChooseCla(studentCourseChooseCla);
    }

    /**
     * 记上课
     * @param reqClaTimeAttend
     * @return
     */
    @PostMapping("/update/claTimeAttend")
    public APIResponse claTimeAttend(@RequestBody ReqClaTimeAttend reqClaTimeAttend){
        return studentCourseService.claTimeAttend(reqClaTimeAttend);
    }

    /**
     * 学生停课
     * @param studentCourseId
     * @return
     */
    @PostMapping("/update/stopStudentCourseStatus/{studentCourseId}")
    public APIResponse stopStudentCourseStatus(@PathVariable("studentCourseId") Long studentCourseId) {
        return studentCourseService.stopStudentCourseStatus(studentCourseId);
    }

    /**
     * 变更学生状态为在读
     * @param studentCourseId
     * @return
     */
    @PostMapping("/update/atClaStudentCourseStatus/{studentCourseId}")
    public APIResponse atClaStudentCourseStatus(@PathVariable("studentCourseId") Long studentCourseId) {
        return studentCourseService.atClaStudentCourseStatus(studentCourseId);
    }

    /**
     * 学生报读课程列表
     * @param reqSearchStudentCourse
     * @return
     */
    @GetMapping("/list/searchStudentCourse")
    public APIResponse searchStudentCourse(ReqSearchStudentCourse reqSearchStudentCourse){
        List<RespStudentCourse> respStudentCourses = studentCourseService.searchStudentCourse(reqSearchStudentCourse);
        return APIResponse.toAPIResponse(respStudentCourses);
    }

    /**
     * 报读列表
     * @param reqSearchStuCourseSignUp
     * @return
     */
    @GetMapping("/list/searchStuCourseSignUpList")
    public APIResponse searchStuCourseSignUpList(ReqSearchStuCourseSignUp reqSearchStuCourseSignUp){
        RespPage<RespStuCourseSignUpStudent> respPage = studentCourseService.searchStuCourseSignUpList(reqSearchStuCourseSignUp);
        return APIResponse.toAPIResponse(respPage);
    }

    /**
     * 报读信息中 将学员从班级中移除
     * @param studentCourseId
     * @return
     */
    @PostMapping("/update/removeStuFromCla/{studentCourseId}")
    public APIResponse removeStuFromCla(@PathVariable("studentCourseId") Long studentCourseId) {
        return studentCourseService.removeStuFromCla(studentCourseId);
    }
}
