package com.study.business.sys.staff.domain.resp;

import com.study.business.sys.staff.repo.model.SysStaff;
import lombok.Data;

/**
 * 员工信息
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/18 11:03
 */
@Data
public class RespStaffInfo extends SysStaff {

    private String username;

    private String locked;

    private String personnelStatusName;

    private String deptName;
}
