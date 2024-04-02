package com.study.core.config.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

/**
 * 自定义的json转换器，匹配web api(以/web/开头的controller)中的接口方法的返回参数
 *
 * @author zhangby
 *
 */
public class CustomMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    private final static Logger logger = LoggerFactory.getLogger(CustomMappingJackson2HttpMessageConverter.class);

    /**
     * 判断该转换器是否能将请求内容转换成 Java 对象
     */
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        // 不需要反序列化
        return false;
    }

    /**
     * 判断该转换器是否能将请求内容转换成 Java 对象
     */
    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        // 不需要反序列化
        return false;
    }

    /**
     * 判断该转换器是否可以将 Java 对象转换成返回内容.
     * 匹配web api(形如/web/xxxx)中的接口方法的返回参数
     */
    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        if (super.canWrite(clazz, mediaType)) {
            ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null != ra) { // web请求
                HttpServletRequest request = ra.getRequest();
                String uri = request.getRequestURI();
                /*if (uri.startsWith("/api/")) {
                    return true;
                }*/
                return true;
            }
        }
        return false;
    }

}
