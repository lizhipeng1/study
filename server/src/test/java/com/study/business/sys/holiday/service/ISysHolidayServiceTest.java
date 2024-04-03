package com.study.business.sys.holiday.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/16 13:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Slf4j
public class ISysHolidayServiceTest {

    @Autowired
    private ISysHolidayService holidayService;

    @Test
    public void test() {
        holidayService.autoInsertHoliday("2020");
    }
}
