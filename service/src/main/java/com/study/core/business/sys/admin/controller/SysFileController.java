package com.study.core.business.sys.admin.controller;

import cn.xluobo.business.sys.admin.domain.req.ReqUploadFile;
import cn.xluobo.business.sys.admin.service.BusinessSysFileService;
import cn.xluobo.config.properties.UploadConfigProperties;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.file.FileUtils;
import cn.xluobo.core.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 文件
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020-03-01 09:36
 */
@Controller
@RequestMapping("/api/system/file")
@Slf4j
public class SysFileController {

    @Autowired
    private BusinessSysFileService sysFileService;
    @Autowired
    private UploadConfigProperties uploadConfigProperties;

    /**
     * 上传图片
     *
     * @param reqUploadFile
     * @return
     */
    @PostMapping("/uploadImg")
    @ResponseBody
    public APIResponse updateImg(ReqUploadFile reqUploadFile) {
        try {
            return sysFileService.uploadImg(reqUploadFile);
        } catch (IOException e) {
            log.error("uploadAvatar error", e);
            return APIResponse.toExceptionResponse("上传图片失败");
        }
    }

    /**
     * 下载导出的excel
     * @param fileName
     * @param logicName
     * @throws Exception
     */
    @GetMapping("/downExportFile")
    @ResponseBody
    public void downExportFile(String fileName,String logicName) throws Exception {
        HttpServletResponse response = ServletUtils.getResponse();
        HttpServletRequest request = ServletUtils.getRequest();
        String agent = request.getHeader("USER-AGENT");
        String encodeFileName = FileUtils.encodeDownloadFileName(agent, logicName);

        String saveExportPath = uploadConfigProperties.getTempSaveExportPath();
        String fullPath = saveExportPath + "/" + fileName + ".xlsx";

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName + ".xlsx");

        File file = FileUtils.getFile(fullPath);
        if (!file.exists()) {
            return;
        }
        FileUtils.writeInputToOutput(new FileInputStream(file), response.getOutputStream());
        com.alibaba.excel.util.FileUtils.delete(file);
        Cookie status = new Cookie("exportDataStatus","success");
        status.setMaxAge(600);
        status.setPath("/");
        response.addCookie(status);
    }


}
