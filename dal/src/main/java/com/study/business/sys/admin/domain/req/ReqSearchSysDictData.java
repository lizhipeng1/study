package com.study.business.sys.admin.domain.req;

import com.study.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchSysDictData extends ReqPageBase implements Serializable {

    private String dictLabel;

    private String dictType;

    private String status;
}
