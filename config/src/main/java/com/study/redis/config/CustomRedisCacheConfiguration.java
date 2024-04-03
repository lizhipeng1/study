package com.study.redis.config;

import com.study.config.enums.CacheNameEnums;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * redis 缓存配置
 *
 * @author protoss
 */
@Configuration
@EnableCaching
@Slf4j
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties({CacheProperties.class})
@ConditionalOnClass({CacheProperties.Redis.class, RedisCacheConfiguration.class})
public class CustomRedisCacheConfiguration extends CachingConfigurerSupport {

    @Bean
    public RedisSerializer<String> stringRedisSerializer() {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        return redisSerializer;
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory, RedisSerializer<String> redisSerializer) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(factory);

        template.setKeySerializer(redisSerializer);
        template.setHashKeySerializer(redisSerializer);
        template.setValueSerializer(getJackson2JsonRedisSerializer());
        template.setHashValueSerializer(getJackson2JsonRedisSerializer());
        template.setDefaultSerializer(redisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, CacheProperties cacheProperties) {
        Map<String, CacheProperties.Redis> customCache = Maps.newHashMap();
        for (CacheNameEnums cacheNameEnums : CacheNameEnums.values()) {
            CacheProperties.Redis redis = new CacheProperties.Redis();
            redis.setKeyPrefix(cacheNameEnums.getKeyPrefix());
            redis.setCacheNullValues(false);
            redis.setTimeToLive(cacheNameEnums.getTimeToLive());
            redis.setUseKeyPrefix(true);
            customCache.put(cacheNameEnums.name(), redis);
        }

        RedisCacheConfiguration defaultConfiguration = getDefaultRedisCacheConfiguration(cacheProperties);
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
                .fromCacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory)).cacheDefaults(defaultConfiguration);
        Map<String, RedisCacheConfiguration> map = Maps.newHashMap();
        Optional.ofNullable(customCache).ifPresent(cc -> {
            cc.forEach((key, cache) -> {
                RedisCacheConfiguration cfg = handleRedisCacheConfiguration(cache, defaultConfiguration);
                map.put(key, cfg);
            });
        });
        builder.withInitialCacheConfigurations(map);
        return builder.build();
    }

    private RedisCacheConfiguration getDefaultRedisCacheConfiguration(CacheProperties cacheProperties) {
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = getJackson2JsonRedisSerializer();
        config = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        config = handleRedisCacheConfiguration(redisProperties, config);
        return config;
    }

    private Jackson2JsonRedisSerializer getJackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    private RedisCacheConfiguration handleRedisCacheConfiguration(CacheProperties.Redis redisProperties, RedisCacheConfiguration config) {
        if (Objects.isNull(redisProperties)) {
            return config;
        }
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.computePrefixWith(cacheName -> redisProperties.getKeyPrefix() + ":" + cacheName + ":");
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }

    /**
     * 错误处理,简单打印日志
     *
     * @return
     */
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        return new SimpleCacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                log.error("cache : {} , key : {}", cache, key);
                log.error("handleCacheGetError", exception);
                super.handleCacheGetError(exception, cache, key);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key,
                                            Object value) {
                log.error("cache : {} , key : {} , value : {} ", cache, key,
                        value
                );
                log.error("handleCachePutError", exception);
                super.handleCachePutError(exception, cache, key, value);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                log.error("cache : {} , key : {}", cache, key);
                log.error("handleCacheEvictError", exception);
                super.handleCacheEvictError(exception, cache, key);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                log.error("cache : {} ", cache);
                log.error("handleCacheClearError", exception);
                super.handleCacheClearError(exception, cache);
            }
        };
    }
}
