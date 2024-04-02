package com.study.base.business.sc.course.domain.req.time;

import cn.xluobo.core.page.ReqDeptCondition;
import lombok.Builder;
import lombok.Data;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/24 12:21
 */
@Data
@Builder
public class ReqClaTimeCount extends ReqDeptCondition {

    private String beginDate;
    private String endDate;
    private Boolean hadBegin;
    private Long teacherId;

}
