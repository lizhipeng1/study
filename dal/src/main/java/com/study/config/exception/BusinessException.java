package com.study.config.exception;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/4 16:36
 */
public class BusinessException extends RuntimeException{

    public BusinessException(String message) {
        super(message);
    }
}
