package com.study.business.sc.course.service.impl;

import com.study.business.sc.course.domain.req.time.ReqSearchScClaTimeRule;
import com.study.business.sc.course.domain.resp.time.RespClaTimeRule;
import com.study.business.sc.course.service.IScClaTimeRuleService;
import com.study.config.tenant.TenantContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/16 13:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Slf4j
public class ScClaTimeRuleServiceImplTest {

    @Autowired
    private IScClaTimeRuleService claTimeRuleService;

    @Test
    public void getClaTimeList() {
        TenantContextHolder.setTenant("1192453683439271937");
        ReqSearchScClaTimeRule reqSearchScClaTimeRule = new ReqSearchScClaTimeRule();
        reqSearchScClaTimeRule.setClaId(1300744888429596674L);
        reqSearchScClaTimeRule.setBeginDate("2020-01-01");
        reqSearchScClaTimeRule.setEndDate("2020-10-11");
        List<RespClaTimeRule> claTimeList = claTimeRuleService.getClaTimeList(reqSearchScClaTimeRule);
        for (RespClaTimeRule respClaTime : claTimeList) {
            System.out.println(respClaTime.getClaDate());
        }
    }
}
