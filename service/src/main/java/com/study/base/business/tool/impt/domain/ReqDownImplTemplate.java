package com.study.base.business.tool.impt.domain;

import lombok.Data;

/**
 * 下载导入模板
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/2 17:42
 */
@Data
public class ReqDownImplTemplate {

    private String templateName;

    /**
     * 导出文件名
     */
    private String logicFileName;

}
