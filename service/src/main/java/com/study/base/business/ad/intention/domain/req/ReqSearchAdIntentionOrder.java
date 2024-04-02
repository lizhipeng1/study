package com.study.base.business.ad.intention.domain.req;

import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchAdIntentionOrder extends ReqPageBase implements Serializable {
    private String orgName;
    private String contact;
    private String contactPhone;
    private String accountOpen;
    private String hadDeal;
}
