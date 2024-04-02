package com.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/ssoLogin")
    public String login() {
        return "login";
    }

    /**
     * 统一跳转
     * @param t token
     * @param s state
     * @param modelMap
     * @return
     */
    @RequestMapping("/ssoLoginRedirect")
    public String ssoLoginRedirect(String t,String s, ModelMap modelMap){
        modelMap.put("accessToken",t);
        modelMap.put("state",s);
        return "loginRedirect";
    }
}
