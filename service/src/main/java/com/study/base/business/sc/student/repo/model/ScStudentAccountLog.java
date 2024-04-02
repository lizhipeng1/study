package com.study.base.business.sc.student.repo.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 账户变更日志
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_student_account_log")
public class ScStudentAccountLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "account_log_id", type = IdType.ASSIGN_ID)
    private String accountLogId;

    /**
     * 账户id
     */
    @TableField("account_id")
    private String accountId;

    /**
     * 学生
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 1储值 2消费
     */
    @TableField("log_type")
    private String logType;

    /**
     * 变更金额
     */
    @TableField("change_fee")
    private BigDecimal changeFee;

    /**
     * 变更时间
     */
    @TableField("change_time")
    private Date changeTime;

    /**
     * 变更备注
     */
    @TableField("change_memo")
    private String changeMemo;

    /**
     * 变更人
     */
    @TableField("last_update_user")
    private String lastUpdateUser;


}
