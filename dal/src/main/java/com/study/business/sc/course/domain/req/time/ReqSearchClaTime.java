package com.study.business.sc.course.domain.req.time;

import com.study.core.page.ReqPageBase;
import lombok.Data;
import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/18 14:45
 */
@Data
public class ReqSearchClaTime extends ReqPageBase implements Serializable {

    // 排课 编号
    private Long courseTimeId;

    // 课程
    private Long courseId;

    // 校区
    private Long deptId;

    // 当前登录用户
    private String userId;

    // 班级
    private Long claId;

    // 学生
    private Long studentId;

    private Long teacherId;

    // 开始时间
    private String beginDate;

    // 结束时间
    private String endDate;

    /**
     * 与今天相隔几天
     * 如果设置了，将重置 beginDate、endDate
     */
    private Integer diffNowDay;

    /**
     * 是否已上课
     * true 已上课
     * false 未上课
     */
    private Boolean attended;

    /**
     * 排序类型
     * claTimeAttend
     */
    private String orderByType;

    public void setDiffNowDay(Integer diffNowDay) {
        this.diffNowDay = diffNowDay;
        if(null != diffNowDay) {
            DateTime now = DateTime.now();
            this.beginDate = now.minusDays(diffNowDay).toString("yyyy-MM-dd");
            this.endDate = now.plusDays(diffNowDay).toString("yyyy-MM-dd");
        }
    }
}
