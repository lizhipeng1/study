package com.study.business.sc.course.domain.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 班级学生
 * </p>
 *
 * @author zhangby
 * @since 2020-04-27 07:13:40
 */
@Data
public class RespBusinessStudent implements Serializable {

    private Long studentId;

    private String studentName;

    public RespBusinessStudent(Long studentId) {
        this.studentId = studentId;
    }

    public RespBusinessStudent(Long studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }
}
