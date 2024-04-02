package com.study.base.business.sc.student.domain.resp;

import cn.xluobo.business.sc.course.enums.CourseChargeTypeEnum;
import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.math.BigDecimal;

/**
 * 学生 报读 课程列表
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/8 22:02
 */
@Data
public class RespStudentCourse {

    private Long studentCourseId;

    private String courseName;

    private String chargeType;

    private BigDecimal totalDay;

    private BigDecimal totalHour;

    private BigDecimal balanceHour;

    // 过期课时
    private BigDecimal expireHour;

    private BigDecimal totalFee;

    private String status;

    private Long studentId;

    private String studentName;

    private String deptName;

    private String claName;

    private String orderDetail;

    // 即将生效的 总天数
    private BigDecimal comingEffectDay;

    /**
     * 按时间收费 当期 到期时间
     * 当前生效周期到期时间
     */
    private String endDate;

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
