package com.study.base.business.sc.course.repo.enums;

/**
 * 排课 来源
 * @author ：zhangbaoyu
 * @date ：Created in 2020/4/30 15:13
 */
public enum ClaTimeSourceEnums {

    PLAN_CLA_TIME("1","排课"),
    UN_PLAN_CLA_TIME("2","未排课上课"),
    ONE_ADD("3","单个新增"),
    ;

    private final String source;

    private final String sourceName;

    ClaTimeSourceEnums(String source, String sourceName) {
        this.source = source;
        this.sourceName = sourceName;
    }

    public String getSource() {
        return source;
    }

    public String getSourceName() {
        return sourceName;
    }
}
