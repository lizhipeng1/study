package com.study.business.tool.gen.repo.mapper;

import com.study.business.tool.gen.repo.model.ToolGenTableColumn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 代码生成表字段 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-01-18
 */
public interface ToolGenTableColumnMapper extends BaseMapper<ToolGenTableColumn> {

    /**
     * 查询数据库 表列
     * @param tableName
     * @return
     */
    List<ToolGenTableColumn> selectDbTableColumns(String tableName);
}
