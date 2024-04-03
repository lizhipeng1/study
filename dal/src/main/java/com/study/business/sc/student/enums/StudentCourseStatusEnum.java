package com.study.business.sc.student.enums;

/**
 * 上课状态
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/3 09:32
 */
public enum StudentCourseStatusEnum {

    AT_CLA("1","在读"),
    STOP_CLA("2","停课");

    private final String studentCourseStatus;
    private final String studentCourseStatusName;

    StudentCourseStatusEnum(String studentCourseStatus, String studentCourseStatusName) {
        this.studentCourseStatus = studentCourseStatus;
        this.studentCourseStatusName = studentCourseStatusName;
    }

    public String getStudentCourseStatus() {
        return studentCourseStatus;
    }

    public String getStudentCourseStatusName() {
        return studentCourseStatusName;
    }
}
