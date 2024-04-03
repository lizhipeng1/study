package com.study.business.wechat.cp.domain.req;

import com.study.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchWechatCpUser extends ReqPageBase implements Serializable {
    private String status;
}
