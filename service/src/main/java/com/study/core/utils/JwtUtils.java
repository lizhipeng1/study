package com.study.core.utils;

import cn.xluobo.core.utils.ServletUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/7/5 09:36
 */
public class JwtUtils {

    private static TokenExtractor tokenExtractor = new BearerTokenExtractor();
    private static JsonParser jsonParser = JsonParserFactory.create();

    /**
     * 获取当前请求的jti
     *
     * @return
     */
    public static String getJtiByRequest() {
        HttpServletRequest request = ServletUtils.getRequest();
        Object attribute = request.getAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE);
        if (null != attribute) {
            String accessToken = String.valueOf(attribute);
            Jwt jwt = JwtHelper.decode(accessToken);
            Map<String, Object> claimMap = jsonParser.parseMap(jwt.getClaims());
            return MapUtils.getString(claimMap, "jti");
        }
        return null;
    }

    /**
     * 获取当前accessToken
     * @return
     */
    public static String getAccessTokenByRequest() {
        HttpServletRequest request = ServletUtils.getRequest();
        Object attribute = request.getAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE);
        if (null != attribute) {
            String accessToken = String.valueOf(attribute);
            return accessToken;
        }
        return null;
    }

    /**
     * 获取jwt token失效时间
     * @param jwtToken
     * @return
     */
    public static Long getTokenExp(String jwtToken){
        Jwt jwt = JwtHelper.decode(jwtToken);
        Map<String, Object> claimMap = jsonParser.parseMap(jwt.getClaims());
        return MapUtils.getLong(claimMap, "exp") * 1000;
    }

    /**
     * 获取access token jti
     * @param accessToken
     * @return
     */
    public static String getAccessTokenJti(String accessToken){
        Jwt jwt = JwtHelper.decode(accessToken);
        Map<String, Object> claimMap = jsonParser.parseMap(jwt.getClaims());
        return MapUtils.getString(claimMap, "jti");
    }
}
