package com.study.base.business.tool.impt.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传导入数据
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/3 21:16
 */
@Data
public class ReqUploadImplData {

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 文件
     */
    private MultipartFile file;

    private Long importId;

    private String fileId;
}
