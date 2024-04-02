package com.study.core.business.sc.student.domain.req;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/1 19:47
 */
@Data
public class ReqClaTimeAttendItem {

    // 学生
    private Long studentCourseId;

    // 1：到课 2：请假 3：缺勤
    private String attendStatus;

    // 备注
    private String memo;

    // 学生消耗课时
    private BigDecimal stuLoseHour;

}
