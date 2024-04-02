package com.study.core.business.sc.course.domain.resp;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 选择课程信息
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/26 17:01
 */
@Data
@Builder
public class RespBusinessChooseCourseInfo {

    private Long courseId;

    private String courseName;

    private Long deptId;

    private String deptName;

    private String teachingMode;

    private List<RespBusinessChooseCourseCharge> courseChargeList;

    // 续报
    private Boolean continueCourse;

}
