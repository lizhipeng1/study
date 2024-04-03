package com.study.business.wechat.cp.service;

import com.study.business.wechat.cp.repo.model.WechatCpContactFollow;

/**
 * <p>
 * 外部联系人对应大员工信息 服务类
 * </p>
 *
 * @author xluobo
 * @since 2024-01-25 05:53:25
 */
public interface IWechatCpContactFollowService extends com.baomidou.mybatisplus.extension.service.IService<WechatCpContactFollow> {

    /**
     * 删除标签
     * @param externalUserId
     * @param cpUserId
     */
    void deleteContactFollow(String externalUserId, String cpUserId);

}
