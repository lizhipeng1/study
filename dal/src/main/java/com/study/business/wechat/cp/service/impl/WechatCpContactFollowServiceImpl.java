package com.study.business.wechat.cp.service.impl;

import com.study.business.wechat.cp.repo.model.WechatCpContactFollow;
import com.study.business.wechat.cp.repo.mapper.WechatCpContactFollowMapper;
import com.study.business.wechat.cp.service.IWechatCpContactFollowService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 外部联系人对应大员工信息 服务实现类
 * </p>
 *
 * @author xluobo
 * @since 2024-01-25 05:53:25
 */
@Service
public class WechatCpContactFollowServiceImpl extends ServiceImpl<WechatCpContactFollowMapper, WechatCpContactFollow> implements IWechatCpContactFollowService {

    @Override
    public void deleteContactFollow(String externalUserId, String cpUserId) {
        UpdateWrapper<WechatCpContactFollow> uw = new UpdateWrapper<>();
        uw.eq("external_user_id", externalUserId).eq("cp_user_id", cpUserId);
        this.remove(uw);
    }
}
