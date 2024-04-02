package com.study.config.security;

import cn.xluobo.business.sys.log.service.ISysUserLoginLogService;
import cn.xluobo.core.api.APIBaseResponse;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录成功
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020-02-27 17:41
 */
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private boolean contextRelative;
    @Autowired
    private ISysUserLoginLogService loginLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        loginLogService.saveLoginLog(request, authentication);

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (MediaType.APPLICATION_JSON_UTF8_VALUE.equals(request.getContentType())
                || MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {

            //获取需要redirect的url
            SavedRequest savedRequest = requestCache.getRequest(request, response);

            if (savedRequest == null) {
                super.onAuthenticationSuccess(request, response, authentication);

                return;
            }
            String targetUrlParameter = getTargetUrlParameter();
            if (isAlwaysUseDefaultTargetUrl()
                    || (targetUrlParameter != null && StringUtils.hasText(request
                    .getParameter(targetUrlParameter)))) {
                requestCache.removeRequest(request, response);
                super.onAuthenticationSuccess(request, response, authentication);

                return;
            }

            clearAuthenticationAttributes(request);

            // Use the DefaultSavedRequest URL
            String targetUrl = savedRequest.getRedirectUrl();

            String redirectUrl = calculateRedirectUrl(request.getContextPath(), targetUrl);
            redirectUrl = response.encodeRedirectURL(redirectUrl);

            String failJson = JSON.toJSONString(new APIBaseResponse("0000", redirectUrl));
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            PrintWriter out = response.getWriter();
            out.write(failJson);
            out.flush();
            out.close();
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    protected String calculateRedirectUrl(String contextPath, String url) {
        if (!UrlUtils.isAbsoluteUrl(url)) {
            if (isContextRelative()) {
                return url;
            } else {
                return contextPath + url;
            }
        }

        // Full URL, including http(s)://

        if (!isContextRelative()) {
            return url;
        }

        // Calculate the relative URL from the fully qualified URL, minus the last
        // occurrence of the scheme and base context.
        url = url.substring(url.lastIndexOf("://") + 3); // strip off scheme
        url = url.substring(url.indexOf(contextPath) + contextPath.length());

        if (url.length() > 1 && url.charAt(0) == '/') {
            url = url.substring(1);
        }

        return url;
    }

    /**
     * If <tt>true</tt>, causes any redirection URLs to be calculated minus the protocol
     * and context path (defaults to <tt>false</tt>).
     */
    public void setContextRelative(boolean useRelativeContext) {
        this.contextRelative = useRelativeContext;
    }

    protected boolean isContextRelative() {
        return contextRelative;
    }
}
