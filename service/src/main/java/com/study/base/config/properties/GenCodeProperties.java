package com.study.base.config.properties;

import cn.xluobo.business.tool.gen.domain.GenCodeTemplate;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-19 14:19
 */
@ConfigurationProperties(prefix = "xluobo.tool.gen.config")
@Data
public class GenCodeProperties {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Autowired
    private MybatisPlusProperties mybatisPlusProperties;

    /**
     * 包路径
     */
    private String basePackage = "cn.xluobo.business";

    private DateType dateType = DateType.ONLY_DATE;

    /**
     * 乐观锁字段
     */
    private String versionFieldName = "version";

    /**
     * 字段类型转换
     */
    private ITypeConvert typeConvert;

    /**
     * 模板
     */
    private List<GenCodeTemplate> templateConfigs = Lists.newArrayList();

    private GlobalConfig globalConfig;

    /**
     * 获取字段转换
     *
     * @return
     */
    public ITypeConvert getColumnTypeConvert() {
        if (null == typeConvert) {
            this.typeConvert = getTypeConvert();
        }
        return this.typeConvert;
    }

    public GlobalConfig getGlobalConfig() {
        if (null == globalConfig) {
            GlobalConfig globalConfig = new GlobalConfig();
            globalConfig.setDateType(dateType);
            this.globalConfig = globalConfig;
        }
        return this.globalConfig;
    }

    /**
     * 获取逻辑删除 字段名称
     *
     * @return
     */
    public String getLogicDeleteField() {
        try {
            return mybatisPlusProperties.getGlobalConfig().getDbConfig().getLogicDeleteField();
        } catch (Exception e) {
            return "";
        }
    }

    private ITypeConvert getTypeConvert() {
        if (null == typeConvert) {
            switch (getDbType()) {
                case ORACLE:
                    typeConvert = new OracleTypeConvert();
                    break;
                case SQL_SERVER:
                    typeConvert = new SqlServerTypeConvert();
                    break;
                case POSTGRE_SQL:
                    typeConvert = new PostgreSqlTypeConvert();
                    break;
                case DB2:
                    typeConvert = new DB2TypeConvert();
                    break;
                case SQLITE:
                    typeConvert = new SqliteTypeConvert();
                    break;
                case DM:
                    typeConvert = new DmTypeConvert();
                    break;
                case MARIADB:
                    typeConvert = new MySqlTypeConvert();
                    break;
                case KINGBASE_ES:
                    typeConvert = new KingbaseESTypeConvert();
                    break;
                default:
                    // 默认 MYSQL
                    typeConvert = new MySqlTypeConvert();
                    break;
            }
        }
        return typeConvert;
    }

    private DbType getDbType() {
        String url = dataSourceProperties.getUrl();
        if (url.contains("mysql")) {
            return DbType.MYSQL;
        } else if (url.contains("oracle")) {
            return DbType.ORACLE;
        } else if (url.contains("postgresql")) {
            return DbType.POSTGRE_SQL;
        } else if (url.contains("sqlserver")) {
            return DbType.SQL_SERVER;
        } else if (url.contains("db2")) {
            return DbType.DB2;
        } else if (url.contains("mariadb")) {
            return DbType.MARIADB;
        } else if (url.contains("sqlite")) {
            return DbType.MARIADB;
        } else if (url.contains("h2")) {
            return DbType.H2;
        } else if (url.contains("kingbase") || url.contains("kingbase8")) {
            return DbType.KINGBASE_ES;
        } else {
            return null;
        }
    }
}
