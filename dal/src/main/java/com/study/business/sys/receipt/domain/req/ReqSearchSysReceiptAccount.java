package com.study.business.sys.receipt.domain.req;

import com.study.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchSysReceiptAccount extends ReqPageBase implements Serializable {
    private String accountName;
    private String memo;
}
