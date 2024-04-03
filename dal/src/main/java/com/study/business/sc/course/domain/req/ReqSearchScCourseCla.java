package com.study.business.sc.course.domain.req;

import com.study.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchScCourseCla extends ReqPageBase implements Serializable {
    private Long courseId;
    private Long staffId;
    private String claName;
    private String courseTime;
    private Long departId;
    /**
     * 收费模式
     */
    private String chargeType;
}
