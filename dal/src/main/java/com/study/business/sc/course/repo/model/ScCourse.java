package com.study.business.sc.course.repo.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程信息
 * </p>
 *
 * @author zhangby
 * @since 2020-03-17 11:25:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_course")
public class ScCourse implements Serializable {


    /**
     * 课程id
     */
    @TableId(value = "course_id")
    private Long courseId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 课程名称
     */
    @TableField("course_name")
    private String courseName;

    /**
     * 课程类型
     */
    @TableField("course_type_id")
    private Long courseTypeId;

    /**
     * 授课模式 1 班课 2 一对一
     */
    @TableField("teaching_mode")
    private String teachingMode;

    /**
     * 课程简介
     */
    @TableField("course_intro")
    private String courseIntro;

    /**
     * 是否在售 1在售 0停售
     */
    @TableField("sale")
    private String sale;

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
    @JsonIgnore
    private String lastUpdateUser;

    /**
     * 更新时间
     */
    @TableField("last_update_time")
    @JsonIgnore
    private Date lastUpdateTime;

    /**
     * 导入id
     */
    @TableId(value = "import_id")
    @JsonIgnore
    private Long importId;
}
