package com.study.base.business.sc.course.domain.req;

import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchScCourseType extends ReqPageBase implements Serializable {
    private String courseType;
}
