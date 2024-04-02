package com.study.base.business.sc.base.domain.req;

import cn.xluobo.business.sc.base.repo.model.ScRoom;
import cn.xluobo.core.page.ReqPageBase;
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
