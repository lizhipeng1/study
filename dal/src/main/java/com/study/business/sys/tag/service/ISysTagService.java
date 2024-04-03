package com.study.business.sys.tag.service;

import com.study.business.sys.tag.repo.model.SysTag;

/**
 * <p>
 * 标签 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-03 03:08:13
 */
public interface ISysTagService extends com.baomidou.mybatisplus.extension.service.IService<SysTag> {

    /**
     * 校验是否存在 如果不存在则创建
     *
     * @param tags
     * @param tagType
     * @param tenantId
     * @param userId
     */
    void autoCreateTag(String[] tags, String tagType, String tenantId, String userId);

}
