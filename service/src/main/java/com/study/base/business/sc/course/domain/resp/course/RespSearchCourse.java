package com.study.base.business.sc.course.domain.resp.course;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/7/25 11:11
 */
@Data
public class RespSearchCourse implements Serializable {

    private Long courseId;

    private String courseName;

    private Long courseTypeId;

    /**
     * 课程类型
     */
    private String courseTypeName;

    /**
     * 授课模式
     */
    private String teachingMode;

    /**
     * 开班数
     */
    private Integer claCount;

    /**
     * 收费模式
     */
    private String chargeNames;

    /**
     * 开课校区
     */
    private String campusIds;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 课程状态
     */
    private String sale;

    /**
     * 课程简介
     */
    private String courseIntro;

    /**
     * 学生课程
     */
    private Long studentCourseId;
}
