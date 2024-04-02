package com.study.core.config.tenant;

import cn.xluobo.utils.LoginUserUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 租户切换
 */
public class TenantContextHolder {
    private static ThreadLocal<String> tenantThreadLocal = new ThreadLocal<String>();

    /**
     * 注意：手动调用代码切换租户，处理完毕需调用remove()
     *     由于getTenant()方法中。tenantThreadLocal 优先级 高于 用户当前的租户
     *     如不手动remove() , getTenant()时，有时候是从tenantThreadLocal取，有时候从用户中取。(因为处理线程不一致)
     * @param scheme
     */
    public static final void setTenant(String scheme) {
        tenantThreadLocal.set(scheme);
    }

    public static final String getTenant() {
        String tenantId = tenantThreadLocal.get();
        if (StringUtils.isEmpty(tenantId)) {
            return LoginUserUtil.getNowTenant();
        }
        return tenantId;
    }

    public static final void remove() {
        tenantThreadLocal.remove();
    }
}
