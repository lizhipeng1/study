package com.study.business.sc.student.controller;

import com.study.business.sc.student.domain.req.ReqSearchScStudent;
import com.study.business.sc.student.domain.req.ReqStudentSelect;
import com.study.business.sc.student.repo.model.ScStudent;
import com.study.business.sc.student.service.BusinessScStudentService;
import com.study.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 学生基本信息 Controller
 * </p>
 *
 * @author zhangby
 * @since 2020-04-27 07:13:40
 */
@RestController
@RequestMapping("/api/sc/student")
public class ScStudentController {
    @Autowired
    private BusinessScStudentService scStudentService;

    /**
     * 列表
     *
     * @param reqSearchScStudent
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchScStudent reqSearchScStudent) {
        return scStudentService.searchList(reqSearchScStudent);
    }

    /**
     * 前端select
     *
     * @return
     */
    @GetMapping("/list/select")
    public APIResponse select(ReqStudentSelect studentSelect) {
        return scStudentService.select(studentSelect);
    }

    /**
     * 详情
     *
     * @param studentId
     * @return
     */
    @GetMapping("/info/detailById/{studentId}")
    public APIResponse detailById(@PathVariable("studentId") Long studentId) {
        return scStudentService.detailById(studentId);
    }

    /**
     * 添加
     *
     * @param scStudent
     * @return
     */
    @PostMapping("/add/addScStudent")
    public APIResponse addScStudent(@RequestBody ScStudent scStudent) {
        return scStudentService.addScStudent(scStudent);
    }

    /**
     * 修改
     *
     * @param scStudent
     * @return
     */
    @PutMapping("/update/updateScStudent")
    public APIResponse updateScStudent(@RequestBody ScStudent scStudent) {
        return scStudentService.updateScStudent(scStudent);
    }

    /**
     * 删除
     *
     * @param studentIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{studentIds}")
    public APIResponse deleteById(@PathVariable("studentIds") Long[] studentIds) {
        return scStudentService.deleteById(studentIds);
    }
}
