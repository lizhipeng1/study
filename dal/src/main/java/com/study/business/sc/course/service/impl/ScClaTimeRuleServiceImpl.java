package com.study.business.sc.course.service.impl;

import com.study.business.sc.course.domain.req.time.ReqSearchScClaTimeRule;
import com.study.business.sc.course.domain.resp.time.RespClaTimeRule;
import com.study.business.sc.course.repo.enums.ClaTimeRepeatTypeEnums;
import com.study.business.sc.course.repo.enums.ClaTimeRuleTypeEnums;
import com.study.business.sc.course.repo.mapper.ScClaTimeRuleMapper;
import com.study.business.sc.course.repo.model.ScClaTime;
import com.study.business.sc.course.repo.model.ScClaTimeRule;
import com.study.business.sc.course.service.IScClaTimeRuleService;
import com.study.business.sc.course.service.IScClaTimeService;
import com.study.business.sys.holiday.service.ISysHolidayService;
import com.study.core.utils.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 上课时间配置规则 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-14
 */
@Service
public class ScClaTimeRuleServiceImpl extends ServiceImpl<ScClaTimeRuleMapper, ScClaTimeRule> implements IScClaTimeRuleService {

    @Autowired
    private ISysHolidayService holidayService;
    @Autowired
    private IScClaTimeService claTimeService;

    @Override
    public List<RespClaTimeRule> getClaTimeList(ReqSearchScClaTimeRule reqSearchScClaTimeRule) {
        List<RespClaTimeRule> resultClaTimeList = Lists.newArrayList();

        // 节假日缓存
        Map<String, String> holidayMap = holidayService.getHolidayMap();

        // 获取满足条件的配置
        List<RespClaTimeRule> ruleList = baseMapper.selectByCondition(reqSearchScClaTimeRule, null);

        DateTime searchBegin = DateUtil.yyyMMddDayBegin(reqSearchScClaTimeRule.getBeginDate());
        DateTime searchEnd = DateUtil.yyyMMddDayEnd(reqSearchScClaTimeRule.getEndDate());

        for (RespClaTimeRule itemTime : ruleList) {
            List<RespClaTimeRule> claTimeListByRuleId = this.getClaTimeListByRule(itemTime, holidayMap);
            resultClaTimeList.addAll(claTimeListByRuleId);
        }

        Collections.sort(resultClaTimeList, (o1, o2) -> {
            int a = Integer.parseInt(o1.getClaDate().replaceAll("-", ""));
            int b = Integer.parseInt(o2.getClaDate().replaceAll("-", ""));
            if (a > b) {
                return 1;
            } else if (a < b) {
                return -1;
            } else {
                return 0;
            }
        });
        return resultClaTimeList;
    }

    @Override
    public List<RespClaTimeRule> getClaTimeListByRuleId(Long ruleId) {
        RespClaTimeRule itemTime = baseMapper.selectByRuleId(ruleId);
        return this.getClaTimeListByRule(itemTime, null);
    }

