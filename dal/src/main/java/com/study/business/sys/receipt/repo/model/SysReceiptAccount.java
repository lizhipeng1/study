package com.study.business.sys.receipt.repo.model;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 收款账户
 * </p>
 *
 * @author zhangby
 * @since 2020-09-05 05:36:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_receipt_account")
public class SysReceiptAccount implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "account_id", type = IdType.ASSIGN_ID)
    private Long accountId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 账户名称
     */
    @TableField("account_name")
    private String accountName;

    /**
     * 备注
     */
    @TableField("memo")
    private String memo;

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
