package com.study.business.sys.admin.controller;


import com.study.business.sys.admin.domain.req.ReqBusinessAddTenant;
import com.study.business.sys.admin.domain.req.ReqSearchSysTenant;
import com.study.business.sys.admin.repo.model.SysTenant;
import com.study.business.sys.admin.service.BusinessSysTenantService;
import com.study.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 租户信息 前端控制器
 * </p>
 *
 * @author zhangby
 * @since 2019-11-06
 */
@RestController
@RequestMapping("/api/system/tenant")
public class SysTenantController {

    @Autowired
    private BusinessSysTenantService sysTenantService;

    /**
     * 列表
     *
     * @param reqSearchSysTenant
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchSysTenant reqSearchSysTenant) {
        return sysTenantService.searchList(reqSearchSysTenant);
    }

    /**
     * 前端select
     *
     * @return
     */
    @GetMapping("/list/treeSelect")
    public APIResponse treeSelect() {
        return sysTenantService.treeSelect();
    }

    /**
     * 详情
     *
     * @param tenantId
     * @return
     */
    @GetMapping("/info/detailById/{tenantId}")
    public APIResponse detailById(@PathVariable("tenantId") Long tenantId) {
        return sysTenantService.detailById(tenantId);
    }

    /**
     * 添加
     *
     * @param reqBusinessAddTenant
     * @return
     */
    @PostMapping("/add/addTenant")
    public APIResponse addSysTenant(@RequestBody ReqBusinessAddTenant reqBusinessAddTenant) {
        return sysTenantService.addTenant(reqBusinessAddTenant);
    }

    /**
     * 修改
     *
     * @param sysTenant
     * @return
     */
    @PutMapping("/update/updateTenant")
    public APIResponse updateTenant(@RequestBody SysTenant sysTenant) {
        return sysTenantService.updateTenant(sysTenant);
    }

    /**
     * 删除
     *
     * @param tenantIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{tenantIds}")
    public APIResponse deleteById(@PathVariable("tenantIds") String[] tenantIds) {
        return sysTenantService.deleteById(tenantIds);
    }

    /**
     * 用户所属租户列表
     * 需当前登录拥有的租户才可以展示出来
     *
     * @param userId
     * @return
     */
    @GetMapping("/list/userTenantSelectLimitSelf/{userId}")
    public APIResponse userTenantSelectLimitSelf(@PathVariable("userId") String userId){
        return sysTenantService.userTenantSelectLimitSelf(userId);
    }

    /**
     * 用户所属租户列表
     *
     * @return
     */
    @GetMapping("/list/userTenantSelect/{userId}")
    public APIResponse userTenantSelect(@PathVariable("userId") String userId){
        return sysTenantService.userTenantSelect(userId);
    }

    /**
     * 当前登录人租户列表
     *
     * @return
     */
    @GetMapping("/list/loginUserTenantSelect")
    public APIResponse loginUserTenantSelect(){
        return sysTenantService.loginUserTenantSelect();
    }

    /**
     * 当前租户信息
     * @return
     */
    @GetMapping("/info/nowTenantInfo")
    public APIResponse nowTenantInfo() {
        SysTenant tenantInfo = sysTenantService.nowTenantInfo();
        return APIResponse.toAPIResponse(tenantInfo);
    }
}
