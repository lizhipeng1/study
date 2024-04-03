package com.study.business.sc.course.domain.resp.cla;

import com.study.business.sc.course.domain.resp.RespBusinessChooseCourseCharge;
import com.study.business.sc.course.repo.model.ScCourse;
import com.study.business.sc.course.repo.model.ScCourseCla;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 班级详情
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/29 18:36
 */
@Data
@Builder
public class RespClaAllDetailInfo {

    // 班级信息
    private ScCourseCla courseCla;

    // 课程信息
    private ScCourse course;

    // 收费方式
    private List<RespBusinessChooseCourseCharge> courseChargeList;

    // 上课时间
    private List<String> claTimeList;
}
