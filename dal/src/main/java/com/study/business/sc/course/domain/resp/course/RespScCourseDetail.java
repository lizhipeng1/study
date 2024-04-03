package com.study.business.sc.course.domain.resp.course;

import com.study.business.sc.course.domain.req.course.ReqAddScCourse;
import lombok.Data;

/**
 * 课程详情
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/7/8 20:34
 */
@Data
public class RespScCourseDetail extends ReqAddScCourse {

    private Long courseId;

    private String courseTypeName;

}
