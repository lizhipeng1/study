package com.study.business.sc.student.repo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 学生基本信息
 * </p>
 *
 * @author zhangby
 * @since 2020-04-27 07:13:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_student")
public class ScStudent implements Serializable {


    /**
     * 学生id
     */
    @TableId(value = "student_id")
    private Long studentId;

    /**
     * 所属机构
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 所属学校
     */
    @TableField("school_id")
    private Long schoolId;

    /**
     * 学生姓名
     */
    @TableField("student_name")
    private String studentName;

    /**
     * 出生日期
     */
    @TableField("birth_day")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDay;

    /**
     * 性别 M男 F女
     */
    @TableField("sex")
    private String sex;

    /**
     * 监护人联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 入校时间
     */
    @TableField("in_time")
    private String inTime;

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
    private String schoolName;
    @TableField(exist = false)
    private Integer age;
    @TableField(exist = false)
    List<ScStudentContact> contactList;

    public Integer getAge() {
        if(null == age && null != birthDay) {
            DateTime now = new DateTime();
            DateTime birthDateTime = new DateTime(birthDay);
            Period period = new Period(birthDateTime, now, PeriodType.years());
            return period.getYears();
        } else {
            return age;
        }
    }
}
