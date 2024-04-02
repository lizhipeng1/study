package com.study.base.business.sys.admin.enums;

/**
 * 角色枚举
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/17 12:09
 */
public enum RoleEnum {

    ADMIN("admin","系统管理员"),
    TENANT_MANAGER("tenantManager","租户管理"),
    SCHOOL("school","学校"),
    SCHOOL_MANAGER("schoolManager","校长"),
    TEACHING_MANAGER("teachingManager","教务管理"),
    TEACHER("teacher","教师")
    ;

    private String roleCode;

    private String roleName;

    RoleEnum(String roleCode, String roleName) {
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public String getRoleName() {
        return roleName;
    }
}
