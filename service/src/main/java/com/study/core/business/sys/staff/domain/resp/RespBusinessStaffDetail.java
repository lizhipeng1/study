package com.study.core.business.sys.staff.domain.resp;

import cn.xluobo.business.sys.admin.repo.model.SysUser;
import cn.xluobo.business.sys.staff.repo.model.SysStaff;
import lombok.Data;

import java.util.List;

/**
 * 员工详情
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/25 20:19
 */
@Data
public class RespBusinessStaffDetail {

    // 所属校区 全部、部分
    private String belongCampus;

    // 所属校区
    private List<Long> partCampus;

    // 所属校区名称
    private List<String> partCampusName;

    // 员工信息
    private SysStaff staffInfo;

    // 关联用户信息
    private SysUser userInfo;

    // 用户角色
    private List<String> roleTreeIdList;
}
