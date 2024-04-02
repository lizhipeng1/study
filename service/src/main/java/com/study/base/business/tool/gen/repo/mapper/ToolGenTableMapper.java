package com.study.base.business.tool.gen.repo.mapper;

import cn.xluobo.business.tool.gen.domain.DbTableParams;
import cn.xluobo.business.tool.gen.repo.model.ToolGenTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 代码生成表 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-01-18
 */
public interface ToolGenTableMapper extends BaseMapper<ToolGenTable> {

    /**
     * 获取所有待导入 表中的 表
     *
     * @param dbTableParams
     * @return
     */
    List<ToolGenTable> selectDbTableNotGenList(@Param("page")Page page, @Param("dbTableParams")DbTableParams dbTableParams);

    /**
     * 获取表信息
     * @param tables
     * @return
     */
    List<ToolGenTable> selectDbTableListByTableName(@Param("tables")List<String> tables);
}
