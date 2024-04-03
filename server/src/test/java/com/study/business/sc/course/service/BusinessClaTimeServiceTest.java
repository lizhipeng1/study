package com.study.business.sc.course.service;

import com.study.business.sc.course.domain.req.time.ReqSearchClaTime;
import com.study.config.tenant.TenantContextHolder;
import com.study.core.api.APIResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/24 15:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Slf4j
public class BusinessClaTimeServiceTest {

    @Autowired
    private BusinessClaTimeService businessClaTimeService;

    @Test
    public void searchListForCalendar() {
        TenantContextHolder.setTenant("1192453683439271937");
        ReqSearchClaTime reqSearchClaTime = new ReqSearchClaTime();
        reqSearchClaTime.setBeginDate("2020-09-21");
        reqSearchClaTime.setEndDate("2020-09-27");
        APIResponse apiResponse = businessClaTimeService.searchListForCalendar(reqSearchClaTime);
        System.out.println(JSON.toJSONString(apiResponse));
    }
}
