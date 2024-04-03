package com.study.business.tool.gen.domain;

import com.study.business.tool.gen.repo.model.ToolGenTable;
import com.study.business.tool.gen.repo.model.ToolGenTableColumn;
import com.study.config.properties.GenCodeProperties;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.*;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-20 15:11
 */
@Data
public class GenCodeTableInfo {

    public GenCodeTableInfo(GenCodeProperties genCodeProperties) {
        this.genCodeProperties = genCodeProperties;
    }

    private GenCodeProperties genCodeProperties;

    private ToolGenTable genTable;

    /**
     * 逻辑删除字段
     */
    private String logicDeleteFieldName;

    /**
     * 时间
     */
    private String date = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");

    /**
     * 包路径
     */
    private Map packageInfo = new HashMap();

    /**
     * 类名
     */
    private Map fileNameInfo = new HashMap();

    /**
     * 实体类引用的包
     */
    private List<String> entityImportPackages = new ArrayList<>();

    /**
     * 主键类型
     */
    private String idType;

    /**
     * 包路径
     */
    private String basePackage;

    /**
     * 初始化
     *
     * @param genTable
     */
    public GenCodeTableInfo init(ToolGenTable genTable) {

        this.genTable = genTable;

        //实体类 引用包
        boolean hadLogicDeleteFiled = genTable.getColumns().parallelStream().anyMatch(tf -> tf.getColumnName().equals(genCodeProperties.getLogicDeleteField()));
        if (hadLogicDeleteFiled) {
            entityImportPackages.add(TableLogic.class.getCanonicalName());
        }

        entityImportPackages.add(Serializable.class.getCanonicalName());
        entityImportPackages.add(Date.class.getCanonicalName());
        entityImportPackages.add(IdType.class.getCanonicalName());
        entityImportPackages.add(TableId.class.getCanonicalName());
        entityImportPackages.add(TableField.class.getCanonicalName());
        entityImportPackages.add(Data.class.getCanonicalName());
        entityImportPackages.add(EqualsAndHashCode.class.getCanonicalName());
        entityImportPackages.add(Accessors.class.getCanonicalName());
        entityImportPackages.add(TableName.class.getCanonicalName());


        List<GenCodeTemplate> templateConfigs = genCodeProperties.getTemplateConfigs();
        for (GenCodeTemplate genCodeTemplate : templateConfigs) {

            // 文件名
            GenCodeTemplate.JavaType javaType = genCodeTemplate.getJavaType();
            if (null == javaType) {
                continue;
            }

            String outFileName = genCodeTemplate.getOutFileName();
            String outFileNameNoSuffix = outFileName.substring(0, outFileName.lastIndexOf("."));
            outFileNameNoSuffix = outFileNameNoSuffix.replace("{className}", genTable.getClassName());
            fileNameInfo.put(javaType.name(), outFileNameNoSuffix);
            fileNameInfo.put("FIRST_LOWER_"+javaType.name(), outFileNameNoSuffix.substring(0,1).toLowerCase()+outFileNameNoSuffix.substring(1));

            //包路径
            String packageOrDir = genCodeTemplate.getPackageOrDir();
            if (genCodeTemplate.isPrefixPkg()) {
                packageOrDir = genTable.getPackageName() + packageOrDir;
            }
            packageOrDir = packageOrDir
                    .replace("{moduleName}", genTable.getModuleName())
                    .replace("{businessName}", genTable.getBusinessName())
                    .replace("{childModuleName}", genTable.getChildModuleName());

            packageInfo.put(javaType.name(), packageOrDir);
        }


        //主键类型
        boolean isIncrement = genTable.getColumns().parallelStream().allMatch(tf -> tf.getIsIncrement().equals("1"));
        if (isIncrement) {
            this.idType = "ASSIGN_ID";
        } else {
            this.idType = "AUTO";
        }

        this.logicDeleteFieldName = genCodeProperties.getLogicDeleteField();
        return this;
    }

    /**
     * freemarker 参数
     *
     * @return
     */
    public Map generatorParam() {
        String entityClassName = genTable.getClassName();
        Map param = Maps.newHashMap();
        param.put("package", packageInfo);
        param.put("fileNameInfo", fileNameInfo);
        param.put("entityImportPackages", entityImportPackages);
        param.put("table", genTable);
        param.put("date", date);

        param.put("idType", idType);
        param.put("versionFieldName", genCodeProperties.getVersionFieldName());
        param.put("logicDeleteFieldName", logicDeleteFieldName);

        param.put("superMapperClassPackage", BaseMapper.class.getCanonicalName());
        param.put("superMapperClass", BaseMapper.class.getName());
        param.put("superServiceClassPackage", IService.class.getName());
        param.put("superServiceClass", IService.class.getName());
        param.put("superServiceImplClassPackage", ServiceImpl.class.getName());

        String superServiceImplClassName = ServiceImpl.class.getName();
        param.put("superServiceImplClass", superServiceImplClassName.substring(superServiceImplClassName.lastIndexOf(StringPool.DOT) + 1));

        //entity名称首字母小写
        String firstLowerEntityName = entityClassName.substring(0, 1).toLowerCase() + entityClassName.substring(1);
        param.put("firstLowerEntityName", firstLowerEntityName);

        // id
        String idStr1 = IdWorker.getIdStr();
        String idStr2 = IdWorker.getIdStr();
        String idStr3 = IdWorker.getIdStr();
        String idStr4 = IdWorker.getIdStr();
        String idStr5 = IdWorker.getIdStr();
        idStr1 = idStr1.substring(0,4) + DateTime.now().toString("yyyyMMddHHmm") + idStr1.substring(idStr1.length() - 3);
        idStr2 = idStr2.substring(0,4) + DateTime.now().toString("yyyyMMddHHmm") + idStr2.substring(idStr2.length() - 3);
        idStr3 = idStr3.substring(0,4) + DateTime.now().toString("yyyyMMddHHmm") + idStr3.substring(idStr3.length() - 3);
        idStr4 = idStr4.substring(0,4) + DateTime.now().toString("yyyyMMddHHmm") + idStr4.substring(idStr4.length() - 3);
        idStr5 = idStr5.substring(0,4) + DateTime.now().toString("yyyyMMddHHmm") + idStr5.substring(idStr5.length() - 3);
        param.put("menuId1", idStr1);
        param.put("menuId2", idStr2);
        param.put("menuId3", idStr3);
        param.put("menuId4", idStr4);
        param.put("menuId5", idStr5);

        //主键
        String pkColumn = "";
        String pkColumnName = "";
        for (ToolGenTableColumn column : genTable.getColumns()) {
            if ("1".equals(column.getIsPk())) {
                pkColumn = column.getJavaField();
                pkColumnName = column.getColumnName();
            }
        }
        if (StringUtils.isNotEmpty(pkColumn)) {
            String firstUpperPkColumn = pkColumn.substring(0,1).toUpperCase()+pkColumn.substring(1);
            //主键 首字母大写
            param.put("firstUpperPkColumn", firstUpperPkColumn);
            //主键
            param.put("pkColumn", pkColumn);
        }
        //主键 字段
        if (StringUtils.isNotEmpty(pkColumnName)) {
            param.put("pkColumnName", pkColumnName);
        }
        return param;
    }

}
