package com.study.base.business.sys.admin.domain.req;

import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchOnlineUser extends ReqPageBase implements Serializable {
    private String username;
}
