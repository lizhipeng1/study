package com.study.config.converter;

import org.springframework.context.annotation.Configuration;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/18 19:43
 */
@Configuration
public class CustomConverterConfig {
    /**
     * 解决前端js处理大数字丢失精度问题，将Long和BigInteger转换成string
     *
     * @return
     */
    /*@Bean
    @ConditionalOnMissingBean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        CustomMappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new CustomMappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        // 序列换成json时,将所有的long变成string 因为js中得数字类型不能包含所有的java long值
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        return jackson2HttpMessageConverter;
    }*/
}
