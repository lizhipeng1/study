package com.study.base.business.sc.course.domain.req;

import lombok.Data;

/**
 * 课程班级 select
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020-04-27 19:41
 */
@Data
public class ReqCourseClaSelect {

    private String search;

    private Integer maxRecord = 500;

}
