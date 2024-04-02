package com.study.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * 授权异常 返回自定义 格式结果(暂时未用)
 * AuthorizationServerConfiguration.configure
 * endpoints.exceptionTranslator(new Auth2ResponseExceptionTranslator());
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/15 09:56
 */
@Slf4j
public class Auth2ResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        log.warn("error:" + e.getMessage());
        return new ResponseEntity<>(new CustomOAuth2Exception(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

}
