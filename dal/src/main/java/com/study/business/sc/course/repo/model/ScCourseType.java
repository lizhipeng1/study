package com.study.business.sc.course.repo.model;

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
 * 课程类型
 * </p>
 *
 * @author zhangby
 * @since 2020-07-09 08:10:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_course_type")
public class ScCourseType implements Serializable {


    @TableId(value = "course_type_id")
    private Long courseTypeId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 课程类型名
     */
    @TableField("course_type")
    private String courseType;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 状态（1正常 0停用）
     */
    @TableField("in_use")
    private String inUse;

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
