package com.study.business.sc.student.repo.model;

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
 * 学生课程
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_student_course")
public class ScStudentCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "student_course_id", type = IdType.ASSIGN_ID)
    private Long studentCourseId;

    /**
     * 学生
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 课程
     */
    @TableField("course_id")
    private Long courseId;

    /**
     * 课程
     */
    @TableField("course_name")
    private String courseName;

    /**
     * 报读校区
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 班级
     */
    @TableField("cla_id")
    private Long claId;

    /**
     * 班级
     */
    @TableField("cla_name")
    private String claName;

    /**
     * 收费模式 hour:课时 date:时间 cycle:期
     */
    @TableField("charge_type")
    private String chargeType;

    /**
     * 总天数
     */
    @TableField("total_day")
    private BigDecimal totalDay;

    /**
     * 总课时
     */
    @TableField("total_hour")
    private BigDecimal totalHour;

    /**
     * 剩余课时
     */
    @TableField("balance_hour")
    private BigDecimal balanceHour;

    /**
     * 总学费
     */
    @TableField("total_fee")
    private BigDecimal totalFee;

    /**
     * 状态 1在读 2停课
     */
    @TableField("status")
    private String status;

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
