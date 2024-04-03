package com.study.business.tool.gen.service;

import com.study.business.tool.gen.repo.model.ToolGenTableColumn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 代码生成表字段 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-18
 */
public interface IToolGenTableColumnService extends IService<ToolGenTableColumn> {

    /**
     * 根据表id查询所有字段
     * @param tableId
     * @return
     */
    List<ToolGenTableColumn> selectByTableId(Long tableId);

    /**
     * 从information_schema.columns 中查询字段定义
     * @param tableName
     * @return
     */
    List<ToolGenTableColumn> selectDbTableColumns(String tableName);

    /**
     * 根据表id删除表所有字段
     * @param tableIds
     * @return
     */
    int removeByTableIds(Long[] tableIds);

}
