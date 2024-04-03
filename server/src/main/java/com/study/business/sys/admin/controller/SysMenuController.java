package com.study.business.sys.admin.controller;


import com.study.business.sys.admin.domain.req.ReqSearchSysMenu;
import com.study.business.sys.admin.domain.resp.RespTreeSelect;
import com.study.business.sys.admin.repo.model.SysMenu;
import com.study.business.sys.admin.service.BusinessSysMenuService;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author zhangby
 * @since 2019-11-06
 */
@RestController
@RequestMapping("/api/system/menu")
public class SysMenuController {

    @Autowired
    private BusinessSysMenuService sysMenuService;

    /**
     * 菜单列表
     * @param reqSearchMenu
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchSysMenu reqSearchMenu){
        List<SysMenu> sysMenuList = sysMenuService.searchList(reqSearchMenu);
        return APIResponse.toAPIResponse(sysMenuList);
    }

    /**
     * 前端select
     * @return
     */
    @GetMapping("/list/treeSelect")
    public APIResponse treeSelect(){
        List<RespTreeSelect> respTreeSelects = sysMenuService.treeSelect();
        return APIResponse.toAPIResponse(respTreeSelects);
    }

    /**
     * 前端select
     * 包含 隐藏的菜单
     * @return
     */
    @GetMapping("/list/treeSelectIncludeHide")
    public APIResponse treeSelectIncludeHide(){
        List<RespTreeSelect> respTreeSelects = sysMenuService.treeSelectIncludeHide();
        return APIResponse.toAPIResponse(respTreeSelects);
    }

    /**
     * 菜单详情
     * @param menuId
     * @return
     */
    @GetMapping("/info/detailById/{menuId}")
    public  APIResponse detailById(@PathVariable("menuId") String menuId){
        SysMenu detailInfo = sysMenuService.detailById(menuId);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加菜单
     * @param sysMenu
     * @return
     */
    @PostMapping("/add/addMenu")
    public APIResponse addMenu(@RequestBody SysMenu sysMenu){
        boolean addMenu = sysMenuService.addMenu(sysMenu);
        if(addMenu){
            return APIResponse.toOkResponse();
        }
        return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
    }

    /**
     * 修改菜单
     * @param sysMenu
     * @return
     */
    @PutMapping("/update/updateMenu")
    public APIResponse updateMenu(@RequestBody SysMenu sysMenu){
        boolean updateMenu = sysMenuService.updateMenu(sysMenu);
        if(updateMenu){
            return APIResponse.toOkResponse();
        }
        return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @DeleteMapping("/delete/deleteById/{menuId}")
    public APIResponse deleteById(@PathVariable("menuId") String menuId){
        boolean updateMenu = sysMenuService.deleteById(menuId);
        if(updateMenu){
            return APIResponse.toOkResponse();
        }
        return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
    }

    /**
     * 角色对应的菜单Id列表
     * @return
     */
    @GetMapping("/list/roleMenuTreeIdList/{roleId}")
    public APIResponse roleMenuTreeIdList(@PathVariable("roleId") Long roleId){
        List<String> menuTreeIdList = sysMenuService.selectRoleMenuTreeIdList(roleId);
        return APIResponse.toAPIResponse(menuTreeIdList);
    }
}
