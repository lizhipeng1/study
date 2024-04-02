package com.study.core.config.security;

import cn.xluobo.business.sys.admin.repo.model.SysMenu;
import cn.xluobo.business.sys.admin.service.ISysMenuService;
import cn.xluobo.utils.ContextUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by zhangbaoyu on 18/1/9.
 */
@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private HashMap<String, Collection<ConfigAttribute>> map =null;

    public void loadResourceDefine(){
        ISysMenuService menuService = ContextUtils.getBean(ISysMenuService.class);
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<SysMenu> sysMenuList = menuService.selectAllPermissionUrl();
        for(SysMenu menu : sysMenuList) {
            array = new ArrayList<>();
            cfg = new SecurityConfig(menu.getPermissionMeta());
            //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            array.add(cfg);
            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            map.put(menu.getRequestUrl(), array);
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Collection<ConfigAttribute> configAttributeCollection = null;
        if(map ==null){
            loadResourceDefine();
        }

        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if(matcher.matches(request)) {
                if(null == configAttributeCollection){
                    configAttributeCollection= map.get(resUrl);
                }else{
                    configAttributeCollection.addAll(map.get(resUrl));
                }
            }
        }
        return configAttributeCollection;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
