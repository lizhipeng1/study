package com.study.core.log;

import com.study.core.log.enums.BusinessType;
import com.study.core.log.enums.ClientType;
import com.study.core.log.enums.OperateModule;

import java.lang.annotation.*;

/**
 * 操作日志记录注解
 * @author zhangby
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     */
    OperateModule module() default OperateModule.SYS;

    /**
     * 功能
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    ClientType clientType() default ClientType.PC;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

}
