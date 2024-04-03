package com.study.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/15 10:35
 */
@Data
@ConfigurationProperties(prefix = "wechat.oauth2.client")
public class WechatOAuth2ClientProperties {

    private String clientId;

    private String clientSecret;
}
