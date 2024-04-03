package com.study.business.sc.course.repo.model;

import com.study.business.sc.course.repo.enums.ClaTimeRepeatTypeEnums;
import com.study.business.sc.course.repo.enums.ClaTimeRuleTypeEnums;
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
 * 上课时间配置规则
 * </p>
 *
 * @author zhangby
 * @since 2020-09-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_cla_time_rule")
public class ScClaTimeRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规则id
     */
    @TableId(value = "rule_id", type = IdType.ASSIGN_ID)
    private Long ruleId;

    /**
     * 班级
     */
    @TableField("cla_id")
    private Long claId;

    /**
     * 规则类型 1重复排课 2单次排课
     */
    @TableField("rule_type")
    private String ruleType;

    /**
     * 开始日期
     */
    @TableField("begin_date")
    private String beginDate;

    /**
     * 结束日期
     */
    @TableField("end_date")
    private String endDate;

    /**
     * 单次排课 日期
     */
    @TableField("once_date")
    private String onceDate;

    /**
     * 重复方式 1每周重复 2隔天重复 3隔周重复
     */
    @TableField("repeat_type")
    private String repeatType;

    /**
     * 上课星期 周几上课
     */
    @TableField("week_day")
    private String weekDay;

    /**
     * 是否过滤节假日 1过滤 0不过滤
     */
    @TableField("filter_holiday")
    private boolean filterHoliday;

    /**
     * 上课时间
     */
    @TableField("start_time")
    private String startTime;

    /**
     * 下课时间
     */
    @TableField("end_time")
    private String endTime;

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

    /**
     * 单次排课 选择的上课日期
     */
    @TableField(exist = false)
    private String[] chooseDate;

    /**
     * 所属校区
     */
    @TableField(exist = false)
    private Long deptId;

    public boolean checkParam() {
        if (null == claId || null == teacherId) {
            return false;
        }
        if (StringUtils.isAnyEmpty(ruleType)) {
            return false;
        }
        if (ClaTimeRuleTypeEnums.ONCE_RULE.getRuleType().equals(ruleType)) {
            if (StringUtils.isAnyEmpty(startTime, endTime)) {
                return false;
            } else if (null == chooseDate || chooseDate.length == 0) {
                return false;
            }
        }
        if ("1".equals(ruleType) && StringUtils.isAnyEmpty(repeatType)) {
            return false;
        }
        if (ClaTimeRepeatTypeEnums.EVERY_WEEK.getRepeatType().equals(repeatType)
                || ClaTimeRepeatTypeEnums.EVERY_SECOND_WEEK.getRepeatType().equals(repeatType)) {
            if (StringUtils.isAnyEmpty(weekDay, startTime, endTime, beginDate, endDate)) {
                return false;
            }
        } else {
            if (StringUtils.isAnyEmpty(startTime, endTime)) {
                return false;
            }
        }
        return true;
    }


}
