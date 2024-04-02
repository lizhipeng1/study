package com.study.core.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * WebSecurityConfigurerAdapter 默认order值为100
 *
 * @EnableResourceServer 注解定义了order值为3（The annotation creates a WebSecurityConfigurerAdapter with a hard-coded Order (of 3)）
 * <p>
 * 当定义了 @EnableResourceServer 本类中的 protected void configure(HttpSecurity http) 就会失效
 * <p>
 * <p>
 * http.antMatcher("/ta/**").authorizeRequests() 表明 这个 HttpSecurity 只适用于以 /ta/开头的URL
 * <p>
 * Created by zhangbaoyu on 18/1/9.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers(
                "/static/upload/**/*.jpg",
                "/static/upload/**/*.gif",
                "/static/upload/**/*.jpeg",
                "/static/upload/**/*.png",
                "/static/upload/**/*.svg",
                "/static/upload/**/*.bmp"
        );
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

    }
}