    @Override
    public List<RespClaTimeRule> getClaTimeListByRule(RespClaTimeRule itemTime, Map<String, String> holidayMap) {
        List<RespClaTimeRule> resultClaTimeList = Lists.newArrayList();

        // 节假日缓存
        if (null == holidayMap) {
            holidayMap = holidayService.getHolidayMap();
        }


        String ruleType = itemTime.getRuleType();
        String repeatType = itemTime.getRepeatType();
        String weekDay = itemTime.getWeekDay();
        boolean filterHoliday = itemTime.isFilterHoliday();
        String[] chooseDate = itemTime.getChooseDate();

        DateTime beginDate = DateUtil.yyyMMddDayBegin(itemTime.getBeginDate());
        DateTime endDate = DateUtil.yyyMMddDayEnd(itemTime.getEndDate());

        if (ClaTimeRuleTypeEnums.ONCE_RULE.getRuleType().equals(ruleType)) {

            for (String date : chooseDate) {
                RespClaTimeRule respClaTime = RespClaTimeRule.builder()
                        .claName(itemTime.getClaName())
                        .courseName(itemTime.getCourseName())
                        .staffName(itemTime.getStaffName())
                        .claDate(date)
                        .claTimeBegin(itemTime.getClaTimeBegin())
                        .claTimeEnd(itemTime.getClaTimeEnd())
                        .build();
                respClaTime.setRuleId(itemTime.getRuleId());
                respClaTime.setClassTheme(itemTime.getClassTheme());

                resultClaTimeList.add(respClaTime);
            }

            return resultClaTimeList;
        }

        // 周几上课
        List<String> weekDayList = Lists.newArrayList();
        if (ClaTimeRepeatTypeEnums.EVERY_WEEK.getRepeatType().equals(repeatType)
                || ClaTimeRepeatTypeEnums.EVERY_SECOND_WEEK.getRepeatType().equals(repeatType)) {
            if (StringUtils.isEmpty(weekDay)) {
                return resultClaTimeList;
            }
            weekDayList = Lists.newArrayList(weekDay.split(","));
        }

        // 非单次排课 计算所有上课日期
        DateTime cycleBeginDate = beginDate;
        DateTime cycleEndDate = endDate;

        if (ClaTimeRepeatTypeEnums.EVERY_WEEK.getRepeatType().equals(repeatType)) {
            while (cycleBeginDate.isBefore(cycleEndDate)) {

                // 每周重复
                int dayOfWeek = cycleBeginDate.getDayOfWeek();
                boolean contains = weekDayList.contains(String.valueOf(dayOfWeek));

                if (!contains) {
                    // 不上课
                    cycleBeginDate = cycleBeginDate.plusDays(1);
                    continue;
                }

                if (filterHoliday && holidayMap.containsKey(cycleBeginDate.toString("yyyyMMdd"))) {
                    // 过滤节假日
                    cycleBeginDate = cycleBeginDate.plusDays(1);
                    continue;
                }

                RespClaTimeRule respClaTime = RespClaTimeRule.builder()
                        .claName(itemTime.getClaName())
                        .courseName(itemTime.getCourseName())
                        .staffName(itemTime.getStaffName())
                        .claDate(cycleBeginDate.toString("yyyy-MM-dd"))
                        .claTimeBegin(itemTime.getClaTimeBegin())
                        .claTimeEnd(itemTime.getClaTimeEnd())
                        .build();
                respClaTime.setRuleId(itemTime.getRuleId());
                respClaTime.setClassTheme(itemTime.getClassTheme());

                resultClaTimeList.add(respClaTime);

                cycleBeginDate = cycleBeginDate.plusDays(1);
            }
        } else if (ClaTimeRepeatTypeEnums.EVERY_SECOND_WEEK.getRepeatType().equals(repeatType)) {
            while (cycleBeginDate.isBefore(cycleEndDate)) {
                int dayOfWeek = cycleBeginDate.getDayOfWeek();

                boolean contains = weekDayList.contains(String.valueOf(dayOfWeek));

                if (!contains) {
                    // 不上课
                    if (dayOfWeek == 7) {
                        cycleBeginDate = cycleBeginDate.plusDays(8);
                    } else {
                        cycleBeginDate = cycleBeginDate.plusDays(1);
                    }
                    continue;
                }

                if (filterHoliday && holidayMap.containsKey(cycleBeginDate.toString("yyyyMMdd"))) {
                    // 过滤节假日
                    if (dayOfWeek == 7) {
                        cycleBeginDate = cycleBeginDate.plusDays(8);
                    } else {
                        cycleBeginDate = cycleBeginDate.plusDays(1);
                    }
                    continue;
                }

                RespClaTimeRule respClaTime = RespClaTimeRule.builder()
                        .claName(itemTime.getClaName())
                        .courseName(itemTime.getCourseName())
                        .staffName(itemTime.getStaffName())
                        .claDate(cycleBeginDate.toString("yyyy-MM-dd"))
                        .claTimeBegin(itemTime.getClaTimeBegin())
                        .claTimeEnd(itemTime.getClaTimeEnd())
                        .build();
                respClaTime.setRuleId(itemTime.getRuleId());
                respClaTime.setClassTheme(itemTime.getClassTheme());

                resultClaTimeList.add(respClaTime);

                if (dayOfWeek == 7) {
                    cycleBeginDate = cycleBeginDate.plusDays(8);
                } else {
                    cycleBeginDate = cycleBeginDate.plusDays(1);
                }
            }
        } else if (ClaTimeRepeatTypeEnums.EVERY_SECOND_DAY.getRepeatType().equals(repeatType)) {
            while (cycleBeginDate.isBefore(cycleEndDate)) {

                if (filterHoliday && holidayMap.containsKey(cycleBeginDate.toString("yyyyMMdd"))) {
                    // 过滤节假日
                    cycleBeginDate = cycleBeginDate.plusDays(2);
                    continue;
                }

                RespClaTimeRule respClaTime = RespClaTimeRule.builder()
                        .claName(itemTime.getClaName())
                        .courseName(itemTime.getCourseName())
                        .staffName(itemTime.getStaffName())
                        .claDate(cycleBeginDate.toString("yyyy-MM-dd"))
                        .claTimeBegin(itemTime.getClaTimeBegin())
                        .claTimeEnd(itemTime.getClaTimeEnd())
                        .build();
                respClaTime.setRuleId(itemTime.getRuleId());
                respClaTime.setClassTheme(itemTime.getClassTheme());

                resultClaTimeList.add(respClaTime);

                cycleBeginDate = cycleBeginDate.plusDays(2);
            }
        }
        return resultClaTimeList;
    }

