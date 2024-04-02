package com.study.base.business.sys.admin.domain.req;

import cn.xluobo.business.sys.admin.repo.model.SysUser;
import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchSysUser extends ReqPageBase implements Serializable {
    private String username;
    private String name;
    private String phone;
    private Long deptId;
}
