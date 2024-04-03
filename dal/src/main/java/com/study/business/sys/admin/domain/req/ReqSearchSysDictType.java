package com.study.business.sys.admin.domain.req;

import com.study.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchSysDictType extends ReqPageBase implements Serializable {

    private String dictName;

    private String dictType;

    private String status;

    private String beginTime;

    private String endTime;
}
