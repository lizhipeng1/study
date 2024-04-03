package com.study.business.sys.admin.repo.model;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 租户信息
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_tenant")
public class SysTenant implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("tenant_id")
    private String tenantId;

    /**
     * 租户名称
     */
    @TableField("tenant_name")
    private String tenantName;

    /**
     * 超级管理租户
     */
    @TableField("super_tenant")
    private Boolean superTenant;

    /**
     * 联系人
     */
    @TableField("contact_name")
    private String contactName;

    /**
     * 联系人电话
     */
    @TableField("contact_phone")
    private String contactPhone;

    /**
     * 联系地址
     */
    @TableField("contact_address")
    private String contactAddress;

    /**
     * 生效开始时间
     */
    @TableField("begin_time")
    private Date beginTime;

    /**
     * 失效时间
     */
    @TableField("end_time")
    private Date endTime;

    /**
     * 备注
     */
    @TableField("memo")
    private String memo;

    /**
     * 状态（1正常 0停用）
     */
    @TableField("in_use")
    private String inUse;

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


    @TableField(exist = false)
    private String[] useDateRange;
    //当前租户
    @TableField(exist = false)
    private boolean nowTenant = false;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date getBeginTime() {
        return beginTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date getEndTime() {
        return endTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
