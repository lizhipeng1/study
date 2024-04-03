package com.study.business.sys.oauth.service;

import com.study.business.sys.admin.service.ISysUserService;
import com.study.business.sys.admin.service.ISysUserTenantService;
import com.study.config.login.LoginUser;
import com.study.config.properties.OAuth2ClientDetail;
import com.study.config.properties.WechatOAuth2ClientProperties;
import com.study.redis.service.CacheService;
import com.study.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-05 09:42
 */
@Service
@Slf4j
public class Oauth2Service {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private OAuth2ClientDetail clientDetail;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysUserTenantService userTenantService;
    @Autowired
    private WechatOAuth2ClientProperties wechatOAuth2ClientProperties;

    private JsonParser jsonParser = JsonParserFactory.create();

    /**
     * 根据code换取accessToken
     * @param code
     * @return
     */
    public Map getAccessToken(String code) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "authorization_code");
        form.add("code", code);
        form.add("client_id", clientDetail.getClientId());
        form.add("client_secret", clientDetail.getClientSecret());
        form.add("redirect_uri", clientDetail.getRedirectUri());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        Map auth2AccessToken = restTemplate.postForObject(clientDetail.getAccessTokenUri(), request, Map.class);
        return auth2AccessToken;
    }

    /**
     * 刷新token
     * @param refreshToken
     * @return
     */
    public Map refreshToken(String refreshToken) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "refresh_token");
        form.add("client_id", clientDetail.getClientId());
        form.add("client_secret", clientDetail.getClientSecret());
        form.add("refresh_token", refreshToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        return restTemplate.postForObject(clientDetail.getAccessTokenUri(), request, Map.class);
    }

    /**
     * 通过微信小程序 openid 换取 accessToken
     * @param appId
     * @param openid
     * @param password
     * @return
     */
    public Map getAccessTokenByMiniAppOpenid(String appId, String openid, String password){
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("auth_type", "wechatMiniApp");
        form.add("appId", appId);
        form.add("openid", openid);
        form.add("password", password);
        form.add("client_id", wechatOAuth2ClientProperties.getClientId());
        form.add("client_secret", wechatOAuth2ClientProperties.getClientSecret());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        Map auth2AccessToken = restTemplate.postForObject(clientDetail.getAccessTokenUri(), request, Map.class);
        return auth2AccessToken;
    }

    /**
     * 根据accessToken 获取 加载登录用户信息
     * @param accessToken
     * @return
     */
    public LoginUser getLoginUserByAccessToken(String accessToken) {
        Jwt jwt = JwtHelper.decode(accessToken);
        Map<String, Object> claimMap = jsonParser.parseMap(jwt.getClaims());
        String username = MapUtils.getString(claimMap, "username");
        LoginUser loginUser = (LoginUser) sysUserService.loadUserDetailByUserName(username);
        return loginUser;
    }

    /**
     * redis存入 登录用户信息
     * @param accessToken
     * @param refreshToken
     */
    public void putOnlineUser(LoginUser loginUser,String accessToken, String refreshToken) {
        Jwt jwt = JwtHelper.decode(accessToken);
        Map<String, Object> claimMap = jsonParser.parseMap(jwt.getClaims());
        String userId = MapUtils.getString(claimMap, "userId");
        String jti = MapUtils.getString(claimMap, "jti");
        loginUser.setAccessToken(accessToken);
        Long exp = null;
        if (StringUtils.isNotEmpty(refreshToken)) {
            loginUser.setRefreshToken(refreshToken);
            // refresh 失效时间
            exp = JwtUtils.getTokenExp(refreshToken);
        } else {
            // token 失效时间
            exp = JwtUtils.getTokenExp(accessToken);
        }

        String tenantId = userTenantService.selectUserDefaultTenant(userId);
        loginUser.setNowTenantId(tenantId);

        // 在线用户，失效时间
        cacheService.putOnlineUser(userId, jti, loginUser);
        cacheService.expireUserAt(userId, jti, exp);
    }

    /**
     * 删除当前登录用户 redis中的token
     */
    public void removeToken() {
        String accessToken = JwtUtils.getAccessTokenByRequest();
        Map<String, Object> claimMap = null;
        try {
            Jwt jwt = JwtHelper.decode(accessToken);
            claimMap = jsonParser.parseMap(jwt.getClaims());
        } catch (Exception e) {

        }
        String userId = MapUtils.getString(claimMap, "userId");
        String jti = MapUtils.getString(claimMap, "jti");
        cacheService.expireUserAt(userId, jti, 0L);
    }
}
