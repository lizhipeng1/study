package com.study.business.wechat.cp.service;

import com.study.config.wechat.cp.WechatCpConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Slf4j
@Import(WechatCpConfiguration.class)
public class ThirdWechatCpSyncServiceTest {

    @Autowired
    private WechatCpSyncService wechatCpSyncService;

    @Test
    public void syncContact() throws InterruptedException {
        wechatCpSyncService.syncSelfWechatCpContact("-1");
        Thread.sleep(1000 * 10);
    }

    @Test
    public void syncTag() throws InterruptedException {
        wechatCpSyncService.syncSelfWechatCpTag("-1");
        Thread.sleep(1000 * 10);
    }

    @Test
    public void syncUser() throws InterruptedException {
        wechatCpSyncService.syncSelfWechatCpUser("-1");
        Thread.sleep(1000 * 10);
    }

}