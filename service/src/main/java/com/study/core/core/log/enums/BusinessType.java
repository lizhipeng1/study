package com.study.core.core.log.enums;

/**
 * 业务操作类型
 */
public enum BusinessType {

    OTHER("其他", "other"),
    INSERT("新增", "insert"),
    UPDATE("修改","update"),
    DELETE("删除","delete"),
    ;

    private final String name;

    private final String value;

    BusinessType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
