package com.study.business.sys.admin.controller;


import com.study.business.sys.admin.domain.req.ReqSearchSysDictType;
import com.study.business.sys.admin.repo.model.SysDictType;
import com.study.business.sys.admin.service.BusinessSysDictTypeService;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.core.page.RespPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典类型表 前端控制器
 * </p>
 *
 * @author zhangby
 * @since 2020-01-15
 */
@RestController
@RequestMapping("/api/system/dict/type")
public class SysDictTypeController {

    @Autowired
    private BusinessSysDictTypeService sysDictTypeService;

    /**
     * 列表
     * @param reqSearchDictType
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchSysDictType reqSearchDictType){
        RespPage<SysDictType> respPage = sysDictTypeService.searchList(reqSearchDictType);
        return APIResponse.toAPIResponse(respPage);
    }

    /**
     * 列表 select
     * @return
     */
    @GetMapping("/list/optionSelect")
    public APIResponse optionSelect(){
        List<SysDictType> sysDictTypes = sysDictTypeService.optionSelect();
        return APIResponse.toAPIResponse(sysDictTypes);
    }

    /**
     * 详情
     * @param dictTypeId
     * @return
     */
    @GetMapping("/info/detailById/{dictTypeId}")
    public  APIResponse detailById(@PathVariable("dictTypeId") String dictTypeId){
        SysDictType detailInfo = sysDictTypeService.detailById(dictTypeId);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     * @param sysDictType
     * @return
     */
    @PostMapping("/add/addDictType")
    public APIResponse addDictType(@RequestBody SysDictType sysDictType){
        boolean addDictType = sysDictTypeService.addDictType(sysDictType);
        if(addDictType){
            return APIResponse.toOkResponse();
        }
        return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
    }

    /**
     * 修改
     * @param sysDictType
     * @return
     */
    @PutMapping("/update/updateDictType")
    public APIResponse updateDictType(@RequestBody SysDictType sysDictType){
        return sysDictTypeService.updateDictType(sysDictType);
    }

    /**
     * 删除
     * @param dictTypeIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{dictTypeIds}")
    public APIResponse deleteById(@PathVariable("dictTypeIds") String[] dictTypeIds){
        return sysDictTypeService.deleteById(dictTypeIds);
    }
}
