package com.study.base.business.sc.course.domain.req.time;

import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchScClaTimeAttend extends ReqPageBase implements Serializable {

    private Long courseTimeId;

//    private String chargeType;
    private String attendStatus;

    private Long studentCourseId;

    private Long studentId;

    private Long teacherId;

    private String tenantId;
}
