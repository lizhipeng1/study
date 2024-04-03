package com.study.business.sys.holiday.service.impl;

import com.study.business.sys.holiday.repo.model.SysHoliday;
import com.study.business.sys.holiday.repo.mapper.SysHolidayMapper;
import com.study.business.sys.holiday.service.ISysHolidayService;
import com.study.core.api.APIBaseResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 节假日表 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-15
 */
@Service
@Slf4j
public class SysHolidayServiceImpl extends ServiceImpl<SysHolidayMapper, SysHoliday> implements ISysHolidayService {

    @Resource(name = "holidayCache")
    private Cache<String, Map<String, String>> holidayCache;

    @Override
    public List<SysHoliday> selectYearOrLaterHoliday(String year) {
        QueryWrapper<SysHoliday> qw = new QueryWrapper<>();
        qw.ge("day", year);
        qw.in("day_type", "1","2");
        return this.list(qw);
    }

    @Override
    public Map<String, String> getHolidayMap() {
        Map<String, String> holidayMap = holidayCache.get("holidayCache", k -> {
            String now = DateTime.now().toString("yyyy");
            List<SysHoliday> sysHolidays = this.selectYearOrLaterHoliday(now);
            Map<String, String> collect = sysHolidays.stream().collect(Collectors.toMap(SysHoliday::getDay, SysHoliday::getDayType));
            return collect;
        });
        return holidayMap;
    }

    @Override
    public APIBaseResponse autoInsertHoliday(String year) {
        JSONObject resultMap = new JSONObject();
        try {
            HttpGet httpGet = new HttpGet("http://tool.bitefu.net/jiari/?d=" + year);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpEntity entity = httpClient.execute(httpGet).getEntity();
            String resultJson = EntityUtils.toString(entity, Charset.forName("UTF-8"));
            resultMap = JSON.parseObject(resultJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object object = resultMap.get(year);
        if (object instanceof Boolean) {
            log.error("can not get {} year holiday,interface result={}", year, object);
            return APIBaseResponse.fail("can not get year holiday,interface result = " + object);
        }
        JSONObject holidayMap = resultMap.getJSONObject(year);

        List<SysHoliday> holidayList = Lists.newArrayList();

        holidayMap.forEach((key, value) -> {
            holidayList.add(new SysHoliday(year + key, holidayMap.getString(key)));
        });

        // 删除对应年份所有数据
        UpdateWrapper uw = new UpdateWrapper();
        uw.likeRight("day", year);
        this.remove(uw);

        // 入库
        this.saveBatch(holidayList);
        return APIBaseResponse.success();
    }
}
