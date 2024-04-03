package com.study.business.sc.course.repo.model;

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
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 排课信息
 * </p>
 *
 * @author zhangby
 * @since 2020-09-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_cla_time")
public class ScClaTime implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 排课编号
     */
    @TableId(value = "course_time_id", type = IdType.ASSIGN_ID)
    private Long courseTimeId;

    /**
     * 规则编号
     */
    @TableField("rule_id")
    private Long ruleId;

    /**
     * 班级id
     */
    @TableField("cla_id")
    private Long claId;

    /**
     * 任课教师
     */
    @TableField("teacher_id")
    private Long teacherId;

    /**
     * 上课教室
     */
    @TableField("room_id")
    private Long roomId;

    /**
     * 上课教室
     */
    @TableField("room_name")
    private String roomName;

    /**
     * 上课主题
     */
    @TableField("class_theme")
    private String classTheme;

    /**
     * 上课日期 如:2020-02-05
     */
    @TableField("cla_date")
    private String claDate;

    /**
     * 上课开始时间
     */
    @TableField("start_time")
    private String startTime;

    /**
     * 上课结束时间
     */
    @TableField("end_time")
    private String endTime;

    /**
     * 实际上课时间
     */
    @TableField("real_cla_date")
    private String realClaDate;

    /**
     * 实际开始时间
     */
    @TableField("real_start_time")
    private String realStartTime;

    /**
     * 实际结束时间
     */
    @TableField("real_end_time")
    private String realEndTime;

    /**
     * 课时变更数量
     */
    @TableField("pay_hour")
    private BigDecimal payHour;

    /**
     * 总课时消耗
     */
    @TableField("pay_total_hour")
    private BigDecimal payTotalHour;

    /**
     * 总学费消耗
     */
    @TableField("pay_total_fee")
    private BigDecimal payTotalFee;

    /**
     * 来源 1:重复排课 2:未排课上课 3:单个新增
     */
    @TableField("source")
    private String source;

    /**
     * 状态 1:待上课 2:已上课
     */
    @TableField("status")
    private String status;

    /**
     * 应到人数
     */
    @TableField("need_attend_cnt")
    private Integer needAttendCnt;

    /**
     * 实到人数
     */
    @TableField("real_attend_cnt")
    private Integer realAttendCnt;

    /**
     * 到课人数
     */
    @TableField("at_class_cnt")
    private Integer atClassCnt;

    /**
     * 请假人数
     */
    @TableField("leave_cnt")
    private Integer leaveCnt;

    /**
     * 缺勤人数
     */
    @TableField("out_cnt")
    private Integer outCnt;

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

    @TableField(exist = false)
    private Long deptId;
    @TableField(exist = false)
    private String courseName;
    @TableField(exist = false)
    private String claName;
    @TableField(exist = false)
    private String deptName;

    public boolean checkUpdateParam() {
        if (StringUtils.isAnyEmpty(claDate, startTime, endTime)) {
            return false;
        }
        if (null == teacherId) {
            return false;
        }
        return true;
    }

    public boolean checkAddParam() {
        if(null == claId || null == teacherId) {
            return false;
        }
        if (StringUtils.isAnyEmpty(claDate, startTime, endTime)) {
            return false;
        }
        return true;
    }



}
