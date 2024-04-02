package com.study.core.config.oauth2;

import cn.xluobo.business.sys.oauth.service.Oauth2Service;
import cn.xluobo.config.exception.ForceOfflineException;
import cn.xluobo.config.exception.NoTokenInRedisException;
import cn.xluobo.config.login.LoginTypeEnum;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.api.ApiResEnums;
import cn.xluobo.redis.service.CacheService;
import cn.xluobo.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 未授权 响应
 *
 * @author zhangbaoyu
 */
public class BusinessAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    private JsonParser jsonParser = JsonParserFactory.create();

    @Autowired
    private Oauth2Service oauth2Service;
    @Autowired
    private CacheService cacheService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");

        Throwable cause = authException.getCause();
        if (cause instanceof ForceOfflineException) {
            // 强制踢出
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(objectMapper.writeValueAsString(APIResponse.toExceptionResponse(ApiResEnums.SC_FORCE_OFFLINE, cause.getMessage())));
            return;
        } else if (cause instanceof NoTokenInRedisException) {
            // redis 中的token 被人为删除
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(objectMapper.writeValueAsString(APIResponse.toExceptionResponse(ApiResEnums.SC_FORCE_OFFLINE, cause.getMessage())));
        }else if (cause instanceof InvalidTokenException) {
            // 获取用户 id
            String oldAccessToken = null;
            try {
                oldAccessToken = JwtUtils.getAccessTokenByRequest();
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(objectMapper.writeValueAsString(APIResponse.toExceptionResponse(ApiResEnums.SC_INVALID_TOKEN, cause.getMessage())));
                return;
            }
            if (StringUtils.isEmpty(oldAccessToken)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(objectMapper.writeValueAsString(APIResponse.toExceptionResponse(ApiResEnums.SC_INVALID_TOKEN)));
                return;
            }
            Jwt jwt = null;
            Map<String, Object> claimMap = null;
            try {
                jwt = JwtHelper.decode(oldAccessToken);
                claimMap = jsonParser.parseMap(jwt.getClaims());
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(objectMapper.writeValueAsString(APIResponse.toExceptionResponse(ApiResEnums.SC_INVALID_TOKEN, cause.getMessage())));
                return;
            }

            String userId = MapUtils.getString(claimMap, "userId");
            String oldJti = MapUtils.getString(claimMap, "jti");

            LoginUser onlineUser = cacheService.getOnlineUser(userId, oldJti);
            if (null != onlineUser && StringUtils.isNotEmpty(onlineUser.getRefreshToken())) {
                Map auth2AccessTokenMap = null;
                // 刷新 token
                if (LoginTypeEnum.PC.getLoginType().equals(onlineUser.getLoginType())) {
                    auth2AccessTokenMap = oauth2Service.refreshToken(onlineUser.getRefreshToken());
                } else if (LoginTypeEnum.WECHAT_MINI_APP.getLoginType().equals(onlineUser.getLoginType())) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(objectMapper.writeValueAsString(APIResponse.toExceptionResponse(ApiResEnums.SC_INVALID_TOKEN)));
                    return;
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(objectMapper.writeValueAsString(APIResponse.toExceptionResponse(ApiResEnums.SC_UNAUTHORIZED)));
                    return;
                }

                String error = MapUtils.getString(auth2AccessTokenMap, "error");
                if (error != null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(objectMapper.writeValueAsString(APIResponse.toExceptionResponse(ApiResEnums.SC_INVALID_TOKEN)));
                    return;
                }

                // 设置用户 信息
                String newAccessToken = MapUtils.getString(auth2AccessTokenMap, "access_token");
                String newRefreshToken = MapUtils.getString(auth2AccessTokenMap, "refresh_token");
                Jwt newRefreshTokenJwt = JwtHelper.decode(newRefreshToken);
                Map<String, Object> newRefreshTokenClaimMap = jsonParser.parseMap(newRefreshTokenJwt.getClaims());
                Long exp = MapUtils.getLong(newRefreshTokenClaimMap, "exp") * 1000;
                String newJti = JwtUtils.getAccessTokenJti(newAccessToken);

                onlineUser.setAccessToken(newAccessToken);
                onlineUser.setRefreshToken(newRefreshToken);
                cacheService.deleteOnlineUser(userId, oldJti);
                cacheService.putOnlineUser(userId, newJti, onlineUser);
                cacheService.expireUserAt(userId, newJti, exp);

                // 返回新的 token 给客户端
                response.addHeader("access_token", newAccessToken);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(objectMapper.writeValueAsString(APIResponse.toExceptionResponse(ApiResEnums.SC_INVALID_REFRESH_TOKEN)));
                return;
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(objectMapper.writeValueAsString(APIResponse.toExceptionResponse(ApiResEnums.SC_INVALID_TOKEN)));
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(objectMapper.writeValueAsString(APIResponse.toExceptionResponse(ApiResEnums.SC_UNAUTHORIZED)));
        }
    }
}
