package com.study.base.business.sc.course.domain.resp.time;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 上课记录明细
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/4 20:47
 */
@Data
public class RespClaTimeAttend {

    private Long attendId;

    private Long studentId;

    private String studentName;

    private String claName;

    private String chargeType;

    private Long teacherId;

    private String teacherName;

    private String attendStatus;

    private BigDecimal payHour;

    private BigDecimal payFee;

    private String memo;

    private String realClaDate;

    private String realStartTime;

    private String realEndTime;

    // 创建时间
    private Date createTime;
}
