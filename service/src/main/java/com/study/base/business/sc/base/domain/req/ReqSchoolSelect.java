package com.study.base.business.sc.base.domain.req;

import lombok.Data;

/**
 * 学校 select
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020-04-27 19:41
 */
@Data
public class ReqSchoolSelect {

    private String search;

    private Integer maxRecord = 50;

    private String schoolId;

}
