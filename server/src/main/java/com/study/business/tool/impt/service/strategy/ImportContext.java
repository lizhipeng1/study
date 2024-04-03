package com.study.business.tool.impt.service.strategy;

import com.study.business.tool.export.handler.bean.SelectValidationData;
import com.study.business.tool.impt.domain.ReqUploadImplData;
import com.study.business.tool.impt.repo.model.ToolImport;
import com.study.business.tool.impt.repo.service.IToolImportService;
import com.study.config.login.LoginUser;
import com.study.config.properties.UploadConfigProperties;
import com.study.core.api.APIResponse;
import com.study.utils.LoginUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 策略上下文
 */
@Service
@Transactional
@Slf4j
public class ImportContext {

    private final Map<String, IImportStrategy> strategyMap = new ConcurrentHashMap<>();
    @Autowired
    private IToolImportService importService;
    @Autowired
    private UploadConfigProperties uploadConfigProperties;

    @Autowired
    public ImportContext(Map<String, IImportStrategy> strategyMap) {
        this.strategyMap.clear();
        strategyMap.forEach((k, v) -> this.strategyMap.put(k, v));
    }

    /**
     * 获取excel select下拉 校验配置
     *
     * @param beanName
     * @return
     */
    public List<SelectValidationData> selectValidateConfig(String beanName) {
        return strategyMap.get(beanName).selectValidateConfig();
    }

    /**
     * 导入数据
     *
     * @param uploadImplData
     * @return
     */
    @Transactional
    public APIResponse importData(ReqUploadImplData uploadImplData) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        // 保存导入记录
        ToolImport toolImport = new ToolImport();
        toolImport.setImportType(uploadImplData.getTemplateName());
        toolImport.setFileSize(uploadImplData.getFile().getSize());
        toolImport.setCreateUser(loginUser.getUserId());
        toolImport.setCreateTime(new Date());
        importService.save(toolImport);
        // 执行导入操作
        APIResponse apiResponse = null;
        try {
            apiResponse = strategyMap.get(uploadImplData.getTemplateName()).importData(toolImport.getImportId(),uploadImplData.getFile());
        } catch (Exception e) {
            log.error("importData error",e);
            apiResponse = APIResponse.toExceptionResponse(e.getMessage());
        }
        // 更新导入结果
        ToolImport update = new ToolImport();
        update.setImportId(toolImport.getImportId());
        update.setImportResult(apiResponse.getRespCode());
        update.setResultMemo(apiResponse.getRespMsg());
        importService.updateById(update);
        return apiResponse;
    }

    /**
     * 导入数据
     *
     * @param uploadImplData
     * @return
     */
    @Transactional
    public APIResponse importDataByFileId(ReqUploadImplData uploadImplData) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();

        String path = uploadConfigProperties.getTempSaveImportPath();
        path = path + "/" + uploadImplData.getTemplateName();
        File file = new File(path, uploadImplData.getFileId());
        if (!file.exists()) {
            return APIResponse.toExceptionResponse("无法获取文件,请重试");
        }

        // 保存导入记录
        ToolImport toolImport = new ToolImport();
        toolImport.setImportType(uploadImplData.getTemplateName());
        toolImport.setFileSize(file.length());
        toolImport.setCreateUser(loginUser.getUserId());
        toolImport.setCreateTime(new Date());
        importService.save(toolImport);
        // 执行导入操作
        APIResponse apiResponse = null;
        try {
            apiResponse = strategyMap.get(uploadImplData.getTemplateName()).importDataByFileId(toolImport.getImportId(),uploadImplData.getFileId());
        } catch (Exception e) {
            log.error("importDataByFileId error",e);
            apiResponse = APIResponse.toExceptionResponse(e.getMessage());
        }
        // 更新导入结果
        ToolImport update = new ToolImport();
        update.setImportId(toolImport.getImportId());
        update.setImportResult(apiResponse.getRespCode());
        update.setResultMemo(apiResponse.getRespMsg());
        importService.updateById(update);
        return apiResponse;
    }

    /**
     * 校验数据
     * @param uploadImplData
     * @return
     */
    public APIResponse checkData(ReqUploadImplData uploadImplData) {
        // 执行导入操作
        APIResponse apiResponse = null;
        try {
            apiResponse = strategyMap.get(uploadImplData.getTemplateName()).checkData(uploadImplData.getFile());
        } catch (Exception e) {
            log.error("checkData error",e);
            apiResponse = APIResponse.toExceptionResponse(e.getMessage());
        }
        return apiResponse;
    }


}
