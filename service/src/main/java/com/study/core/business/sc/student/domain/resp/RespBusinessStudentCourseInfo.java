package com.study.core.business.sc.student.domain.resp;

import lombok.Builder;
import lombok.Data;

/**
 * 学生报读课程信息
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/2 15:48
 */
@Data
@Builder
public class RespBusinessStudentCourseInfo {

    // 联系电话
    private String contactPhone;
    // 联系电话明细
    private String contactPhoneDetail;
    // 报读课程名称列表
    private String courseNames;
    // 报读校区名称列表
    private String deptNames;
}
