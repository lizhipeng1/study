package com.study.utils;

import com.study.config.exception.ForceOfflineException;
import com.study.config.login.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by zhangbaoyu on 18/1/22.
 */
public class LoginUserUtil {

    /**
     * 获取登录用户
     * @return
     */
    public static LoginUser getLoginUser(){
        Object user = getUser();
        if(user instanceof LoginUser){
            return (LoginUser) user;
        }else{
            throw new ForceOfflineException("无法获取登录用户");
        }
    }

    /**
     * 获取登录用户编号
     * @return
     */
    public static String getLoginUserId(){
        LoginUser loginUser = getLoginUser();
        return loginUser.getUserId();
    }

    /**
     * 获取登录用户当前租户
     * @return
     */
    public static String getNowTenant(){
        LoginUser loginUser = getLoginUser();
        return loginUser.getNowTenantId();
    }

    private static Object getUser(){
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
