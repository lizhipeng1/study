package com.study.business.sc.base.domain.req;

import com.study.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchScRoom extends ReqPageBase implements Serializable {
    private Long deptId;
    private String roomName;
    private String memo;
}
