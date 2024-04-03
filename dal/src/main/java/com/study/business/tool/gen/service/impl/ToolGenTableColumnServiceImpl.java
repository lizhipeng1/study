package com.study.business.tool.gen.service.impl;

import com.study.business.tool.gen.repo.model.ToolGenTableColumn;
import com.study.business.tool.gen.repo.mapper.ToolGenTableColumnMapper;
import com.study.business.tool.gen.service.IToolGenTableColumnService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 代码生成表字段 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-18
 */
@Service
public class ToolGenTableColumnServiceImpl extends ServiceImpl<ToolGenTableColumnMapper, ToolGenTableColumn> implements IToolGenTableColumnService {

    @Override
    public List<ToolGenTableColumn> selectByTableId(Long tableId) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("table_id",tableId);
        qw.orderByAsc("sort");
        return baseMapper.selectList(qw);
    }

    @Override
    public List<ToolGenTableColumn> selectDbTableColumns(String tableName) {
        return baseMapper.selectDbTableColumns(tableName);
    }

    @Override
    public int removeByTableIds(Long[] tableIds) {
        UpdateWrapper qw = new UpdateWrapper();
        qw.in("table_id",tableIds);
        return baseMapper.delete(qw);
    }
}
