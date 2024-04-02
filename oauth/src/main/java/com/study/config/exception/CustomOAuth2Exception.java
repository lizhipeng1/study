package com.study.config.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/15 10:07
 */
public class CustomOAuth2Exception extends OAuth2Exception {



    public CustomOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public CustomOAuth2Exception(String msg) {
        super(msg);
    }
}
