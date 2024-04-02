package com.study.base.business.sc.course.domain.resp;

import lombok.Data;

/**
 * 班级列表信息
 * @author ：zhangbaoyu
 * @date ：Created in 2020-03-23 19:36
 */
@Data
public class RespCourseClaInfo {

    private Long claId;

    private String claName;

    // 当前人数
    private Integer studentCnt;

    // 满班人数
    private String capacity;

    private String recruitStatus;

    private String openDate;

    private Long courseId;

    private String courseName;

    private Long staffId;

    private String staffName;

    private String deptName;

    // 上课星期
    private String weekDay;

}
