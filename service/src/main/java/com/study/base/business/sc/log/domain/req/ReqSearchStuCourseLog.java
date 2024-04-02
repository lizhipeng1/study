package com.study.base.business.sc.log.domain.req;

import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/12/21 14:44
 */
@Data
public class ReqSearchStuCourseLog extends ReqPageBase implements Serializable {

    private Long studentId;

    private Long courseId;

    private Long claId;

    private String logType;

    private String beginDate;

    private String endDate;

}
