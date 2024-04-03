package com.study.business.sc.course.repo.model;

import java.math.BigDecimal;

import com.study.business.sc.course.enums.ChargeDateUnitEnum;
import com.study.business.sc.course.enums.CourseChargeTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程收费模式
 * </p>
 *
 * @author zhangby
 * @since 2020-07-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_course_charge")
public class ScCourseCharge implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收费编号
     */
    @TableId(value = "charge_id")
    private Long chargeId;

    /**
     * 课程编号
     */
    @TableField("course_id")
    private Long courseId;

    /**
     * 校区 -1为全部校区
     */
    @TableField("depart_id")
    private Long departId;

    /**
     * 收费模式 hour:课时 date:时间 cycle:期
     */
    @TableField("charge_type")
    private String chargeType;

    /**
     * 课时数量
     */
    @TableField("count")
    private BigDecimal count;

    /**
     * 总价
     */
    @TableField("total_fee")
    private BigDecimal totalFee;

    /**
     * 时间周期 天/月/季/年
     */
    @TableField("date_unit")
    private String dateUnit;

    public String getChargeName() {
        if (CourseChargeTypeEnum.HOUR.getChargeType().equals(chargeType)) {
            return "按课时 " + count.toString() + "课时=" + totalFee +"元";
        } else if (CourseChargeTypeEnum.DATE.getChargeType().equals(chargeType)) {
            return "按时间 " + count.toString() + ChargeDateUnitEnum.getDateUnitLabel(dateUnit) + "=" + totalFee+"元";
        } else if (CourseChargeTypeEnum.CYCLE.getChargeType().equals(chargeType)) {
            return "按周期 " + count.toString() + "课时=" + totalFee +"元";
        }
        return "";
    }

}
