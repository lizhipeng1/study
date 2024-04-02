package com.study.core.business.sys.oauth.controller;

import cn.xluobo.business.sys.admin.service.ISysUserTenantService;
import cn.xluobo.business.sys.oauth.service.Oauth2Service;
import cn.xluobo.business.sys.staff.service.ISysStaffService;
import cn.xluobo.config.login.LoginTypeEnum;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.config.properties.OAuth2SsoDetail;
import cn.xluobo.config.tenant.TenantContextHolder;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * http://localhost:9092/oauth/authorize?response_type=code&client_id=business-server&redirect_uri=http://localhost:9091/oauth2/callback&scope=all&state=http://www.baidu.com
 */
@Controller
public class Oauth2Controller {

    @Autowired
    private Oauth2Service oauth2Service;
    @Autowired
    private OAuth2SsoDetail ssoDetail;
    @Autowired
    private ISysStaffService staffService;
    @Autowired
    private ISysUserTenantService userTenantService;

    @RequestMapping("/oauth2/callback")
    public String oauth2CallBack(String code, String state,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        //根据code获取accessToken
        Map oAuth2AccessToken = oauth2Service.getAccessToken(code);

        // 如果错误
        String error = MapUtils.getString(oAuth2AccessToken, "error");
        if (error != null) {
            try {
                request.getRequestDispatcher(request.getRequestURI()).forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }

        String refreshToken = MapUtils.getString(oAuth2AccessToken, "refresh_token");
        String accessToken = MapUtils.getString(oAuth2AccessToken, "access_token");

        LoginUser loginUser = oauth2Service.getLoginUserByAccessToken(accessToken);
        loginUser.setLoginType(LoginTypeEnum.PC.getLoginType());

        try {
            String tenantId = userTenantService.selectUserDefaultTenant(loginUser.getUserId());
            TenantContextHolder.setTenant(tenantId);

            Long staffId = staffService.getStaffIdByUserId(loginUser.getUserId());
            loginUser.setStaffId(staffId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            TenantContextHolder.remove();
        }

        oauth2Service.putOnlineUser(loginUser, accessToken, refreshToken);

        String redirectUrl = String.format(ssoDetail.getSsoRedirectUrl(), state, accessToken);
        return "redirect:" + redirectUrl;
    }
}
