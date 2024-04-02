package com.study.core.business.sc.course.domain.req;

import lombok.Data;

import java.io.Serializable;

/**
 * 班级新增学生
 * @author ：zhangbaoyu
 * @date ：Created in 2020-04-28 19:08
 */
@Data
public class ReqBusinessAddClaStu implements Serializable {

    private Long claId;

    private Long[] studentIds;
}
