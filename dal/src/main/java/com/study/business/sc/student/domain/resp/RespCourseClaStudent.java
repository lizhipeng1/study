package com.study.business.sc.student.domain.resp;

import com.study.business.sc.course.enums.CourseChargeTypeEnum;
import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 班级学员
 * 用于 班级下学生列表
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/28 16:51
 */
@Data
public class RespCourseClaStudent {

    private Long studentCourseId;

    private Long claId;

    private Long courseId;

    // 报读校区
    private String deptName;

    private Long studentId;

    private String studentName;

    private String sex;

    private String phone;

    // 联系人信息
    private String contactInfo;

    private String chargeType;

    private BigDecimal totalDay;
    private BigDecimal totalHour;
    private BigDecimal balanceHour;
    // 过期课时
    private BigDecimal expireHour;

    // 即将生效的 总天数
    private BigDecimal comingEffectDay;

    /**
     * 按时间收费 当期 到期时间
     * 当前生效周期到期时间
     */
    private Date endDate;

    // 状态
    private String status;

    // 首次报名时间
    private Date firstSignTime;

    // 最后一次续费时间
    private Date lastSignTime;

    /**
     * 获取剩余天数
     * @return
     */
    public BigDecimal getBalanceDays() {
        try {
            if(CourseChargeTypeEnum.DATE.getChargeType().equals(chargeType)) {
                if(null == this.endDate) {
                    // 无生效周期
                    return comingEffectDay;
                } else {
                    // 有生效周期
                    Period period = new Period(DateTime.now(), new DateTime(this.endDate), PeriodType.days());
                    return new BigDecimal(period.getDays() + 1).add(comingEffectDay);
                }
            } else {
                return BigDecimal.ZERO;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }

    /**
     * 按时间收费 当前时间 是否在缴费周期内(是否有效)
     * @return
     */
    public boolean isEffect() {
        try{
            if(CourseChargeTypeEnum.DATE.getChargeType().equals(chargeType)) {
                if(null == this.endDate) {
                    // 未生效
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
