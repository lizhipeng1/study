package com.study.base.config.constants;

import java.time.Duration;

/**
 * 缓存常量
 * @author ：zhangbaoyu
 * @date ：Created in 2020-02-12 17:46
 */
public class CacheNameConstants {

    public static final String cachePrefix = "Cache";
    public static final Duration defaultTtlSecond = Duration.parse("PT30M");
}
