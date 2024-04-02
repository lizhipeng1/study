package com.study.core.config.oauth2;

import cn.xluobo.business.sys.admin.service.ISysMenuService;
import cn.xluobo.business.sys.admin.service.ISysUserService;
import cn.xluobo.business.sys.admin.service.ISysUserTenantService;
import cn.xluobo.config.properties.OAuth2ClientDetail;
import cn.xluobo.config.properties.OAuth2SsoDetail;
import cn.xluobo.config.properties.WechatOAuth2ClientProperties;
import cn.xluobo.config.security.CustomAccessDecisionManager;
import cn.xluobo.config.security.CustomFilterSecurityInterceptor;
import cn.xluobo.config.security.CustomUserService;
import cn.xluobo.redis.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 *
 */
@Configuration
@EnableResourceServer
@EnableConfigurationProperties({OAuth2ClientDetail.class, OAuth2SsoDetail.class, WechatOAuth2ClientProperties.class})
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private ResourceServerProperties resourceServerProperties;
    @Autowired
    private ISysUserTenantService userTenantService;
    @Autowired
    private ISysMenuService menuService;
    @Autowired
    private CustomUserService customUserService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private ISysUserService sysUserService;

    @Bean
    @ConditionalOnMissingBean(CustomUserService.class)
    @Lazy
    public CustomUserService customUserService() {
        return new CustomUserService();
    }

    @Bean
    public RemoteTokenServices remoteTokenServices() {
        //用户详细信息
        CustomUserAuthenticationConverter userTokenConverter = new CustomUserAuthenticationConverter();
        userTokenConverter.setCacheService(cacheService);
        userTokenConverter.setUserTenantService(userTenantService);
        userTokenConverter.setMenuService(menuService);
        userTokenConverter.setCustomUserService(customUserService);
        userTokenConverter.setSysUserService(sysUserService);

        //accessToken 转换
        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        defaultAccessTokenConverter.setUserTokenConverter(userTokenConverter);

        RemoteTokenServices services = new RemoteTokenServices();
        services.setCheckTokenEndpointUrl(this.resourceServerProperties.getTokenInfoUri());
        services.setClientId(this.resourceServerProperties.getClientId());
        services.setClientSecret(this.resourceServerProperties.getClientSecret());
        services.setAccessTokenConverter(defaultAccessTokenConverter);
        return services;
    }

    @Bean
    public BusinessAccessDeniedHandler businessAccessDeniedHandler(){
        return new BusinessAccessDeniedHandler();
    }

    @Bean
    public BusinessAuthenticationEntryPoint businessAuthenticationEntryPoint(){
        return new BusinessAuthenticationEntryPoint();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceServerProperties.getResourceId())
                .tokenServices(remoteTokenServices())
        ;
        resources.accessDeniedHandler(businessAccessDeniedHandler())
                .authenticationEntryPoint(businessAuthenticationEntryPoint());

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(
                "/oauth2/**", // oauth2 授权
                "/api/wechat/miniapp/*/base/login/*", // 小程序登录
                "/api/wechat/miniapp/*/base/bind", // 绑定
                "/server/check/**", // 微信服务器消息
                "/activiti-editor/**",
                "/login/imageCode/asyn",
                "/favicon.ico"
        )
                .permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedHandler(new BusinessAccessDeniedHandler())
        .and().csrf().ignoringAntMatchers(
                "/oauth2/**",
                "/server/check/**",
                "/activiti-editor/**",
                "/api/**"
        ).and().cors().configurationSource(urlBasedCorsConfigurationSource())
        .and().headers().frameOptions().sameOrigin()
        ;

        http.addFilterBefore(new CustomFilterSecurityInterceptor(new CustomAccessDecisionManager()), FilterSecurityInterceptor.class);
    }

    private UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1 设置访问源地址
        corsConfiguration.addAllowedOrigin("*");
        // 2 设置访问源请求头
        corsConfiguration.addAllowedHeader("Authorization");
        corsConfiguration.addAllowedHeader("Content-Type");
        // 3 设置访问源请求方法
        corsConfiguration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/wechat/miniapp/**", corsConfiguration);
        source.registerCorsConfiguration("/login/imageCode/asyn", corsConfiguration);
        return source;
    }
}
