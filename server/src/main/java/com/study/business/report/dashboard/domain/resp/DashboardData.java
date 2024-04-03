package com.study.business.report.dashboard.domain.resp;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/18 15:35
 */
@Data
@Builder
public class DashboardData {

    // 今日排课数量
    private Integer todayClaTimeCnt;

    // 今日已上课数量
    private Integer completeTodayClaTimeCnt;

    // 今日报名
    private Integer todayOrderCnt;

    // 本月报名
    private Integer thisMonthOrderCnt;

    // 今日上课实到课人次
    private Integer todayRealAttendCnt;

    // 今日上课应到人次
    private Integer todayNeedAttendCnt;

    // 今日应消课时
    private BigDecimal todayNeedCostHour;

    // 今日实消课时
    private BigDecimal todayRealCostHour;

    // 学员数量
    private Integer studentCnt;

    // 班级数量
    private Integer claCnt;

    // 课程数量
    private Integer courseCnt;

    // 欠费学员数量
    private Integer arrearsStudentCnt;

    // 按期缴费 即将过期人数
    private Integer dateWillExpireCnt;

    // 按课时缴费 即将过期 数量
    private Integer hourWillExpireCnt;

}
