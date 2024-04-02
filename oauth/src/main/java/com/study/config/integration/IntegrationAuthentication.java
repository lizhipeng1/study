package com.study.config.integration;

import lombok.Data;

import java.util.Map;

/**
 * 授权参数
 * @author ：zhangbaoyu
 * @date ：Created in 2020/7/7 09:36
 */
@Data
public class IntegrationAuthentication {

    /**
     * 授权类型
     */
    private String authType;

    /**
     * 用户名
     */
    private String username;

    /**
     * 参数
     */
    private Map<String,String[]> authParameters;

    public String getAuthParameter(String parameter){
        String[] values = this.authParameters.get(parameter);
        if(values != null && values.length > 0){
            return values[0];
        }
        return null;
    }
}
