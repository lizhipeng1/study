package com.study.base.business.sc.course.domain.resp.time;

import cn.xluobo.business.sc.course.repo.model.ScClaTimeRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 排课信息
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/14 22:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespClaTimeRule extends ScClaTimeRule {

    private String courseName;
    private String claName;
    private String staffName;
    private String claDate;
    private String claTimeBegin;
    private String claTimeEnd;

    private String weekDayName;

}
