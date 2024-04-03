package com.study.business.sys.admin.controller;


import com.study.business.sys.admin.domain.req.ReqPageSelect;
import com.study.business.sys.admin.domain.req.ReqSearchSysDictData;
import com.study.business.sys.admin.repo.model.SysDictData;
import com.study.business.sys.admin.service.BusinessSysDictDataService;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.core.page.RespPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author zhangby
 * @since 2020-01-15
 */
@RestController
@RequestMapping("/api/system/dict/data")
public class SysDictDataController {

    @Autowired
    private BusinessSysDictDataService sysDictDataService;

    /**
     * 列表
     * @param reqSearchDictData
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchSysDictData reqSearchDictData){
        RespPage<SysDictData> respPage = sysDictDataService.searchList(reqSearchDictData);
        return APIResponse.toAPIResponse(respPage);
    }

    /**
     * 详情
     * @param dictTypeId
     * @return
     */
    @GetMapping("/info/detailById/{dictTypeId}")
    public  APIResponse detailById(@PathVariable("dictTypeId") String dictTypeId){
        SysDictData detailInfo = sysDictDataService.detailById(dictTypeId);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     * @param sysDictData
     * @return
     */
    @PostMapping("/add/addDictData")
    public APIResponse addDictData(@RequestBody SysDictData sysDictData){
        boolean addDictData = sysDictDataService.addDictData(sysDictData);
        if(addDictData){
            return APIResponse.toOkResponse();
        }
        return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
    }

    /**
     * 修改
     * @param sysDictData
     * @return
     */
    @PutMapping("/update/updateDictData")
    public APIResponse updateDictData(@RequestBody SysDictData sysDictData){
        return sysDictDataService.updateDictData(sysDictData);
    }

    /**
     * 删除
     * @param dictTypeIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{dictTypeIds}")
    public APIResponse deleteById(@PathVariable("dictTypeIds") String[] dictTypeIds){
        return sysDictDataService.deleteById(dictTypeIds);
    }

    /**
     * 某字典数据
     * @param dictType
     * @return
     */
    @GetMapping("/list/dictType/{dictType}")
    public APIResponse dictTypeDataList(@PathVariable("dictType") String dictType){
        List<SysDictData> dictDataList = sysDictDataService.dictTypeDataList(dictType);
        return APIResponse.toAPIResponse(dictDataList);
    }

    /**
     * 某字典数据
     * 分页查询
     * @param dictType
     * @return
     */
    @GetMapping("/list/dictTypeByPage/{dictType}")
    public APIResponse dictTypeDataPageList(@PathVariable("dictType") String dictType,
                                        ReqPageSelect reqPageSelect){
        RespPage<SysDictData> respPage = sysDictDataService.dictTypeDataList(dictType, reqPageSelect);
        return APIResponse.toAPIResponse(respPage);
    }

    /**
     * 某字典数据
     * @param dictType
     * @param parentValue
     * @return
     */
    @GetMapping("/list/dictType/{dictType}/{parentValue}")
    public APIResponse dictTypeDataListByParentValue(@PathVariable("dictType") String dictType,
                                        @PathVariable("parentValue") String parentValue){
        List<SysDictData> dictDataList = sysDictDataService.dictTypeDataListByParentValue(dictType, parentValue);
        return APIResponse.toAPIResponse(dictDataList);
    }

}
