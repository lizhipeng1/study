package com.study.business.sys.admin.domain.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件
 * @author ：zhangbaoyu
 * @date ：Created in 2020-03-01 09:46
 */
@Data
public class ReqUploadFile {

    private MultipartFile file;

    private String fileType;

    private String fileIndex;
}
