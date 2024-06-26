package com.study.controller;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@SessionAttributes("authorizationRequest")  // 重要！
public class AuthorizationController {
    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView view = new ModelAndView();
        view.setViewName("authorization");
        // 传递 scope 过去,Set 集合
        view.addObject("scopes", authorizationRequest.getScope());
        view.addObject("scopeName", String.join(",", authorizationRequest.getScope()));
        view.addObject("clientId", authorizationRequest.getClientId());
        return view;
    }
}
