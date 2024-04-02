package ${package.REQ_ENTITY};

import ${package.ENTITY}.${fileNameInfo.ENTITY};
import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearch${fileNameInfo.ENTITY} extends ReqPageBase implements Serializable {
<#list table.columns as field>
 <#if field.isQuery?? && field.isQuery=="1">
    private String ${field.javaField};
 </#if>
</#list>
}
