package com.study.core.core.log.enums;

/**
 * 操作模块
 */
public enum OperateModule {

    OTHER("其他", "other"),
    SYS("系统", "sys"),
    USER("用户","user"),
    ;

    private final String name;

    private final String value;

    OperateModule(String name, String value) {
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
