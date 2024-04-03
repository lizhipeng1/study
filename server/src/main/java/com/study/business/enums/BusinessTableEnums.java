package com.study.business.enums;

/**
 * 表枚举
 */
public enum BusinessTableEnums {

    SYS_USER("sys_user","系统用户"),
    SYS_STAFF("sys_staff","员工表",true),
    SYS_DEPT("sys_dept","部门",true),

    SYS_TAG("sys_tag","标签",true),

    SYS_RECEIPT_ACCOUNT("sys_receipt_account","机构收费账户",true),

    SC_SCHOOL("sc_school","学校",true),
    SC_ROOM("sc_room","教室",true),
    SC_TEACHER("sc_teacher","教师",true),
    SC_COURSE("sc_course","课程",true),
    SC_COURSE_CLA("sc_course_cla","课程班级",true),
    SC_STUDENT("sc_student","学生",true),
    SC_COURSE_TYPE("sc_course_type","课程类型",true),
    SC_ORDER("sc_order","订单",true),
    SC_STUDENT_ACCOUNT("sc_student_account","学生余额账户",true),
    SC_STUDENT_COURSE("sc_student_course","学生报读课程",true),
    SC_STUDENT_COURSE_LOG("sc_student_course_log","学生日志",true),

    STOCK_GOODS("stock_goods","商品信息",true),
    STOCK_INFO("stock_info","库存信息",true),

    WECHAT_CP_ACCOUNT("wechat_cp_account","企业应用信息",true),
    WECHAT_CP_CONTACT("wechat_cp_contact","企业外部联系人",true),
    WECHAT_CP_CONTACT_WAY("wechat_cp_contact_way","企业客户联系我",true),
    WECHAT_CP_GROUP("wechat_cp_group","企业标签组",true),
    WECHAT_CP_GROUP_TAG("wechat_cp_group_tag","企业标签",true),
    WECHAT_CP_USER("wechat_cp_user","企业通讯录",true),

    ;

    private String table;

    private String tableName;

    /**
     * 是否为多租户 table
     */
    private boolean isTenant = false;

    BusinessTableEnums(String table, String tableName) {
        this.table = table;
        this.tableName = tableName;
    }

    BusinessTableEnums(String table, String tableName, boolean isTenant) {
        this.table = table;
        this.tableName = tableName;
        this.isTenant = isTenant;
    }

    public String getTable() {
        return table;
    }

    public String getTableName() {
        return tableName;
    }

    public boolean isTenant() {
        return isTenant;
    }
}
