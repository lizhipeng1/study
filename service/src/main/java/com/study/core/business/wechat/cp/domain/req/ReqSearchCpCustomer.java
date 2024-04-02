package com.study.core.business.wechat.cp.domain.req;

import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

/**
 * 获取客户列表
 * @projectName: qyxt
 * @package: cn.xluobo.business.wechat.cp.dmain.req
 * @className: ReqSearchCustomer
 * @author: xluobo
 * @description: TODO
 * @date: 2024/1/25 10:20
 */
@Data
public class ReqSearchCpCustomer extends ReqPageBase {

    /**
     * 企业微信员工id
     */
    private String cpUserIds;

    /**
     * 用于分页查询的游标
     */
    private String cpCursor;
}
