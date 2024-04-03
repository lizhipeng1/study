package com.study.config.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-19 14:19
 */
@Configuration
@EnableConfigurationProperties(GenCodeProperties.class)
public class GenCodeConfig {

    /**
     * 不编辑的字段
     */
    public static final List<String> notEditList = Arrays.asList("create_user", "create_time", "last_update_user", "last_update_time", "delete_flag");
    /**
     * 不在table展示的字段
     */
    public static final List<String> notTableList = Arrays.asList("create_user", "create_time", "last_update_user", "last_update_time", "delete_flag");
    /**
     * 不查询的字段
     */
    public static final List<String> notQueryList = Arrays.asList("create_user", "create_time", "last_update_user", "last_update_time", "delete_flag");
    /**
     * radio 字段
     */
    public static final List<String> htmlRadioTypeList = Arrays.asList("in_user");
    /**
     * textarea 字段
     */
    public static final List<String> htmlTextAreaTypeList = Arrays.asList("memo", "remark");
}
