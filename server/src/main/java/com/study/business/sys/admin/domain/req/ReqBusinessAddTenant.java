package com.study.business.sys.admin.domain.req;

import com.study.business.sys.admin.repo.model.SysTenant;
import com.study.business.sys.admin.repo.model.SysUser;
import com.study.business.sys.staff.repo.model.SysStaff;
import lombok.Data;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/25 12:27
 */
@Data
public class ReqBusinessAddTenant extends SysTenant {

    private String emailAddress;

    private String sex;

    private String username;

    private String password;

    private String checkPass;

    public SysUser transferToSysUser() {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(this.getUsername());
        sysUser.setPassword(this.getPassword());
        sysUser.setName(this.getContactName());
        sysUser.setPhone(this.getContactPhone());
        sysUser.setEmailAddress(this.getEmailAddress());
        return sysUser;
    }

    public SysStaff transferToSysStaff() {
        SysStaff sysStaff = new SysStaff();
        sysStaff.setStaffName(this.getContactName());
        sysStaff.setEmailAddress(this.getEmailAddress());
        sysStaff.setPhone(this.getContactPhone());
        sysStaff.setSex(this.getSex());
        sysStaff.setSuperStaff(true);
        return sysStaff;
    }

}
