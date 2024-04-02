package com.study.base.business.wechat.cp.service.impl;

import cn.xluobo.business.wechat.cp.repo.model.WechatCpContactFollowTag;
import cn.xluobo.business.wechat.cp.repo.mapper.WechatCpContactFollowTagMapper;
import cn.xluobo.business.wechat.cp.service.IWechatCpContactFollowTagService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工给外部联系人添加的标签 服务实现类
 * </p>
 *
 * @author xluobo
 * @since 2024-01-25 10:46:17
 */
@Service
public class WechatCpContactFollowTagServiceImpl extends ServiceImpl<WechatCpContactFollowTagMapper, WechatCpContactFollowTag> implements IWechatCpContactFollowTagService {

    @Override
    public void deleteTag(String externalUserId, String cpUserId) {
        UpdateWrapper<WechatCpContactFollowTag> uw = new UpdateWrapper<>();
        uw.eq("external_user_id", externalUserId)
                .eq("cp_user_id", cpUserId);

        this.remove(uw);
    }
}
