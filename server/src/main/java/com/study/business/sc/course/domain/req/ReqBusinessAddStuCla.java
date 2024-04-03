package com.study.business.sc.course.domain.req;

import lombok.Data;

import java.io.Serializable;

/**
 * 学生新学课程
 * @author ：zhangbaoyu
 * @date ：Created in 2020-06-21 10:08
 */
@Data
public class ReqBusinessAddStuCla implements Serializable {

    private Long[] claIds;

    private Long studentId;
}
