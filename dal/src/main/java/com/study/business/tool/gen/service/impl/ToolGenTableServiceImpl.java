package com.study.business.tool.gen.service.impl;

import com.study.business.tool.gen.domain.DbTableParams;
import com.study.business.tool.gen.repo.model.ToolGenTable;
import com.study.business.tool.gen.repo.mapper.ToolGenTableMapper;
import com.study.business.tool.gen.service.IToolGenTableService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 代码生成表 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-18
 */
@Service
public class ToolGenTableServiceImpl extends ServiceImpl<ToolGenTableMapper, ToolGenTable> implements IToolGenTableService {

    @Override
    public List<ToolGenTable> selectDbTableNotGenList(Page page, DbTableParams dbTableParams) {
        return baseMapper.selectDbTableNotGenList(page, dbTableParams);
    }

    @Override
    public List<ToolGenTable> selectDbTableListByTableName(List<String> tables) {
        return baseMapper.selectDbTableListByTableName(tables);
    }
}
