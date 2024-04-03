package com.study.business.tool.gen.domain;

import lombok.Builder;
import lombok.Data;

/**
 * 生成代码模板
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-20 21:14
 */
@Data
public class GenCodeTemplate {

    /**
     *
     */
    public enum JavaType {
        ENTITY,
        MAPPER,
        SERVICEIMPL,
        BUSINESS_SERVICE,
        ISERVICE,
        CONTROLLER,
        REQ_ENTITY
        ;
    }

    /**
     * 模板文件名
     */
    private String templateFileName;

    /**
     * 生成包路径
     */
    private String packageOrDir;

    /**
     * 输出文件名
     */
    private String outFileName;

    /**
     * 类型
     */
    private JavaType javaType;

    /**
     * 是否将基础路径 拼接到 包路径中
     */
    private boolean prefixPkg = true;

    /**
     * 生成文件夹
     */
    private String outPath;
}
