package com.study.config.exception;

import com.study.core.api.APIBaseResponse;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-03-03 20:31
 */
@ControllerAdvice
@Slf4j
public class CustomExceptionAdvice {

    /**
     * 强制下线后  使用异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ForceOfflineException.class)
    public APIBaseResponse errorHandler(ForceOfflineException ex) {
        log.error("errorHandler",ex);
        return new APIBaseResponse("0001",ex.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public APIBaseResponse businessException(Exception ex) {
        log.error("businessException",ex);
        return APIResponse.toExceptionResponse(ex.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public APIBaseResponse commonResponseException(Exception ex) {
        log.error("commonResponseException",ex);
        return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
    }
}
