package com.study.business.monitor.online.service;

import com.study.business.sys.admin.domain.req.ReqSearchOnlineUser;
import com.study.config.enums.CacheNameEnums;
import com.study.config.login.LoginUser;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.core.page.RespPage;
import com.study.redis.service.CacheService;
import com.study.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-03-03 09:54
 */
@Service
public class BusinessOnlineUserService {

    @Autowired
    private CacheService cacheService;

    /**
     * 在线用户列表
     *
     * @param reqSearchOnlineUser
     * @return
     */
    public APIResponse searchList(ReqSearchOnlineUser reqSearchOnlineUser) {
        List<LoginUser> loginUserList = Lists.newArrayList();
        Collection<String> keys = cacheService.keys(CacheNameEnums.ONLINE_USER.name() + ":*");
        for (String key : keys) {
            LoginUser loginUser = cacheService.getCacheObject(key);
            loginUser.setJti(JwtUtils.getAccessTokenJti(loginUser.getAccessToken()));
            loginUser.setRefreshToken(null);
            loginUser.setAccessToken(null);
            if (StringUtils.isNotEmpty(reqSearchOnlineUser.getUsername())) {
                if (reqSearchOnlineUser.getUsername().equals(loginUser.getUsername())) {
                    loginUserList.add(loginUser);
                }
            } else {
                loginUserList.add(loginUser);
            }
        }
        RespPage<LoginUser> respPage = new RespPage<>(reqSearchOnlineUser.getPageNum(), reqSearchOnlineUser.getPageSize());
        respPage.setRows(loginUserList);
        respPage.setTotal(keys.size());
        return APIResponse.toAPIResponse(respPage);
    }

    /**
     * 强制下线
     * @param userId
     * @return
     */
    public APIResponse forceOffline(String userId, String jti){
        if(StringUtils.isEmpty(userId)){
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }
        cacheService.deleteCacheObject(CacheNameEnums.ONLINE_USER.name() + ":" + userId + ":" + jti);
        return APIResponse.toOkResponse();
    }
}
