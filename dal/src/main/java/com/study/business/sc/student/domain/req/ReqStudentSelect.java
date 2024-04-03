package com.study.business.sc.student.domain.req;

import com.study.core.page.ReqPageBase;
import lombok.Data;

/**
 * 学生 select
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020-06-19 08:41
 */
@Data
public class ReqStudentSelect extends ReqPageBase {

    private String search;

    private String schoolId;

}
