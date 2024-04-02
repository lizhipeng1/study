package com.study.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-03-01 10:45
 */
@ConfigurationProperties(prefix = "xluobo.upload")
public class UploadConfigProperties {

    /**
     * 文件存储路径
     */
    private String filePath;

    /**
     * 临时存储下载文件
     */
    private String tempSaveExportPath;

    /**
     * 临时存储导入文件
     */
    private String tempSaveImportPath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTempSaveExportPath() {
        return tempSaveExportPath;
    }

    public void setTempSaveExportPath(String tempSaveExportPath) {
        this.tempSaveExportPath = tempSaveExportPath;
    }

    public String getTempSaveImportPath() {
        return tempSaveImportPath;
    }

    public void setTempSaveImportPath(String tempSaveImportPath) {
        this.tempSaveImportPath = tempSaveImportPath;
    }
}
