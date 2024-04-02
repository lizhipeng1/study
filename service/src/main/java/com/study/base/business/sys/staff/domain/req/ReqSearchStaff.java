package com.study.base.business.sys.staff.domain.req;

import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchStaff extends ReqPageBase implements Serializable {
    private String staffName;
    private String sex;
    private String personnelStatus;
    private Integer teacher;
    private Long deptId;
}
