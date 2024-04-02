package com.study.base.config.enums;

import cn.xluobo.config.constants.CacheNameConstants;

import java.time.Duration;

/**
 * 缓存存在时间
 * @author ：zhangbaoyu
 * @date ：Created in 2020-02-11 14:01
 */
public enum CacheNameEnums {

    // 在线用户
    ONLINE_USER(),
    // 用户oauth2 refresh token
    USER_REFRESH_TOKEN(),
    // 用户权限缓存
    USER_PERMISSION_META(),
    // 字典缓存
    SYS_DICT_DATA(),
    // 图片验证码
    SYS_KAPTCHA(Duration.ofMinutes(2)),
    ;

    private String keyPrefix = CacheNameConstants.cachePrefix;

    private Duration timeToLive = CacheNameConstants.defaultTtlSecond;

    CacheNameEnums() {
    }

    CacheNameEnums(Duration timeToLive) {
        this.timeToLive = timeToLive;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public Duration getTimeToLive() {
        return timeToLive;
    }
}
