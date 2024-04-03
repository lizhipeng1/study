package com.study.business.sc.student.domain.req;

import com.study.core.page.ReqDeptCondition;
import lombok.Data;

/**
 * 查询学生报读的课程信息
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/8 22:03
 */
@Data
public class ReqSearchStudentCourse extends ReqDeptCondition {

    private Long studentId;

}
