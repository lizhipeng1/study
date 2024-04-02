package com.study.core.core.log.enums;

/**
 * 客户端
 */
public enum ClientType {

    PC("PC", "pc"),
    APP("APP","app"),
    ;

    private final String name;

    private final String value;

    ClientType(String name, String value) {
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
