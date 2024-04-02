package com.study.core.business.sc.course.domain.resp.time;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 具体排课数据
 * table 单行
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/23 18:59
 */
@Data
public class ClaTimeContainer {

    // 上课时间
    private String time;

    // 排课信息
    private Map<Integer,List<ClaTimeCalendarItem>> claTimeWeekDayMap;

}
