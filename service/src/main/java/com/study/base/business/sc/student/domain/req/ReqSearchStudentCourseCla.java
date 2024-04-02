package com.study.base.business.sc.student.domain.req;

import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询学生课程、班级
 * @author ：zhangbaoyu
 * @date ：Created in 2020-06-20 12:30
 */
@Data
public class ReqSearchStudentCourseCla extends ReqPageBase implements Serializable {

    private Long claId;

    // 所属校区
    private Long departId;

    // 所属课程
    private Long courseId;

    private Boolean unChooseCla;

    // 是否生效
    private Boolean effect;
}
