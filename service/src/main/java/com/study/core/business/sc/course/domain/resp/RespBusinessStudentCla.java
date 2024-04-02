package com.study.core.business.sc.course.domain.resp;

import cn.xluobo.business.sc.student.repo.model.ScStudent;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 学生班级
 * </p>
 *
 * @author zhangby
 * @since 2020-06-21 07:13:40
 */
@Data
public class RespBusinessStudentCla implements Serializable {

    /**
     * 全部可选班级
     */
    private List<RespBusinessCla> claInfoList;

    /**
     * 已有课程
     */
    private List<Long> claIds;

    /**
     * 班级信息
     */
    private ScStudent student;


    public RespBusinessStudentCla(List<RespBusinessCla> claInfoList, List<Long> claIds, ScStudent student) {
        this.claInfoList = claInfoList;
        this.claIds = claIds;
        this.student = student;
    }
}
