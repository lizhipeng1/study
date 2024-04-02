package com.study.base.business.sc.student.domain.req;

import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * 报读列表 请求参数
 * @author ：zhangbaoyu
 * @date ：Created in 2020/12/21 20:28
 */
@Data
public class ReqSearchStuCourseSignUp extends ReqPageBase implements Serializable {

    private Long claId;

    // 所属校区
    private Long departId;

    // 所属课程
    private Long courseId;

    // 剩余最大天数
    private Integer minBalanceDay;

    // 剩余最大课时
    private Integer minBalanceHour;
}
