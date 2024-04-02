package com.study.core.config.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 强制下线后 异常
 * @author ：zhangbaoyu
 * @date ：Created in 2020-03-03 20:32
 */
public class ForceOfflineException extends OAuth2Exception {
    public ForceOfflineException(String msg, Throwable t) {
        super(msg, t);
    }

    public ForceOfflineException(String msg) {
        super(msg);
    }
}
