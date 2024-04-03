package com.study.business.sc.course.domain.resp;

import lombok.Data;

/**
 * 班级 select
 * @author ：zhangbaoyu
 * @date ：Created in 2020/5/6 13:11
 */
@Data
public class RespCourseClaSelectInfo {

    private Long claId;

    private String claName;

    private Long courseId;

    private String courseName;

    private String staffName;

    private Long deptId;

    private String deptName;

}
