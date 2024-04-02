package com.study.config.security;

import cn.xluobo.core.api.APIBaseResponse;
import com.alibaba.fastjson.JSON;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录失败
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020-02-27 17:42
 */
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String xRequestedWith = request.getHeader("X-Requested-With");
        if (MediaType.APPLICATION_JSON_UTF8_VALUE.equals(request.getContentType())
                || MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {
            String message = exception.getMessage();
            String failJson = JSON.toJSONString(new APIBaseResponse("LOGIN0001", message));
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            PrintWriter out = response.getWriter();
            out.write(failJson);
            out.flush();
            out.close();
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
