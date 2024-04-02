package com.study.core.business.sc.course.domain.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 班级学生
 * </p>
 *
 * @author zhangby
 * @since 2020-04-27 07:13:40
 */
@Data
public class RespBusinessClaStudent implements Serializable {

    /**
     * 全部可选学生
     */
    private List<RespBusinessStudent> students;

    /**
     * 已有学生
     */
    private List<Long> claStudentIds;

    /**
     * 班级信息
     */
    private RespBusinessClaInfo claInfo;

    /**
     * 课程信息
     */
    private RespBusinessCourseInfo courseInfo;

    public RespBusinessClaStudent(List<RespBusinessStudent> students, List<Long> claStudentIds, RespBusinessClaInfo claInfo, RespBusinessCourseInfo courseInfo) {
        this.students = students;
        this.claStudentIds = claStudentIds;
        this.claInfo = claInfo;
        this.courseInfo = courseInfo;
    }
}
