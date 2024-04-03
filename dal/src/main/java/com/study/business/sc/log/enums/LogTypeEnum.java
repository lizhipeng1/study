package com.study.business.sc.log.enums;

/**
 * 学生日志类型
 * @author ：zhangbaoyu
 * @date ：Created in 2020/12/19 10:15
 */
public enum LogTypeEnum {

    PAY_FEE("1","缴费"),
    ATTEND_CLA("2","上课"),
    DELETE_ATTEND_CLA("3","删除上课记录"),
    INVALID_ORDER("4","作废订单"),
    OUT_CLA("5","退出班级"),
    IN_CLA("6","进入班级"),
    ;

    private final String logType;

    private final String logTypeName;

    LogTypeEnum(String logType, String logTypeName) {
        this.logType = logType;
        this.logTypeName = logTypeName;
    }

    public String getLogType() {
        return logType;
    }

    public String getLogTypeName() {
        return logTypeName;
    }
}
