package com.study.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-03-01 10:45
 */
@ConfigurationProperties(prefix = "xluobo.address")
public class AddressProperties {

    private static boolean decodeIp;

    public void setDecodeIp(boolean decodeIp) {
        AddressProperties.decodeIp = decodeIp;
    }

    public static boolean isDecodeIp() {
        return decodeIp;
    }
}
