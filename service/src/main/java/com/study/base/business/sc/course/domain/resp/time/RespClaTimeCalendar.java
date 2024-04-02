package com.study.base.business.sc.course.domain.resp.time;

import cn.xluobo.business.sc.course.repo.model.ScClaTime;
import lombok.Data;

/**
 * 排课信息（课表）
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/18 14:50
 */
@Data
public class RespClaTimeCalendar extends ScClaTime {

    private String claColor;

    private String staffName;

    // 上课星期
    private Integer weekDay;

    // 上课 开始小时
    private Integer startHour;

    private Integer studentCount;

}
