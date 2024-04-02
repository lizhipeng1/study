package com.study.base.business.sys.staff.repo.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工信息
 * </p>
 *
 * @author zhangby
 * @since 2020-03-17 11:27:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_staff")
public class SysStaff implements Serializable {


    /**
     * 教师id
     */
    @TableId(value = "staff_id")
    private Long staffId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    @TableField("user_id")
    private String userId;

    /**
     * 机构管理者
     */
    @TableField("super_staff")
    private Boolean superStaff;

    /**
     * 员工姓名
     */
    @TableField("staff_name")
    private String staffName;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email_address")
    private String emailAddress;

    /**
     * 性别 M男 F女
     */
    @TableField("sex")
    private String sex;

    /**
     * 入职日期
     */
    @TableField("entry_date")
    private String entryDate;

    /**
     * 人事状态 1正式员工 2实习员工 3试用期 4兼职员工 5停薪留职 6主动离职 7解聘
     */
    @TableField("personnel_status")
    private String personnelStatus;

    /**
     * 是否为教师
     */
    @TableField("teacher")
    private Boolean teacher;

    /**
     * 头像
     */
    @TableField("avatar_img")
    private String avatarImg;

    /**
     * 部门编号
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 删除标志（1删除 0在用）
     */
    @TableField("delete_flag")
    @TableLogic
    private String deleteFlag;

    /**
     * 创建者
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField("last_update_user")
    private String lastUpdateUser;

    /**
     * 更新时间
     */
    @TableField("last_update_time")
    private Date lastUpdateTime;
}
