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



    @TableField(exist = false)
    private List<${fileNameInfo.ENTITY}> children;

    private static Map<Long, List<${fileNameInfo.ENTITY}>> subTreeMap = new HashMap<>();

    /**
     * 树转换
     * @param ${firstLowerEntityName}List
     */
    public void converterTree(List<${fileNameInfo.ENTITY}> ${firstLowerEntityName}List) {
        subTreeMap.clear();
        for (${fileNameInfo.ENTITY} menu : ${firstLowerEntityName}List) {
            List<${fileNameInfo.ENTITY}> subTreeList = subTreeMap.get(menu.getParentId());
            if (null == subTreeList) {
                subTreeList = new LinkedList<>();
                subTreeMap.put(menu.getParentId(),subTreeList);
            }
            subTreeList.add(menu);
        }

        this.children = tree${table.businessName?uncap_first}(this).getChildren();
        if(this.children.isEmpty()){
            this.children = ${firstLowerEntityName}List;
        }
    }

    /**
     * 深度优先 遍历树
     * @param ${firstLowerEntityName}
     * @return
     */
    private ${fileNameInfo.ENTITY} tree${table.businessName?uncap_first}(${fileNameInfo.ENTITY} ${firstLowerEntityName}) {
        Long deptId = ${firstLowerEntityName}.get${firstUpperPkColumn}();
        List<${fileNameInfo.ENTITY}> childMenuList = subTreeMap.get(deptId);
        if(null != childMenuList){
            List<${fileNameInfo.ENTITY}> childList = Lists.newLinkedList();
            for (${fileNameInfo.ENTITY} child${table.businessName?uncap_first} : childMenuList) {
                ${fileNameInfo.ENTITY} child = tree${table.businessName?uncap_first}(child${table.businessName?uncap_first});
                childList.add(child);
            }
            ${firstLowerEntityName}.setChildren(childList);
            return ${firstLowerEntityName};
        }else{
            ${firstLowerEntityName}.setChildren(Lists.newLinkedList());
            return ${firstLowerEntityName};
        }
    }
}
