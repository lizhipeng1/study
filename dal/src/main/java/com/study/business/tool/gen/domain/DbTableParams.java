package com.study.business.tool.gen.domain;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-19 11:07
 */
@Data
public class DbTableParams {
    private String tableName;
    private String tableComment;
    private List<String> excludeTables = Arrays.asList("oauth_client_details","tool_gen_table","tool_gen_table_column");

    public DbTableParams(String tableName, String tableComment) {
        this.tableName = tableName;
        this.tableComment = tableComment;
    }

    public DbTableParams() {

    }
}
