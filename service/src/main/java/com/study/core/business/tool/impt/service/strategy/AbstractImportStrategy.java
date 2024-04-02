package com.study.core.business.tool.impt.service.strategy;

import cn.xluobo.business.tool.export.handler.bean.SelectValidationData;
import cn.xluobo.core.api.APIResponse;
import com.google.common.collect.Lists;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/11 11:19
 */
public abstract class AbstractImportStrategy implements IImportStrategy {

    @Override
    public List<SelectValidationData> selectValidateConfig(){
        return Lists.newArrayList();
    }

    @Override
    public APIResponse importData(Long importId, MultipartFile multipartFile) throws IOException{
        return APIResponse.toOkResponse();
    }

    @Override
    public APIResponse importDataByFileId(Long importId, String fileId) throws IOException{
        return APIResponse.toOkResponse();
    }

    @Override
    public APIResponse checkData(MultipartFile multipartFile) throws IOException {
        return APIResponse.toOkResponse();
    }
}
