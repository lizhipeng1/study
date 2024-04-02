package com.study.base.business.sys.tag.service.impl;

import cn.xluobo.business.sys.tag.repo.model.SysTag;
import cn.xluobo.business.sys.tag.repo.mapper.SysTagMapper;
import cn.xluobo.business.sys.tag.service.ISysTagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-03 03:08:13
 */
@Service
public class SysTagServiceImpl extends ServiceImpl<SysTagMapper, SysTag> implements ISysTagService {

    @Override
    public void autoCreateTag(String[] tags, String tagType, String tenantId, String userId) {
        for (String tag : tags) {
            QueryWrapper qw = new QueryWrapper();
            qw.eq("tenant_id", tenantId);
            qw.eq("tag_name", tag);
            qw.eq("tag_type", tagType);
            int count = this.count(qw);
            if (count == 0 ){
                SysTag sysTag = new SysTag();
                sysTag.setTagName(tag);
                sysTag.setTagType(tagType);
                sysTag.setCreateUser(userId);
                this.save(sysTag);
            }
        }
    }
}
