package com.study.business.tool.impt.service.strategy;

import com.study.business.tool.export.handler.bean.SelectValidationData;
import com.study.core.api.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 导出模板 导入数据策略
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/2 18:53
 */
public interface IImportStrategy {

    /**
     * excel 下拉数据校验 配置
     * @return
     */
    List<SelectValidationData> selectValidateConfig();

    /**
     * 导入数据
     * @return
     */
    APIResponse importData(Long importId, MultipartFile multipartFile) throws IOException;

    /**
     * 导入数据
     * @param importId
     * @param fileId
     * @return
     * @throws IOException
     */
    APIResponse importDataByFileId(Long importId, String fileId) throws IOException;

    /**
     * 校验数据
     * @param multipartFile
     * @return
     * @throws IOException
     */
    APIResponse checkData(MultipartFile multipartFile) throws IOException;

}
