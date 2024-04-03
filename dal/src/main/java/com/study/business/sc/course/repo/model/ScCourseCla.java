package com.study.business.sc.course.repo.model;

import java.math.BigDecimal;

import com.study.core.api.APIBaseResponse;
import com.baomidou.mybatisplus.annotation.TableName;

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
 * 课程班级信息
 * </p>
 *
 * @author zhangby
 * @since 2020-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_course_cla")
public class ScCourseCla implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级id
     */
    @TableId(value = "cla_id")
    private Long claId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 课程id
     */
    @TableField("course_id")
    private Long courseId;

    /**
     * 开班校区
     */
    @TableField("depart_id")
    private Long departId;

    /**
     * 班主任id
     */
    @TableField("staff_id")
    private Long staffId;

    /**
     * 班级名称
     */
    @TableField("cla_name")
    private String claName;

    /**
     * 班级颜色
     */
    @TableField("cla_color")
    private String claColor;

    /**
     * 满班人数
     */
    @TableField("capacity")
    private Integer capacity;

    /**
     * 招生状态 1开放 2满班后停止 0停止
     */
    @TableField("recruit_status")
    private String recruitStatus;

    /**
     * 每次上课学生扣除课时
     */
    @TableField("every_stu_lose_hour")
    private BigDecimal everyStuLoseHour;

    /**
     * 每次上课教师获得课时
     */
    @TableField("every_tea_get_hour")
    private BigDecimal everyTeaGetHour;

    /**
     * 开班日期
     */
    @TableField("open_date")
    private String openDate;

    /**
     * 结班日期
     */
    @TableField("close_date")
    private String closeDate;

    /**
     * 备注
     */
    @TableField("memo")
    private String memo;

    /**
     * 删除标志（1删除 0在用）
     */
    @TableField("delete_flag")
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
    private String deptName;

    @TableField(exist = false)
    private String teacherName;

    // 当前班级人数
    @TableField(exist = false)
    private Integer studentCnt;

    /**
     * 校验参数
     * @return
     */
    public APIBaseResponse checkParam(){
        if(StringUtils.isAnyEmpty(claName,claColor,recruitStatus,openDate)){
            return APIBaseResponse.fail("请求参数错误,请全部填写后,重新提交");
        }
        if(null == courseId || null == departId || null == staffId || null == capacity || null == everyStuLoseHour || null == everyTeaGetHour){
            return APIBaseResponse.fail("请求参数错误,请全部填写后,重新提交");
        }
        return APIBaseResponse.success();
    }

}
