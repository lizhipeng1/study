package com.study.business.tool.gen.domain.req;

import com.study.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchToolGenTable extends ReqPageBase implements Serializable {

    private String tableName;
    private String tableComment;
    private String beginTime;
    private String endTime;
}
