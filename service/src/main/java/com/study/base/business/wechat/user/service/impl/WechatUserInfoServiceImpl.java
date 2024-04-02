package com.study.base.business.wechat.user.service.impl;

import cn.xluobo.business.sys.admin.repo.model.SysUser;
import cn.xluobo.business.sys.admin.service.ISysUserService;
import cn.xluobo.business.wechat.user.repo.model.WechatUserInfo;
import cn.xluobo.business.wechat.user.repo.mapper.WechatUserInfoMapper;
import cn.xluobo.business.wechat.user.service.IWechatUserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 微信用户信息 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-10-15
 */
@Service
public class WechatUserInfoServiceImpl extends ServiceImpl<WechatUserInfoMapper, WechatUserInfo> implements IWechatUserInfoService {

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public WechatUserInfo getWechatUser(String appId, String openId) {
        QueryWrapper<WechatUserInfo> qw = new QueryWrapper<>();
        qw.eq("app_id", appId);
        qw.eq("open_id", openId);
        return this.getOne(qw);
    }

    @Override
    public SysUser getSysUser(String appId, String openId) {
        WechatUserInfo wechatUser = this.getWechatUser(appId, openId);
        if( null == wechatUser) {
            return null;
        }
        SysUser sysUser = sysUserService.getByBindByWechatUserId(wechatUser.getWechatUserId());
        return sysUser;
    }
}
