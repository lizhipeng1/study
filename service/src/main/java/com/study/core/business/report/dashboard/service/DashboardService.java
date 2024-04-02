package com.study.core.business.report.dashboard.service;

import cn.xluobo.business.report.dashboard.domain.resp.DashboardData;
import cn.xluobo.business.sc.course.domain.req.time.ReqClaTimeCount;
import cn.xluobo.business.sc.course.repo.enums.ClaTimeAttendStatusEnums;
import cn.xluobo.business.sc.course.service.IScClaTimeAttendService;
import cn.xluobo.business.sc.course.service.IScClaTimeService;
import cn.xluobo.business.sc.course.service.IScCourseClaService;
import cn.xluobo.business.sc.course.service.IScCourseService;
import cn.xluobo.business.sc.order.enums.OrderTypeEnum;
import cn.xluobo.business.sc.order.service.IScOrderService;
import cn.xluobo.business.sc.student.service.IScStudentCourseService;
import cn.xluobo.business.sc.student.service.IScStudentService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/18 15:34
 */
@Service
@Transactional
public class DashboardService {

    @Autowired
    private IScCourseClaService claService;
    @Autowired
    private IScCourseService courseService;
    @Autowired
    private IScStudentService studentService;
    @Autowired
    private IScClaTimeAttendService attendService;
    @Autowired
    private IScOrderService orderService;
    @Autowired
    private IScClaTimeService claTimeService;
    @Autowired
    private IScStudentCourseService studentCourseService;

    /**
     * 首页各项数据
     *
     * @return
     */
    public DashboardData dashboardData() {
        String todayDate = DateTime.now().toString("yyyy-MM-dd");
        String thisMonthBegin = DateTime.now().withDayOfMonth(1).toString("yyyy-MM-dd");
        String thisMonthEnd = DateTime.now().plusMonths(1).withDayOfMonth(1).minusDays(1).toString("yyyy-MM-dd");
        int claCnt = claService.count();
        int courseCnt = courseService.count();
        int studentCnt = studentService.count();

        ReqClaTimeCount reqClaTimeCount = ReqClaTimeCount.builder().beginDate(todayDate).endDate(todayDate).hadBegin(null).build();
        Integer todayClaTimeCnt = claTimeService.claTimeCount(reqClaTimeCount);

        reqClaTimeCount.setHadBegin(true);
        Integer completeTodayClaTimeCnt = claTimeService.claTimeCount(reqClaTimeCount);

        Integer todayOrderCnt = orderService.orderCount(todayDate, todayDate, OrderTypeEnum.SIGN_UP.getOrderType());
        Integer thisMonthOrderCnt = orderService.orderCount(thisMonthBegin, thisMonthEnd, OrderTypeEnum.SIGN_UP.getOrderType());
        Integer todayNeedAttendCnt = attendService.getNeedAttendCount(todayDate, todayDate);
        Integer todayRealAttendCnt = attendService.getAttendCount(todayDate, todayDate,
                new String[]{ClaTimeAttendStatusEnums.AT_CLASS.getAttendStatus()});
        BigDecimal todayNeedCostHour = attendService.getAttendCostHour(todayDate, todayDate, true);
        BigDecimal todayRealCostHour = attendService.getAttendCostHour(todayDate, todayDate, false);
        Integer arrearsStudentCnt = orderService.arrearsStudentCount();
        Integer dateWillExpireCnt = studentCourseService.getWillExpireDateCount(10);
        Integer hourWillExpireCnt = studentCourseService.getWillExpireHourCount(5);
        return DashboardData.builder()
                .todayClaTimeCnt(todayClaTimeCnt)
                .completeTodayClaTimeCnt(completeTodayClaTimeCnt)
                .todayOrderCnt(todayOrderCnt)
                .thisMonthOrderCnt(thisMonthOrderCnt)
                .todayRealAttendCnt(todayRealAttendCnt)
                .todayNeedAttendCnt(todayNeedAttendCnt)
                .todayNeedCostHour(todayNeedCostHour)
                .todayRealCostHour(todayRealCostHour)
                .studentCnt(studentCnt)
                .claCnt(claCnt)
                .courseCnt(courseCnt)
                .arrearsStudentCnt(arrearsStudentCnt)
                .dateWillExpireCnt(dateWillExpireCnt)
                .hourWillExpireCnt(hourWillExpireCnt)
                .build();
    }

}
