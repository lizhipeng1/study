package com.study.base.business.sc.course.domain.req;

import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询课程请求参数
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchScCourse extends ReqPageBase implements Serializable {

    private String courseName;

    /**
     * 是否开售
     */
    private String sale;

    /**
     * 教学模式
     */
    private String teachingMode;

    /**
     * 课程类型
     */
    private String courseTypeId;

    /**
     * 上课校区
     */
    private Long departId;

    /**
     * 收费模式
     */
    private String chargeType;

    /**
     * 学生 区分结果是否为续费
     */
    private Long studentId;
}
