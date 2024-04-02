package com.study.base.business.sys.tag.domain.req;

import cn.xluobo.business.sys.tag.repo.model.SysTag;
import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchSysTag extends ReqPageBase implements Serializable {
    private String tenantId;
    private String tagName;
    private String tagType;
}
