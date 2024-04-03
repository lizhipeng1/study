package com.study.business.tool.impt.controller;

import com.study.business.tool.impt.domain.ReqDownImplTemplate;
import com.study.business.tool.impt.domain.ReqUploadImplData;
import com.study.business.tool.impt.service.BusinessImportService;
import com.study.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 导入
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/2 17:41
 */
@RestController
@RequestMapping("/api/tool/import")
public class ImportController {

    @Autowired
    private BusinessImportService importService;


    /**
     * 下载导入模板
     * @param downImplTemplate
     * @return
     */
    @GetMapping("/downImportTemplate")
    public void downImportTemplate(ReqDownImplTemplate downImplTemplate) throws IOException {
        importService.downImportTemplate(downImplTemplate);
    }

    /**
     * 导入文件
     * @param uploadImplData
     */
    @PostMapping("/uploadForImportData")
    public APIResponse uploadForImportData(ReqUploadImplData uploadImplData){
        return importService.uploadForImportData(uploadImplData);
    }

    /**
     * 校验文件
     * @param uploadImplData
     */
    @PostMapping("/uploadForCheckData")
    public APIResponse uploadForCheckData(ReqUploadImplData uploadImplData){
        return importService.uploadForCheckData(uploadImplData);
    }

    /**
     * 校验文件
     * @param uploadImplData
     */
    @PostMapping("/uploadForImportDataByFileId")
    public APIResponse uploadForImportDataByFileId(@RequestBody ReqUploadImplData uploadImplData){
        return importService.uploadForImportDataByFileId(uploadImplData);
    }
}
