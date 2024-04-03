package com.study.business.wechat.user.service;

import com.study.business.sys.admin.repo.model.SysUser;
import com.study.business.wechat.user.repo.model.WechatUserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 微信用户信息 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-10-15
 */
public interface IWechatUserInfoService extends IService<WechatUserInfo> {

    WechatUserInfo getWechatUser(String appId,String openId);

    /**
     * 获取绑定的系统用户
     * @param appId
     * @param openId
     * @return
     */
    SysUser getSysUser(String appId,String openId);

}
