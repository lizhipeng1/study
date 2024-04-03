package ${package.ENTITY};

<#list entityImportPackages as pkg>
import ${pkg};
</#list>

/**
 * <p>
 * ${table.tableComment!}
 * </p>
 *
 * @author ${table.codeAuthor}
 * @since ${date}
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("${table.tableName}")
public class ${fileNameInfo.ENTITY} implements Serializable {

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.columns as field>
    <#if field.isPk=="1">
        <#assign keyPropertyName="${field.javaField}"/>
    </#if>

    <#if field.columnComment!?length gt 0>
    /**
     * ${field.columnComment}
     */
    </#if>
    <#if field.isPk=="1">
        <#-- 主键 -->
        <#if field.isPk=="1">
    @TableId(value = "${field.columnName}", type = IdType.AUTO)
        <#elseif idType??>
    @TableId(value = "${field.columnName}", type = IdType.${idType})
        </#if>
        <#-- 普通字段 -->
    <#else>
    @TableField("${field.columnName}")
    </#if>
    <#-- 乐观锁注解 -->
    <#if (versionFieldName!"") == field.columnName>
    @Version
    </#if>
    <#-- 逻辑删除注解 -->
    <#if (logicDeleteFieldName!"") == field.columnName>
    @TableLogic
    </#if>
    private ${field.javaType} ${field.javaField};
</#list>
<#------------  END 字段循环遍历  ---------->
}
