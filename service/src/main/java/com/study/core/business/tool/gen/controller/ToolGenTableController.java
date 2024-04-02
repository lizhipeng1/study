package com.study.core.business.tool.gen.controller;


import cn.xluobo.business.tool.gen.domain.req.ReqSearchToolGenTable;
import cn.xluobo.business.tool.gen.repo.model.ToolGenTable;
import cn.xluobo.business.tool.gen.service.ToolGenTableService;
import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * <p>
 * 代码生成表 前端控制器
 * </p>
 *
 * @author zhangby
 * @since 2020-01-18
 */
@RestController
@RequestMapping("/api/tool/gen/table")
public class ToolGenTableController {

    @Autowired
    private ToolGenTableService sysGenTableService;

    /**
     * 列表
     *
     * @param reqSearchSysGenTable
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchToolGenTable reqSearchSysGenTable) {
        return sysGenTableService.searchList(reqSearchSysGenTable);
    }

    /**
     * 未导入的列表
     *
     * @param reqSearchSysGenTable
     * @return
     */
    @GetMapping("/list/tableNotIncludeList")
    public APIResponse tableNotIncludeList(ReqSearchToolGenTable reqSearchSysGenTable) {
        return sysGenTableService.tableNotIncludeList(reqSearchSysGenTable);
    }

    /**
     * 表配置详情
     *
     * @param tableId
     * @return
     */
    @GetMapping("/info/genConfigDetail/{tableId}")
    public APIResponse genConfigDetail(@PathVariable("tableId") Long tableId) {
        return sysGenTableService.genConfigDetail(tableId);
    }

    /**
     * 从系统导入
     *
     * @param tables
     * @return
     */
    @PostMapping("/add/importFormDb")
    public APIResponse importFormDb(String tables) {
        return sysGenTableService.importFormDb(tables);
    }

    /**
     * 修改
     *
     * @param sysGenTable
     * @return
     */
    @PutMapping("/update/updateGenTable")
    public APIResponse updateGenTable(@RequestBody ToolGenTable sysGenTable) {
        return sysGenTableService.updateGenConfig(sysGenTable);
    }

    /**
     * 删除
     *
     * @param tableIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{tableIds}")
    public APIResponse deleteById(@PathVariable("tableIds") Long[] tableIds) {
        return sysGenTableService.deleteById(tableIds);
    }

    /**
     * 生成导出代码
     *
     * @param tableIds
     * @return
     */
    @GetMapping("/export/genCode")
    public void genCode(String tableIds) throws IOException {
        sysGenTableService.genCode(tableIds);
    }
}
