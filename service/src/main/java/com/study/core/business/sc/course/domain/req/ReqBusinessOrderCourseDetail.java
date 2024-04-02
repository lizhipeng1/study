package com.study.core.business.sc.course.domain.req;

import lombok.Data;

/**
 * 报名时 获取课程详情
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/3 11:08
 */
@Data
public class ReqBusinessOrderCourseDetail {

    private Long studentId;

    private Long[] courseIds;

    private Long deptId;
}
