package com.study.base.business.sc.base.domain.req;

import cn.xluobo.business.sc.base.repo.model.ScSchool;
import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchScSchool extends ReqPageBase implements Serializable {
    private String provinceCode;
    private String cityCode;
    private String schoolName;
}
