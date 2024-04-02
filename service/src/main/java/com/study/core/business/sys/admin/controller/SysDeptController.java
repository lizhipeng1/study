package com.study.core.business.sys.admin.controller;


import cn.xluobo.business.sys.admin.domain.req.ReqSearchSysDept;
import cn.xluobo.business.sys.admin.domain.resp.RespTreeSelect;
import cn.xluobo.business.sys.admin.repo.model.SysDept;
import cn.xluobo.business.sys.admin.service.BusinessSysDeptService;
import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author zhangby
 * @since 2020-01-18
 */
@RestController
@RequestMapping("/api/system/dept")
public class SysDeptController {

    @Autowired
    private BusinessSysDeptService sysDeptService;

    /**
     * 列表
     *
     * @param reqSearchSysDept
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchSysDept reqSearchSysDept) {
        return sysDeptService.searchList(reqSearchSysDept);
    }

    /**
     * 前端select
     *
     * @return
     */
    @GetMapping("/list/treeSelect")
    public APIResponse treeSelect() {
        return sysDeptService.treeSelect();
    }

    /**
     * 详情
     *
     * @param deptId
     * @return
     */
    @GetMapping("/info/detailById/{deptId}")
    public APIResponse detailById(@PathVariable("deptId") Long deptId) {
        return sysDeptService.detailById(deptId);
    }

    /**
     * 添加
     *
     * @param sysDept
     * @return
     */
    @PostMapping("/add/addDept")
    public APIResponse addSysDept(@RequestBody SysDept sysDept) {
        return sysDeptService.addDept(sysDept);
    }

    /**
     * 修改
     *
     * @param sysDept
     * @return
     */
    @PutMapping("/update/updateDept")
    public APIResponse updateDept(@RequestBody SysDept sysDept) {
        return sysDeptService.updateDept(sysDept);
    }

    /**
     * 删除
     *
     * @param deptIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{deptIds}")
    public APIResponse deleteById(@PathVariable("deptIds") Long[] deptIds) {
        return sysDeptService.deleteById(deptIds);
    }

    /**
     * 校区列表
     *
     * @return
     */
    @GetMapping("/list/campusList")
    public APIResponse campusList() {
        List<RespTreeSelect> respTreeSelects = sysDeptService.campusList();
        return APIResponse.toAPIResponse(respTreeSelects);
    }

    /**
     * 校区列表
     *
     * @return
     */
    @GetMapping("/list/campusListLimitByUser")
    public APIResponse campusListLimitByUser() {
        List<RespTreeSelect> respTreeSelects = sysDeptService.campusListLimitByUser();
        return APIResponse.toAPIResponse(respTreeSelects);
    }

    /**
     * 可选校区类型
     * 如果拥有全部校区权限，返回全部校区 部分校区
     * 否二 返回 部分校区
     *
     * @return
     */
    @GetMapping("/list/campusSelect")
    public APIResponse campusSelect() {
        List<RespTreeSelect> respTreeSelects = sysDeptService.campusSelect();
        return APIResponse.toAPIResponse(respTreeSelects);
    }

    /**
     * 可选校区类型
     * 如果拥有全部校区权限，返回全部校区 部分校区
     * 否二 返回 部分校区
     *
     * @return
     */
    @GetMapping("/list/campusSelectLimitByUser")
    public APIResponse campusSelectLimitByUser() {
        List<RespTreeSelect> respTreeSelects = sysDeptService.campusSelectLimitByUser();
        return APIResponse.toAPIResponse(respTreeSelects);
    }
}
