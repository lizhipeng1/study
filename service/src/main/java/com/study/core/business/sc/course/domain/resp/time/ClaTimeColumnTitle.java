package com.study.core.business.sc.course.domain.resp.time;

import lombok.Data;

/**
 * 按时间 课表 title
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/23 17:16
 */
@Data
public class ClaTimeColumnTitle {

    // 星期
    private String weekName;

    // 日期
    private String day;

    // 排课数量
    private int count;

}
