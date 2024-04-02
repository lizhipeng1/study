package com.study.core.config.oauth2;

import cn.xluobo.config.exception.ForceOfflineException;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.redis.service.CacheService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 认证成功 监听
 * @author ：zhangbaoyu
 * @date ：Created in 2020/7/4 15:43
 */
@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private CacheService cacheService;
    private JsonParser jsonParser = JsonParserFactory.create();

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {

        Authentication authentication = event.getAuthentication();
        if(authentication instanceof OAuth2Authentication){
            OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;

            Authentication userAuthentication = auth2Authentication.getUserAuthentication();
            Object principal = userAuthentication.getPrincipal();
            if(principal instanceof LoginUser){
                LoginUser loginUser = (LoginUser) principal;

                // 获取token失效时间
                OAuth2AuthenticationDetails auth2AuthenticationDetails = (OAuth2AuthenticationDetails)auth2Authentication.getDetails();
                String accessToken = auth2AuthenticationDetails.getTokenValue();
                Jwt jwt = JwtHelper.decode(accessToken);
                Map<String, Object> claimMap = jsonParser.parseMap(jwt.getClaims());
                String userId = MapUtils.getString(claimMap, "userId");
                String jti = MapUtils.getString(claimMap, "jti");
                LoginUser onlineUser = cacheService.getOnlineUser(userId, jti);
                if(null == onlineUser){
                    throw new ForceOfflineException("强制下线");
                }
            }
        }
    }
}
