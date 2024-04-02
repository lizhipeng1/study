package com.study.service;

import cn.xluobo.config.enums.CacheNameEnums;
import cn.xluobo.config.login.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-02-13 13:01
 */
@Service
public class CacheService {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 落入redis
     *
     * @param userId
     * @param jti
     * @param loginUser
     */
    public void putOnlineUser(String userId, String jti, LoginUser loginUser) {
        loginUser.setPassword("");
        redisTemplate.opsForValue().set(CacheNameEnums.ONLINE_USER.name() + ":" + userId + ":" + jti, loginUser);
    }

    /**
     * 用户信息
     *
     * @param userId
     * @return
     */
    public LoginUser getOnlineUser(String userId, String jti) {
        Object loginUserObj = redisTemplate.opsForValue().get(CacheNameEnums.ONLINE_USER.name() + ":" + userId + ":" + jti);
        if (null == loginUserObj) {
            return null;
        } else {
            return (LoginUser) loginUserObj;
        }
    }
    /**
     * redis中是否存在
     *
     * @param userId
     * @return
     */
    public boolean onlineUserExist(String userId, String jti) {
        Boolean hasKey = redisTemplate.hasKey(CacheNameEnums.ONLINE_USER.name() + ":" + userId);
        return null != hasKey && hasKey;
    }

    /**
     * 设置失效时间
     *
     * @param userId
     */
    public void expireOnlineUser(String userId, String jti) {
        redisTemplate.expire(CacheNameEnums.ONLINE_USER.name() + ":" + userId, 30, TimeUnit.MINUTES);
    }

    /**
     * 设置失效时间
     *
     * @param userId
     */
    public void expireUserAt(String userId, String jti, Long timestamp) {
        redisTemplate.expireAt(CacheNameEnums.ONLINE_USER.name() + ":" + userId + ":" + jti, new Date(timestamp));
    }

    public void deleteOnlineUser(String userId, String jti){
        redisTemplate.delete(CacheNameEnums.ONLINE_USER.name() + ":" + userId + ":" + jti);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除缓存
     *
     * @param key
     * @return
     */
    public Boolean deleteCacheObject(String key) {
        return redisTemplate.delete(key);
    }
}
