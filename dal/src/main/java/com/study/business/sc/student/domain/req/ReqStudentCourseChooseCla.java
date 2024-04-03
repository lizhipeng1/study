package com.study.business.sc.student.domain.req;

import lombok.Data;

/**
 * 学生选择班级
 * 报读课程未选择班级 选择班级
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/30 09:53
 */
@Data
public class ReqStudentCourseChooseCla {

    // 报读编号 sc_student_course.student_course_id
    private Long[] chooseStudentCourseIds;

    // 选择的班级
    private Long claId;

    // 所属课程
    private Long courseId;

    public boolean checkParam() {
        if (null == chooseStudentCourseIds || chooseStudentCourseIds.length == 0) {
            return false;
        } else if (null == claId) {
            return false;
        } else if (null == courseId) {
            return false;
        }
        return true;
    }

}
