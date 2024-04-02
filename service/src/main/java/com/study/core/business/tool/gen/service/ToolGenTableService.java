package com.study.core.business.tool.gen.service;

import cn.xluobo.business.tool.gen.domain.DbTableParams;
import cn.xluobo.business.tool.gen.domain.GenCodeTableInfo;
import cn.xluobo.business.tool.gen.domain.GenCodeTemplate;
import cn.xluobo.business.tool.gen.domain.req.ReqSearchToolGenTable;
import cn.xluobo.business.tool.gen.repo.model.ToolGenTable;
import cn.xluobo.business.tool.gen.repo.model.ToolGenTableColumn;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.config.properties.GenCodeProperties;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.api.ApiResEnums;
import cn.xluobo.core.file.FileUtils;
import cn.xluobo.core.page.RespPage;
import cn.xluobo.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 生成代码服务
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-19 10:34
 */
@Service
@Transactional
public class ToolGenTableService {

    @Autowired
    private IToolGenTableService sysGenTableService;
    @Autowired
    private IToolGenTableColumnService sysGenTableColumnService;
    @Autowired
    private GenCodeProperties genCodeProperties;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 查询树
     *
     * @param reqSearchSysGenTable
     * @return
     */
    public APIResponse searchList(ReqSearchToolGenTable reqSearchSysGenTable) {
        QueryWrapper qw = new QueryWrapper();
        if (StringUtils.isNotEmpty(reqSearchSysGenTable.getTableName())) {
            qw.like("genTable_name", reqSearchSysGenTable.getTableName());
        }
        if (StringUtils.isNotEmpty(reqSearchSysGenTable.getTableComment())) {
            qw.like("contact_name", reqSearchSysGenTable.getTableComment());
        }
        if (StringUtils.isNoneEmpty(reqSearchSysGenTable.getBeginTime(), reqSearchSysGenTable.getEndTime())) {
            qw.between("end_time", reqSearchSysGenTable.getBeginTime() + " 00:00:00", reqSearchSysGenTable.getEndTime() + " 23:59:59");
        }
        qw.orderByDesc("create_time");
        RespPage<ToolGenTable> page = new RespPage(reqSearchSysGenTable.getPageNum(), reqSearchSysGenTable.getPageSize());
        RespPage<ToolGenTable> listPage = sysGenTableService.page(page, qw);
        return APIResponse.toAPIResponse(listPage);
    }

    /**
     * 未导入的 表
     *
     * @param reqSearchSysGenTable
     * @return
     */

    public APIResponse tableNotIncludeList(ReqSearchToolGenTable reqSearchSysGenTable) {
        RespPage<ToolGenTable> page = new RespPage(reqSearchSysGenTable.getPageNum(), reqSearchSysGenTable.getPageSize());
        DbTableParams dbTableParams = new DbTableParams(reqSearchSysGenTable.getTableName(), reqSearchSysGenTable.getTableComment());
        List<ToolGenTable> sysGenTables = sysGenTableService.selectDbTableNotGenList(page, dbTableParams);
        page.setRows(sysGenTables);
        return APIResponse.toAPIResponse(page);
    }

    /**
     * 生成配置详情
     *
     * @param tableId
     * @return
     */
    public APIResponse genConfigDetail(Long tableId) {
        if (null == tableId) {
            return APIResponse.toAPIResponse(null);
        }
        ToolGenTable detailInfo = sysGenTableService.getById(tableId);
        List<ToolGenTableColumn> columnList = sysGenTableColumnService.selectByTableId(tableId);
        Map resultMap = Maps.newHashMap();
        resultMap.put("tableInfo", detailInfo);
        resultMap.put("tableColumnList", columnList);
        return APIResponse.toAPIResponse(resultMap);
    }

