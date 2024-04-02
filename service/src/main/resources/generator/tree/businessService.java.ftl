package ${package.BUSINESS_SERVICE};

import ${package.REQ_ENTITY}.${fileNameInfo.REQ_ENTITY};
import ${package.ENTITY}.${fileNameInfo.ENTITY};
import cn.xluobo.business.sys.admin.domain.resp.RespTreeSelect;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.api.ApiResEnums;
import cn.xluobo.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class ${fileNameInfo.BUSINESS_SERVICE} {

    @Autowired
    private I${fileNameInfo.ENTITY}Service ${firstLowerEntityName}Service;

    /**
     * 查询树
     *
     * @param ${fileNameInfo.FIRST_LOWER_REQ_ENTITY}
     * @return
     */
    public APIResponse searchList(${fileNameInfo.REQ_ENTITY} ${fileNameInfo.FIRST_LOWER_REQ_ENTITY}) {
        QueryWrapper qw = new QueryWrapper();
<#list table.columns as field>
    <#if field.isQuery?? && field.isQuery=="1">
        if(StringUtils.isNotEmpty(${fileNameInfo.FIRST_LOWER_REQ_ENTITY}.get${field.javaField?cap_first}())){
        <#if field.queryType?? && field.queryType=="EQ">
            qw.eq("${field.columnName}",${fileNameInfo.FIRST_LOWER_REQ_ENTITY}.get${field.javaField?cap_first}());
        <#elseif field.queryType?? && field.queryType=="LIKE">
            qw.like("${field.columnName}",${fileNameInfo.FIRST_LOWER_REQ_ENTITY}.get${field.javaField?cap_first}());
        <#else>
            qw.eq("${field.columnName}",${fileNameInfo.FIRST_LOWER_REQ_ENTITY}.get${field.javaField?cap_first}());
        </#if>
        }
    </#if>
</#list>
        List<${fileNameInfo.ENTITY}> list = ${firstLowerEntityName}Service.list(qw);
        ${fileNameInfo.ENTITY} ${firstLowerEntityName} = new ${fileNameInfo.ENTITY}();
        ${firstLowerEntityName}.set${firstUpperPkColumn}(-1L);
        ${firstLowerEntityName}.converterTree(list);
        List<${fileNameInfo.ENTITY}> respPage = ${firstLowerEntityName}.getChildren();
        return APIResponse.toAPIResponse(respPage);
    }

    /**
     * 前端select
     *
     * @return
     */
    public APIResponse treeSelect() {
        ${fileNameInfo.ENTITY} ${firstLowerEntityName} = new ${fileNameInfo.ENTITY}();
        ${firstLowerEntityName}.setInUse("1");
        ${firstLowerEntityName}.setDeleteFlag("0");
        QueryWrapper<${fileNameInfo.ENTITY}> qw = new QueryWrapper(${firstLowerEntityName});
        qw.orderByAsc("sort");
        List<${fileNameInfo.ENTITY}> list = ${firstLowerEntityName}Service.list(qw);
        ${fileNameInfo.ENTITY} tree = new ${fileNameInfo.ENTITY}();
        tree.set${firstUpperPkColumn}(-1L);
        tree.converterTree(list);
        List<${fileNameInfo.ENTITY}> children = tree.getChildren();
        List<RespTreeSelect> respTreeSelects =
                children.stream().map(RespTreeSelect::new).collect(Collectors.toList());
        return APIResponse.toAPIResponse(respTreeSelects);
    }

    /**
     * 详情
     *
     * @param ${pkColumn}
     * @return
     */
    public APIResponse detailById(Long ${pkColumn}) {
        if (null == ${pkColumn}) {
            return APIResponse.toAPIResponse(null);
        }
        ${fileNameInfo.ENTITY} detailInfo = ${firstLowerEntityName}Service.getById(${pkColumn});
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param ${firstLowerEntityName}
     * @return
     */
    public APIResponse add${table.className}(${fileNameInfo.ENTITY} ${firstLowerEntityName}) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        ${firstLowerEntityName}.setCreateUser(loginUser.getUserId());
        ${fileNameInfo.ENTITY} parent${table.className} = ${firstLowerEntityName}Service.getById(${firstLowerEntityName}.getParentId());
        if (null != parentSysRole) {
            if ("0".equals(parent${table.className}.getInUse())) {
                return APIResponse.toExceptionResponse("父级已停用,不允许新增");
            }
            ${firstLowerEntityName}.setAncestors(parent${table.className}.getAncestors() + "," + ${firstLowerEntityName}.getParentId());
        } else {
            ${firstLowerEntityName}.setAncestors(String.valueOf(${firstLowerEntityName}.getParentId()));
        }
        boolean add${table.className} = ${firstLowerEntityName}Service.save(${firstLowerEntityName});
        if (add${table.className}) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param ${firstLowerEntityName}
     * @return
     */
    public APIResponse update${table.className}(${fileNameInfo.ENTITY} ${firstLowerEntityName}) {
        if (null == ${firstLowerEntityName}.get${firstUpperPkColumn}()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        if (${firstLowerEntityName}.getParentId().compareTo(${firstLowerEntityName}.get${firstUpperPkColumn}()) == 0) {
            return APIResponse.toExceptionResponse("父级不能是自己");
        }
        //修改子元素 关系
        ${fileNameInfo.ENTITY} newParent${table.className} = ${firstLowerEntityName}Service.getById(${firstLowerEntityName}.getParentId());
        ${fileNameInfo.ENTITY} old${table.className} = ${firstLowerEntityName}Service.getById(${firstLowerEntityName}.get${firstUpperPkColumn}());
        if (null != newParent${table.className} && null != old${table.className}) {
            String newAncestors = newParent${table.className}.getAncestors() + "," + newParent${table.className}.get${firstUpperPkColumn}();
            String oldAncestors = old${table.className}.getAncestors();
            ${firstLowerEntityName}.setAncestors(newAncestors);
            ${firstLowerEntityName}Service.update${table.className}Children(${firstLowerEntityName}.get${firstUpperPkColumn}(), newAncestors, oldAncestors);
        }

        LoginUser loginUser = LoginUserUtil.getLoginUser();
        ${firstLowerEntityName}.setLastUpdateUser(loginUser.getUserId());
        ${firstLowerEntityName}.setLastUpdateTime(new Date());
        boolean update${table.className} = ${firstLowerEntityName}Service.updateById(${firstLowerEntityName});
        if (update${table.className}) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param ${pkColumn}s
     * @return
     */
    public APIResponse deleteById(Long[] ${pkColumn}s) {
        if (null == ${pkColumn}s || ${pkColumn}s.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }

        for (Long ${pkColumn} : ${pkColumn}s) {
            if (${firstLowerEntityName}Service.hadChild(${pkColumn})) {
                return APIResponse.toExceptionResponse("存在子节点不能删除");
            }
            if (${firstLowerEntityName}Service.hadUser(${pkColumn})) {
                return APIResponse.toExceptionResponse("存在用户不能删除");
            }
        }

        boolean delete${table.className} = ${firstLowerEntityName}Service.removeByIds(Arrays.asList(${pkColumn}s));
        if (delete${table.className}) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }
}
