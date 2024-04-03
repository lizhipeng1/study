package com.study.utils;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.study.config.exception.ForceOfflineException;
import com.study.config.login.LoginTypeEnum;
import com.study.config.login.LoginUser;

/**
 * 小程序登录 session获取
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/15 15:03
 */
public class WechatMiniAppLoginUserUtil {

    public static WxMaJscode2SessionResult getSession() {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if(!LoginTypeEnum.WECHAT_MINI_APP.getLoginType().equals(loginUser.getLoginType())) {
            throw new ForceOfflineException("登录用户类型异常");
        }
        return (WxMaJscode2SessionResult)loginUser.getExtendOjb();
    }
}
