package com.study.business.wechat.cp.service;

import com.study.business.wechat.cp.domain.cp.ReqCpUserDetail;
import com.study.business.wechat.cp.domain.cp.ReqCpUserList;
import com.study.config.wechat.cp.WechatCpConfiguration;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpUserService;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.bean.user.WxCpDeptUserResult;
import org.springframework.stereotype.Service;

/**
 * 企业微信通讯录管理
 * @projectName: qyxt
 * @package: com.study.business.wechat.cp.service
 * @className: WechatCpUserService
 * @author: xluobo
 * @description: 通讯录管理
 * @date: 2024/1/25 18:31
 */
@Service
public class ThirdWechatCpUserService {

    /**
     * 获取企业成员的userid与对应的部门ID列表，预计于2022年8月8号发布。若需要获取其他字段，参见「适配建议」。
     * @param reqCpUserList
     * @return
     * @throws WxErrorException
     */
    public WxCpDeptUserResult getUserListId(ReqCpUserList reqCpUserList) throws WxErrorException {
        WxCpService cpService = WechatCpConfiguration.getCpService(reqCpUserList.getCorpId(), reqCpUserList.getAddressBookAgentId());
        WxCpUserService userService = cpService.getUserService();
        return userService.getUserListId(reqCpUserList.getCursor(), reqCpUserList.getLimit());
    }

    /**
     * 读取成员详情
     * @param reqCpUserDetail
     * @return
     * @throws WxErrorException
     */
    public WxCpUser getById(ReqCpUserDetail reqCpUserDetail) throws WxErrorException {
        WxCpService cpService = WechatCpConfiguration.getCpService(reqCpUserDetail.getCorpId(), reqCpUserDetail.getAgentId());
        WxCpUserService userService = cpService.getUserService();
        return userService.getById(reqCpUserDetail.getUserId());
    }

}
