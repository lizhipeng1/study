package com.study.config.security;

import cn.jljiayu.exception.ValidateCodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 自定义登录
 * 可增加 校验项，但不推荐，无法完成 remember me登录
 * @author ：zhangbaoyu
 * @date ：Created in 2020-02-27 17:25
 */
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (MediaType.APPLICATION_JSON_UTF8_VALUE.equals(request.getContentType())
                || MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {

            String username = "";
            String password = "";
            ObjectMapper mapper = new ObjectMapper();
            // 可增加 校验项，但不推荐，无法完成 remember me登录
            try (InputStream is = request.getInputStream()){
                Map reqParam = mapper.readValue(is, Map.class);
                username = MapUtils.getString(reqParam,getUsernameParameter());
                password = MapUtils.getString(reqParam,getPasswordParameter());
            }catch (IOException e) {
                logger.error("imageCode get error",e);
                throw new ValidateCodeException("无法获取账号密码");
            }

            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }

            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    username, password);
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);

        } else {
            return super.attemptAuthentication(request, response);
        }
    }
}
