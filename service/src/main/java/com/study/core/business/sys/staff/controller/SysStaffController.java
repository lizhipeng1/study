package com.study.core.business.sys.staff.controller;

import cn.xluobo.business.sc.course.domain.req.ReqSelect;
import cn.xluobo.business.sys.staff.domain.req.ReqBusinessAddStaff;
import cn.xluobo.business.sys.staff.domain.req.ReqSearchStaff;
import cn.xluobo.business.sys.staff.service.BusinessSysStaffService;
import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 教师信息 Controller
 * </p>
 *
 * @author zhangby
 * @since 2020-03-17 11:27:37
 */
@RestController
@RequestMapping("/api/sys/staff")
public class SysStaffController {
    @Autowired
    private BusinessSysStaffService sysStaffService;

    /**
     * 列表
     *
     * @param reqSearchSysStaff
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchStaff reqSearchSysStaff) {
        return sysStaffService.searchList(reqSearchSysStaff);
    }

    /**
     * 前端select
     *
     * @return
     */
    @GetMapping("/list/teacherSelect")
    public APIResponse teacherSelect(ReqSelect reqSelect) {
        return sysStaffService.teacherSelect(reqSelect);
    }

    /**
     * 详情
     *
     * @param staffId
     * @return
     */
    @GetMapping("/info/detailById/{staffId}")
    public APIResponse detailById(@PathVariable("staffId") Long staffId) {
        return sysStaffService.detailById(staffId);
    }

    /**
     * 添加
     *
     * @param reqBusinessAddStaff
     * @return
     */
    @PostMapping("/add/addSysStaff")
    public APIResponse addSysStaff(@RequestBody ReqBusinessAddStaff reqBusinessAddStaff) {
        return sysStaffService.addSysStaff(reqBusinessAddStaff);
    }

    /**
     * 修改
     *
     * @param sysStaff
     * @return
     */
    @PutMapping("/update/updateSysStaff")
    public APIResponse updateSysStaff(@RequestBody ReqBusinessAddStaff sysStaff) {
        return sysStaffService.updateSysStaff(sysStaff);
    }

    /**
     * 删除
     *
     * @param staffIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{staffIds}")
    public APIResponse deleteById(@PathVariable("staffIds") Long[] staffIds) {
        return sysStaffService.deleteById(staffIds);
    }
}