    /**
     * 从db中导入表 表字段
     *
     * @param tables
     * @return
     */
    public APIResponse importFormDb(String tables) {
        GlobalConfig globalConfig = genCodeProperties.getGlobalConfig();
        ITypeConvert columnTypeConvert = genCodeProperties.getColumnTypeConvert();

        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (StringUtils.isEmpty(tables)) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        List<String> tableList = Arrays.asList(tables.split(","));
        List<ToolGenTable> sysGenTables = sysGenTableService.selectDbTableListByTableName(tableList);
        for (ToolGenTable sysGenTable : sysGenTables) {
            sysGenTable.setPackageName(genCodeProperties.getBasePackage());
            String tableName = sysGenTable.getTableName();

            String className = NamingStrategy.underlineToCamel(tableName);
            String[] tableNameSplit_ = tableName.split("_");

            sysGenTable.setClassName(NamingStrategy.capitalFirst(className));
            if (tableNameSplit_.length >= 1) {
                sysGenTable.setModuleName(tableNameSplit_[0]);
            }
            if (tableNameSplit_.length >= 2) {
                sysGenTable.setBusinessName(tableNameSplit_[1]);
            }
            sysGenTableService.save(sysGenTable);

            List<ToolGenTableColumn> sysGenTableColumns = sysGenTableColumnService.selectDbTableColumns(sysGenTable.getTableName());
            for (ToolGenTableColumn sysGenTableColumn : sysGenTableColumns) {
                String javaType = columnTypeConvert.processTypeConvert(globalConfig, sysGenTableColumn.getColumnType()).getType();
                String javaFiled = NamingStrategy.underlineToCamel(sysGenTableColumn.getColumnName());

                sysGenTableColumn.setTableId(sysGenTable.getTableId());
                sysGenTableColumn.setJavaType(javaType);
                sysGenTableColumn.setJavaField(javaFiled);
                sysGenTableColumn.dealIsInsert().dealIsEdit().dealIsList().dealIsQuery().dealQueryType().dealHtmlType();
                sysGenTableColumnService.save(sysGenTableColumn);
            }
        }
        return APIResponse.toOkResponse();
    }

    /**
     * 更新配置
     *
     * @param sysGenTable
     * @return
     */
    public APIResponse updateGenConfig(ToolGenTable sysGenTable) {
        if (null == sysGenTable.getTableId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        sysGenTable.setLastUpdateUser(loginUser.getUserId());
        sysGenTable.setLastUpdateTime(new Date());
        boolean updateGenTable = sysGenTableService.updateById(sysGenTable);
        List<ToolGenTableColumn> tableColumns = sysGenTable.getColumns();
        boolean updateBatchById = sysGenTableColumnService.updateBatchById(tableColumns);
        return APIResponse.toOkResponse();
    }

    /**
     * 删除
     *
     * @param tableIds
     * @return
     */
    public APIResponse deleteById(Long[] tableIds) {
        if (null == tableIds || tableIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        boolean deleteGenTable = sysGenTableService.removeByIds(Arrays.asList(tableIds));
        sysGenTableColumnService.removeByTableIds(tableIds);
        return APIResponse.toOkResponse();
    }

    /**
     * 生成
     *
     * @param tableIds
     */
    public void genCode(String tableIds) throws IOException {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        //获取生成代码  模板
        for (String tableId : tableIds.split(",")) {
            generatorFreemarkerCode(Long.valueOf(tableId), zip);
        }
        IOUtils.closeQuietly(zip);
        byte[] data = outputStream.toByteArray();
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * 生成代码内容
     *
     * @param tableId
     * @return
     */
    private void generatorFreemarkerCode(Long tableId, ZipOutputStream zip) {
        ToolGenTable table = sysGenTableService.getById(tableId);
        List<ToolGenTableColumn> tableColumns = sysGenTableColumnService.selectByTableId(tableId);
        table.setColumns(tableColumns);

        GenCodeTableInfo genCodeTableInfo = new GenCodeTableInfo(genCodeProperties);
        Map param = genCodeTableInfo.init(table).generatorParam();

        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        String tplCategory = table.getTplCategory();


        try {
            String projectPath = System.getProperty("user.dir");
            StringBuffer sb = new StringBuffer();
            sb.append("/generator/").append(tplCategory).append("/");
            String prePath = sb.toString();
            List<GenCodeTemplate> templateConfigs = genCodeProperties.getTemplateConfigs();
            for (GenCodeTemplate genCodeTemplate : templateConfigs) {
                //模板路径
                String templatePath = prePath + genCodeTemplate.getTemplateFileName();
                Template template = configuration.getTemplate(templatePath);

                //文件内容
                String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, param);

                //文件路径+名称
                String packageOrDir = genCodeTemplate.getPackageOrDir();
                if (genCodeTemplate.isPrefixPkg()) {
                    packageOrDir = table.getPackageName() + packageOrDir;
                }
                String baseDir = packageOrDir
                        .replace("{moduleName}", table.getModuleName())
                        .replace("{businessName}", table.getBusinessName())
                        .replace("{childModuleName}", table.getChildModuleName())
                        .replaceAll("\\.", "/");
                String fileName = genCodeTemplate.getOutFileName().replace("{className}", table.getClassName());

                String packagePath = baseDir + "/" + fileName;


                //输出文件
                String file = projectPath + genCodeTemplate.getOutPath() + packagePath;
                FileUtils.createFile(file);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                template.process(param, new OutputStreamWriter(fileOutputStream, ConstVal.UTF8));


                zip.putNextEntry(new ZipEntry(packagePath));
                IOUtils.write(htmlContent, zip, "UTF-8");
                zip.flush();
                zip.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
