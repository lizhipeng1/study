package com.study.config.security;

import cn.jljiayu.filter.ValidateCodeFilter;
import cn.xluobo.core.api.APIBaseResponse;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Resource
    private DataSource dataSource;
    @Bean
    @ConditionalOnMissingBean(CustomUserService.class)
    @Lazy
    public CustomUserService customUserService() {
        return new CustomUserService();
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

    /**
     * 登录验证码
     * @return
     * @throws Exception
     */
    @Bean
    public ValidateCodeFilter validateCodeFilter() throws Exception {
        return new ValidateCodeFilter();
    }

    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        CustomAuthenticationFailureHandler customAuthenticationFailureHandler = new CustomAuthenticationFailureHandler();
        customAuthenticationFailureHandler.setDefaultFailureUrl("/ssoLogin");
        return customAuthenticationFailureHandler;
    }

    /**
     * 配置TokenRepository
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        // 配置数据源
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/favicon.ico","/login/imageCode").permitAll()
                .and().formLogin()
                .loginPage("/ssoLogin")
                .loginProcessingUrl("/ssoLoginProcesses")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler())
                //由于重新设置了 CustomUsernamePasswordAuthenticationFilter 并且setAuthenticationFailureHandler， failureHandler不生效
//                .failureHandler(authenticationFailureHandler())
                .permitAll()
                .and()
                .rememberMe()// 记住我相关配置
                .rememberMeCookieName("qyxt-remember-me")
                .authenticationSuccessHandler(customAuthenticationSuccessHandler)
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(7*24*60*60)
                .userDetailsService(customUserService())

                .and().logout().logoutUrl("/api/system/user/logout").logoutSuccessHandler(logoutSuccessHandler())
                .and().csrf().ignoringAntMatchers("/api/system/user/logout")
                .and().authorizeRequests().anyRequest().authenticated();


        http.headers().frameOptions().disable();

        // 自定义账号密码验证
        // http.addFilterAt(customUsernamePasswordAuthenticationFilter(http), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(validateCodeFilter(), CustomUsernamePasswordAuthenticationFilter.class);//把验证码过滤器加载登录过滤器前边
    }

    /**
     * 自定义账号密码验证
     * @return
     * @throws Exception
     */
    /*private CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter(HttpSecurity http) throws Exception {
        CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler());
        filter.setFilterProcessesUrl("/ssoLoginProcesses");
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }*/

    private LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            APIBaseResponse apiBaseResponse = new APIBaseResponse("0000","success");
            PrintWriter writer = response.getWriter();
            writer.write(JSONObject.toJSONString(apiBaseResponse));
            writer.flush();
            writer.close();
        };
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers(
                "/static/**"
        );
    }

    public AuthenticationProvider userAuthenticationProvider() {
        LoginPwdDaoAuthenticationProvider loginPwdDaoAuthenticationProvider = new LoginPwdDaoAuthenticationProvider();
        loginPwdDaoAuthenticationProvider.setUserDetailsService(customUserService());
        loginPwdDaoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return loginPwdDaoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userAuthenticationProvider());
    }
}
