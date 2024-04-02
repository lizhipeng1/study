package com.study.base.business.sc.course.domain.resp.time;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 上课记录
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/2 20:33
 */
@Data
public class RespClaTime {

    private Long courseTimeId;

    private Long claId;

    private String claName;

    private Long courseId;

    private String courseName;

    private Long teacherId;

    private String staffName;

    /**
     * 状态 1:待上课 2:已上课
     */
    private String status;

    private String claDate;

    private String startTime;

    private String endTime;

    private String realClaDate;

    private String realStartTime;

    private String realEndTime;

    // 应到
    private Integer needAttendCnt;

    // 实到
    private Integer realAttendCnt;

    private Integer leaveCnt;

    private Integer outCnt;

    private BigDecimal payHour;

    private BigDecimal payTotalHour;

    private BigDecimal payTotalFee;

    private String roomName;

    private String classTheme;

    private String memo;

    // 最后变更时间  记录上课时间
    private Date lastUpdateTime;
    // 记录人
    private String lastUpdateUserName;
}
