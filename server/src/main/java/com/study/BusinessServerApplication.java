package com.study;

import com.study.config.properties.AddressProperties;
import com.study.config.properties.UploadConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.UnknownHostException;

@SpringBootApplication
@EnableConfigurationProperties({UploadConfigProperties.class, AddressProperties.class})
@EnableScheduling
@EnableAsync
public class BusinessServerApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(BusinessServerApplication.class, args);
    }

}


