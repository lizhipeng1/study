package com.study.business.sc.course.repo.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * 上课出勤表
 * </p>
 *
 * @author zhangby
 * @since 2020-09-30 02:33:26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_cla_time_attend")
public class ScClaTimeAttend implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "attend_id", type = IdType.ASSIGN_ID)
    private Long attendId;

    @TableField(value = "student_course_id")
    private Long studentCourseId;

    @TableField(value = "course_order_id")
    private Long courseOrderId;

    /**
     * sc_cla_time.course_time_id
     */
    @TableField("course_time_id")
    private Long courseTimeId;

    /**
     * 学生
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 班级 冗余自动
     */
    @TableField("cla_id")
    private Long claId;

    /**
     * 课程 冗余自动
     */
    @TableField("course_id")
    private Long courseId;

    /**
     * 上课教师
     */
    @TableField("teacher_id")
    private Long teacherId;

    /**
     * 教师名
     */
    @TableField("teacher_name")
    private String teacherName;

    /**
     * 收费模式 hour:课时 date:时间 cycle:期
     */
    @TableField("charge_type")
    private String chargeType;

    /**
     * 出席状态 1:到课 2:请假 3:缺勤
     */
    @TableField("attend_status")
    private String attendStatus;

    /**
     * 教师获取课时数量
     */
    @TableField("teacher_get_hour")
    private BigDecimal teacherGetHour;

    /**
     * 扣减课时数量
     */
    @TableField("pay_hour")
    private BigDecimal payHour;

    /**
     * 学费消耗
     */
    @TableField("pay_fee")
    private BigDecimal payFee;

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
}
