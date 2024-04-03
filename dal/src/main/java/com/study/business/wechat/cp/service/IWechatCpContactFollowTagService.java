package com.study.business.wechat.cp.service;

import com.study.business.wechat.cp.repo.model.WechatCpContactFollowTag;

/**
 * <p>
 * 员工给外部联系人添加的标签 服务类
 * </p>
 *
 * @author xluobo
 * @since 2024-01-25 10:46:17
 */
public interface IWechatCpContactFollowTagService extends com.baomidou.mybatisplus.extension.service.IService<WechatCpContactFollowTag> {

    /**
     * 删除标签
     * @param externalUserId
     * @param cpUserId
     */
    void deleteTag(String externalUserId, String cpUserId);

}
