package com.study.business.sc.course.repo.enums;

/**
 * 排课 状态
 * @author ：zhangbaoyu
 * @date ：Created in 2020/4/30 15:16
 */
public enum ClaTimeStatusEnums {

    WAIT_CLASS("1","待上课"),
    HAD_CLASS("2","已上课"),
    CHANGE_CLASS("3","已调课"),
    ;

    private final String status;

    private final String statusName;

    ClaTimeStatusEnums(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }
}
