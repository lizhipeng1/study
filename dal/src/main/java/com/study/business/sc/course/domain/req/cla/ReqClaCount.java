package com.study.business.sc.course.domain.req.cla;

import com.study.core.page.ReqDeptCondition;
import lombok.Builder;
import lombok.Data;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/24 12:27
 */
@Data
@Builder
public class ReqClaCount  extends ReqDeptCondition {

    private Long teacherId;
}
