package com.study.base.business.sys.admin.service.impl;

import cn.xluobo.business.sys.admin.repo.model.SysDictType;
import cn.xluobo.business.sys.admin.repo.mapper.SysDictTypeMapper;
import cn.xluobo.business.sys.admin.service.ISysDictTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-15
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    @Override
    public SysDictType selectByDictType(String dictType) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("dict_type",dictType);
        return baseMapper.selectOne(qw);
    }
}
