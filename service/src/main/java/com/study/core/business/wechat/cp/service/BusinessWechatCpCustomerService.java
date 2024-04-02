package com.study.core.business.wechat.cp.service;

import cn.xluobo.business.wechat.cp.domain.req.ReqSearchCpCustomer;
import cn.xluobo.core.api.APIResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @projectName: qyxt
 * @package: cn.xluobo.business.wechat.cp.service
 * @className: BusinessWechatCpCustomerService
 * @author: xluobo
 * @description: TODO
 * @date: 2024/1/25 08:15
 */
@Service
@Transactional
public class BusinessWechatCpCustomerService {

    @Autowired
    private ThirdWechatCpContactService thirdWechatCpContactService;

    /**
     * 查询客户列表
     * @param reqSearchCpCustomer
     * @return
     */
    public APIResponse searchList(ReqSearchCpCustomer reqSearchCpCustomer) {
        String cpUserIds = reqSearchCpCustomer.getCpUserIds();
        if (StringUtils.isEmpty(cpUserIds)) {
            return APIResponse.toExceptionResponse("请选择员工");
        }
        String[] userIds = cpUserIds.split(",");
        long pageSize = reqSearchCpCustomer.getPageSize();

//        wechatCpCustomerService.getContactDetailBatch(userIds, reqSearchCpCustomer.getCpCursor(), );
        return null;
    }
}
