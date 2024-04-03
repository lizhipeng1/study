package com.study.business.sc.course.domain.resp.time;

import lombok.Data;

import java.util.List;

/**
 * 按时间 课表
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/23 17:15
 */
@Data
public class RespBusinessClaTimeCalendar {

    // 标题
    private List<ClaTimeColumnTitle> columnTitles;

    // 行数据
    private List<ClaTimeContainer> claTimeContainer;

}
