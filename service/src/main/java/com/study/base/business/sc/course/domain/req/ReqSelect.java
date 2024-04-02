package com.study.base.business.sc.course.domain.req;

import lombok.Data;

/**
 * 前端 select
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020-04-27 19:41
 */
@Data
public class ReqSelect {

    private String search;

    private Integer maxRecord = 50;

    // 是否可分页
    private boolean pageable;

    private long pageNum = 1;

    private long pageSize = 10;

    public long getPageSize() {
        if (pageable) {
            return pageSize;
        } else {
            return maxRecord;
        }
    }
}
