package com.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.UnknownHostException;

@SpringBootApplication(scanBasePackages = {"com.study","cn.jljiayu"})
@EnableTransactionManagement
@MapperScan(value = {"cn.xluobo.*.*.*.repo.mapper"})
public class OauthServerApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(OauthServerApplication.class, args);
    }

}


