package com.study.base.business.sc.course.domain.req.time;

import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchScClaTimeRule extends ReqPageBase implements Serializable {
    private Long deptId;
    private Long claId;
    private Long teacherId;
    private String[] claDate;
    private Boolean filterHoliday;
    private String beginDate;
    private String endDate;

    public void setClaDate(String[] claDate) {
        this.claDate = claDate;
        if (null != claDate && claDate.length == 2 && StringUtils.isAnyEmpty(beginDate, endDate)) {
            this.beginDate = claDate[0];
            this.endDate = claDate[1];
        }
    }
}
