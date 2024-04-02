package com.study.core.config.tenant;

import cn.xluobo.business.enums.BusinessTableEnums;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyTenantHandler implements TenantHandler {

    private static final Map<String, BusinessTableEnums> tenantDbTable = new ConcurrentHashMap();

    static {
        for (BusinessTableEnums businessTableEnums : BusinessTableEnums.values()) {
            if(businessTableEnums.isTenant()){
                tenantDbTable.put(businessTableEnums.getTable(),businessTableEnums);
            }
        }

    }

    @Override
    public Expression getTenantId(boolean where) {
        return singleTenantIdCondition();
    }

    @Override
    public String getTenantIdColumn() {
        return "tenant_id";
    }

    @Override
    public boolean doTableFilter(String tableName) {
        return !tenantDbTable.containsKey(tableName);
    }

    private Expression singleTenantIdCondition() {
        return new LongValue(TenantContextHolder.getTenant());
    }
}
