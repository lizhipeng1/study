package com.study.business.sc.course.domain.resp.time;

import com.study.core.constants.SysConstant;
import lombok.Data;

/**
 * 单节排课信息
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/23 18:54
 */
@Data
public class ClaTimeCalendarItem {

    private Long courseTimeId;

    private String claName;

    private String courseName;

    // 日期
    private String claDate;

    // 星期
    private String weekDay;

    private String startTime;

    private String endTime;

    private String staffName;

    private int studentCount;

    // 教室
    private String roomName;

    private String claColor;

    // 上课状态
    private String claTimeStatus;

    public ClaTimeCalendarItem transfer(RespClaTimeCalendar respClaTime) {
        this.courseTimeId = respClaTime.getCourseTimeId();
        this.claName = respClaTime.getClaName();
        this.courseName = respClaTime.getCourseName();
        this.claDate = respClaTime.getClaDate();
        this.weekDay = SysConstant.WEEK_DAY_MAP.get(respClaTime.getWeekDay());
        this.startTime = respClaTime.getStartTime().substring(0,5);
        this.endTime = respClaTime.getEndTime().substring(0,5);
        this.staffName = respClaTime.getStaffName();
        this.studentCount = respClaTime.getStudentCount();
        this.roomName = respClaTime.getRoomName();
        this.claColor = respClaTime.getClaColor();
        this.claTimeStatus = respClaTime.getStatus();
        return this;
    }

}
