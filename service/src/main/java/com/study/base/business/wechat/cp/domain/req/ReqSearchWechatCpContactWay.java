package com.study.base.business.wechat.cp.domain.req;

import cn.xluobo.business.wechat.cp.repo.model.WechatCpContactWay;
import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchWechatCpContactWay extends ReqPageBase implements Serializable {
    private String scene;
}
