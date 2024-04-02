package com.study.page;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页请求
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-15 21:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReqPageBase extends ReqDeptCondition{

    private long pageNum = 1;

    private long pageSize = 10;
}
