package com.study.business.sc.student.repo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 联系人
 * </p>
 *
 * @author zhangby
 * @since 2020-09-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_student_contact")
public class ScStudentContact implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 联系人编号
     */
    @TableId(value = "contact_id", type = IdType.ASSIGN_ID)
    private Long contactId;

    /**
     * 学生
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 联系人称呼
     */
    @TableField("contact_nick")
    private String contactNick;

    /**
     * 与学生关系
     */
    @TableField("contact_relation")
    private String contactRelation;

    /**
     * 联系电话
     */
    @TableField("contact_phone")
    private String contactPhone;

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
