package com.study.base.business.sc.student.domain.req;

import cn.xluobo.business.sc.student.repo.model.ScStudent;
import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchScStudent extends ReqPageBase implements Serializable {

    private String schoolId;

    private String studentName;

    private String sex;

    private String phone;
}
