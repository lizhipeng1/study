package com.study.business.sc.log.repo.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程缴费扣费记录
 * </p>
 *
 * @author zhangby
 * @since 2020-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_student_course_log")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScStudentCourseLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "log_id", type = IdType.ASSIGN_ID)
    private Long logId;

    /**
     * 学生
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 日志类型 参照 LogTypeEnum
     */
    @TableField("log_type")
    private String logType;

    /**
     * 课程
     */
    @TableField("course_id")
    private Long courseId;

    /**
     * 课程名称
     */
    @TableField("course_name")
    private String courseName;

    /**
     * 班级
     */
    @TableField("cla_id")
    private Long claId;

    /**
     * 班级名称
     */
    @TableField("cla_name")
    private String claName;

    /**
     * 经办校区
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 变更课时
     */
    @TableField("change_hour")
    private BigDecimal changeHour;

    /**
     * 变更后剩余课时
     */
    @TableField("after_balance_hour")
    private BigDecimal afterBalanceHour;

    /**
     * 变更金额
     */
    @TableField("change_fee")
    private BigDecimal changeFee;

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
     * 创建者
     */
    @TableField("create_user_name")
    private String createUserName;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    // 学生姓名
    @TableField(exist = false)
    private String studentName;

}
