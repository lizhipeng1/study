package com.study.business.sc.course.domain.resp;

import lombok.Builder;
import lombok.Data;

/**
 * 业务返回 课程信息
 * @author ：zhangbaoyu
 * @date ：Created in 2020-04-03 14:15
 */
@Data
@Builder
public class RespBusinessCourseInfo {

    private Long courseId;

    private String courseName;
}
