package com.study.business.sys.admin.service.impl;

import com.study.business.sys.admin.domain.req.ReqPageSelect;
import com.study.business.sys.admin.repo.mapper.SysDictDataMapper;
import com.study.business.sys.admin.repo.model.SysDictData;
import com.study.business.sys.admin.service.ISysDictDataService;
import com.study.core.page.RespPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-15
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {

    @Override
    @Cacheable(value = "SYS_DICT_DATA", key = "#dictType", sync = true)
    public List<SysDictData> dictTypeDataList(String dictType) {
        if (StringUtils.isEmpty(dictType)) {
            return Lists.newArrayList();
        }
        QueryWrapper<SysDictData> qw = new QueryWrapper<>();
        qw.select("dict_label", "dict_value")
                .eq("dict_type", dictType);
        qw.orderByAsc("dict_sort");
        return this.list(qw);
    }

    @Override
    public RespPage<SysDictData> dictTypeDataList(String dictType, ReqPageSelect reqPageSelect) {
        if (StringUtils.isEmpty(dictType)) {
            return null;
        }
        RespPage<SysDictData> page = new RespPage<>(reqPageSelect.getPageNum(), reqPageSelect.getPageSize());
        QueryWrapper<SysDictData> qw = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(reqPageSelect.getSearch())) {
            qw.like("dict_label", reqPageSelect.getSearch());
        }
        if(StringUtils.isNotEmpty(reqPageSelect.getDefaultValue())) {
            qw.eq("dict_value", reqPageSelect.getDefaultValue());
        }
        qw.select("dict_label", "dict_value")
                .eq("dict_type", dictType);
        qw.orderByAsc("dict_sort");
        return this.page(page,qw);
    }

    @Override
    public List<SysDictData> dictTypeDataListByParentValue(String dictType, String parentValue) {
        if (StringUtils.isAnyEmpty(dictType, parentValue)) {
            return Lists.newArrayList();
        }
        QueryWrapper<SysDictData> qw = new QueryWrapper<>();
        qw.select("dict_label", "dict_value")
                .eq("dict_type", dictType)
                .eq("parent_value", parentValue);
        qw.orderByAsc("dict_sort");
        return this.list(qw);
    }

    @Override
    @CacheEvict(value = "SYS_DICT_DATA", key = "#dictType")
    public boolean removeCache(String dictType) {
        return true;
    }

    @Override
    public SysDictData getDictLabel(String dictType, String dictValue) {
        List<SysDictData> dictTypeDataList = this.dictTypeDataList(dictType);
        for (SysDictData sysDictData : dictTypeDataList) {
            if (dictValue.equals(sysDictData.getDictValue())) {
                return sysDictData;
            }
        }
        return new SysDictData();
    }
}