    @Override
    public List<RespClaTimeRule> getClaTimeListByRule(ScClaTimeRule claTimeRule, Map<String, String> holidayMap) {
        RespClaTimeRule respClaTime = RespClaTimeRule.builder()
                .claTimeBegin(claTimeRule.getStartTime())
                .claTimeEnd(claTimeRule.getEndTime())
                .build();

        BeanUtils.copyProperties(claTimeRule, respClaTime);

        return this.getClaTimeListByRule(respClaTime, holidayMap);
    }

    @Override
    public List<String> checkDayRepeat(Long claId, String[] day, Long notEqRuleId) {
        if (null == claId || null == day || day.length == 0) {
            return null;
        }

        QueryWrapper<ScClaTime> qw = new QueryWrapper<>();
        qw.select("cla_date");
        qw.eq("cla_id", claId);
        qw.eq("status", "1");
        qw.in("cla_date", day);
        if (null != notEqRuleId) {
            qw.ne("rule_id", notEqRuleId);
        }
        List<ScClaTime> list = claTimeService.list(qw);
        return list.stream().map(ScClaTime::getClaDate).collect(Collectors.toList());
    }

    @Override
    public List<String> checkDayRepeat(ScClaTimeRule claTimeRule) {
        Long claId = claTimeRule.getClaId();
        String repeatType = claTimeRule.getRepeatType();
        String ruleType = claTimeRule.getRuleType();
        String[] chooseDate = claTimeRule.getChooseDate();
        if (null == claId) {
            return null;
        }
        if (StringUtils.isAnyEmpty(repeatType, ruleType)) {
            return null;
        }

        if (ClaTimeRuleTypeEnums.ONCE_RULE.getRuleType().equals(ruleType)) {
            if (null == chooseDate || chooseDate.length == 0) {
                return null;
            }
            return this.checkDayRepeat(claId, chooseDate, claTimeRule.getRuleId());
        }
        // 非单次排课 计算所有上课日期
        List<RespClaTimeRule> claTimeListByRule = this.getClaTimeListByRule(claTimeRule, null);
        return this.checkDayRepeat(claId, claTimeListByRule.stream().map(RespClaTimeRule::getClaDate).toArray(String[]::new), claTimeRule.getRuleId());
    }

    @Override
    public List<String> selectClaTimeInfo(Long claId) {
        return baseMapper.selectClaTimeInfo(claId);
    }
}
