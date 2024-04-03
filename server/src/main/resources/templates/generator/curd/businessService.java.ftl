package ${package.BUSINESS_SERVICE};

import ${package.REQ_ENTITY}.${fileNameInfo.REQ_ENTITY};
import ${package.ENTITY}.${fileNameInfo.ENTITY};
import com.study.business.sys.admin.domain.resp.RespTreeSelect;
import com.study.config.login.LoginUser;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.study.core.page.RespPage;

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
     * 查询
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
        RespPage<${fileNameInfo.ENTITY}> page = new RespPage(${fileNameInfo.FIRST_LOWER_REQ_ENTITY}.getPageNum(), ${fileNameInfo.FIRST_LOWER_REQ_ENTITY}.getPageSize());
        RespPage<${fileNameInfo.ENTITY}> listPage = ${firstLowerEntityName}Service.page(page, qw);
        return APIResponse.toAPIResponse(listPage);
    }

    /**
     * 详情
     *
     * @param ${pkColumn}
     * @return
     */
    public APIResponse detailById(String ${pkColumn}) {
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
    public APIResponse deleteById(String[] ${pkColumn}s) {
        if (null == ${pkColumn}s || ${pkColumn}s.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        boolean delete${table.className} = ${firstLowerEntityName}Service.removeByIds(Arrays.asList(${pkColumn}s));
        if (delete${table.className}) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }
}
