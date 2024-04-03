package com.study.business.sys.admin.domain.req;

import com.study.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchSysTenant extends ReqPageBase implements Serializable {

    private String tenantName;
    private String contactName;
    private String inUse;
    //过期开始时间
    private String beginTime;
    //过期结束
    private String endTime;
}
