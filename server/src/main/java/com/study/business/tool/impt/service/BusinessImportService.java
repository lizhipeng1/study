package com.study.business.tool.impt.service;

import com.study.business.tool.export.handler.SelectWriteHandler;
import com.study.business.tool.export.handler.bean.SelectValidationData;
import com.study.business.tool.impt.domain.ReqDownImplTemplate;
import com.study.business.tool.impt.domain.ReqUploadImplData;
import com.study.business.tool.impt.service.strategy.ImportContext;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.core.file.FileUtils;
import com.study.core.utils.ServletUtils;
import com.alibaba.excel.EasyExcel;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 导入
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/2 17:44
 */
@Service
@Transactional
public class BusinessImportService {

    @Autowired
    private ImportContext selectValidateContext;

    /**
     * 下载导入模板
     *
     * @param downImplTemplate
     * @return
     */
    public void downImportTemplate(ReqDownImplTemplate downImplTemplate) throws IOException {
        if (StringUtils.isAnyEmpty(downImplTemplate.getTemplateName(), downImplTemplate.getLogicFileName())) {
            return;
        }
        HttpServletRequest request = ServletUtils.getRequest();
        HttpServletResponse response = ServletUtils.getResponse();
        String agent = request.getHeader("USER-AGENT");
        String encodeFileName = FileUtils.encodeDownloadFileName(agent, downImplTemplate.getLogicFileName());

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName + ".xlsx");

        String templateFileName = "import/" + downImplTemplate.getTemplateName() + ".xlsx";

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(templateFileName);

        List<SelectValidationData> validateConfig = selectValidateContext.selectValidateConfig(downImplTemplate.getTemplateName());

        EasyExcel.write(response.getOutputStream())
                .autoCloseStream(Boolean.FALSE)
                .withTemplate(inputStream)
                .registerWriteHandler(new SelectWriteHandler(validateConfig))
                .sheet().doWrite(Lists.emptyList());
    }

    /**
     * 上传 导入数据
     *
     * @param uploadImplData
     * @return
     */
    public APIResponse uploadForImportData(ReqUploadImplData uploadImplData) {
        MultipartFile file = uploadImplData.getFile();
        String templateName = uploadImplData.getTemplateName();
        if (null == file || StringUtils.isEmpty(templateName)) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }
        return selectValidateContext.importData(uploadImplData);
    }

    /**
     * 上传 校验数据
     *
     * @param uploadImplData
     * @return
     */
    public APIResponse uploadForCheckData(ReqUploadImplData uploadImplData) {
        MultipartFile file = uploadImplData.getFile();
        String templateName = uploadImplData.getTemplateName();
        if (null == file || StringUtils.isEmpty(templateName)) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }
        return selectValidateContext.checkData(uploadImplData);
    }

    /**
     * 上传 导入数据
     *
     * @param uploadImplData
     * @return
     */
    public APIResponse uploadForImportDataByFileId(ReqUploadImplData uploadImplData) {
        String templateName = uploadImplData.getTemplateName();
        String fileId = uploadImplData.getFileId();
        if (StringUtils.isEmpty(fileId) || StringUtils.isEmpty(templateName)) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }
        return selectValidateContext.importDataByFileId(uploadImplData);
    }
}
