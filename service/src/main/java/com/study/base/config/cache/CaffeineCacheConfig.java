package com.study.base.config.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Caffeine 缓存配置
 */
@Configuration
public class CaffeineCacheConfig {

    /**
     * 节假日缓存
     * @return
     */
    @Bean(name = "holidayCache")
    public Cache<String, Map<String, String>> holidayCache(){
        Cache<String, Map<String, String>> cache = Caffeine.newBuilder()
                .expireAfterWrite(24 * 30, TimeUnit.HOURS)
                .maximumSize(100)
                .build();
        return cache;
    }

}
