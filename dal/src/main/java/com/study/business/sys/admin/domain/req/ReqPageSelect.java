package com.study.business.sys.admin.domain.req;

import com.study.core.page.ReqPageBase;
import lombok.Data;

/**
 * 异步查询select
 * @author ：zhangbaoyu
 * @date ：Created in 2021/1/16 15:37
 */
@Data
public class ReqPageSelect extends ReqPageBase {
    // 默认值
    private String defaultValue;

    // 查询
    private String search;
}
