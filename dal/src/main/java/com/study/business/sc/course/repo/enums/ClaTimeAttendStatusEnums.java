package com.study.business.sc.course.repo.enums;

/**
 * 到课状态
 * @author ：zhangbaoyu
 * @date ：Created in 2020/4/30 15:16
 */
public enum ClaTimeAttendStatusEnums {

    AT_CLASS("1","到课"),
    LEAVE_CLASS("2","请假"),
    OUT_CLASS("3","缺勤"),
    ;

    private final String attendStatus;

    private final String statusName;

    ClaTimeAttendStatusEnums(String attendStatus, String statusName) {
        this.attendStatus = attendStatus;
        this.statusName = statusName;
    }

    public String getAttendStatus() {
        return attendStatus;
    }

    public String getStatusName() {
        return statusName;
    }

    /**
     * 根据状态获取名称
     * @param status
     * @return
     */
    public static String getNameByStatus(String status) {
        for (ClaTimeAttendStatusEnums value : ClaTimeAttendStatusEnums.values()) {
            if(value.getAttendStatus().equals(status)) {
                return value.getStatusName();
            }
        }
        return "";
    }
}
