package com.study.base.business.sc.base.domain.resp;

import lombok.Data;

/**
 * 前端 学校 select
 * @author ：zhangbaoyu
 * @date ：Created in 2020-04-27 19:30
 */
@Data
public class RespSchoolSelect {

    private Long schoolId;

    private String schoolName;

    private String provinceCode;

    private String cityCode;

    private String provinceName;

    private String cityName;

}
