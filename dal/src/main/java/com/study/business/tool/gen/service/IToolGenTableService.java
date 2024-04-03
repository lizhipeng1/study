package com.study.business.tool.gen.service;

import com.study.business.tool.gen.domain.DbTableParams;
import com.study.business.tool.gen.repo.model.ToolGenTable;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 代码生成表 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-18
 */
public interface IToolGenTableService extends IService<ToolGenTable> {

    /**
     * 未生成的表
     * @param dbTableParams
     * @return
     */
    List<ToolGenTable> selectDbTableNotGenList(Page page, DbTableParams dbTableParams);

    List<ToolGenTable> selectDbTableListByTableName(List<String> tables);
}
