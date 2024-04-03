package com.study.scheduled;

import com.study.business.sys.holiday.service.ISysHolidayService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/16 11:19
 */
@Slf4j
@Component
public class HolidayScheduled {

    @Autowired
    private ISysHolidayService holidayService;

    /**
     * 12月20日 早6点
     */
    @Scheduled(cron = "0 0 6 20 12 ?")
    @Transactional
    public void autoInsertSysHoliday() throws Exception {
        log.info("autoInsertSysHoliday begin");
        String year = DateTime.now().plusYears(1).toString("yyyy");
        holidayService.autoInsertHoliday(year);
        log.info("autoInsertSysHoliday end");
    }

}
