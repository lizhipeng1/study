package com.study.core.config.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * redis中无此token
 * @author ：zhangbaoyu
 * @date ：Created in 2020-03-03 20:32
 */
public class NoTokenInRedisException extends OAuth2Exception {
    public NoTokenInRedisException(String msg, Throwable t) {
        super(msg, t);
    }

    public NoTokenInRedisException(String msg) {
        super(msg);
    }
}
