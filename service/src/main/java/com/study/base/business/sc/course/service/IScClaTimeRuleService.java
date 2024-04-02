package com.study.base.business.sc.course.service;

import cn.xluobo.business.sc.course.domain.req.time.ReqSearchScClaTimeRule;
import cn.xluobo.business.sc.course.domain.resp.time.RespClaTimeRule;
import cn.xluobo.business.sc.course.repo.model.ScClaTimeRule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 上课时间配置规则 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-14
 */
public interface IScClaTimeRuleService extends IService<ScClaTimeRule> {

    /**
     * 获取具体上课时间
     * 已排序
     * @param reqSearchScClaTimeRule
     * @return 排序后的上课日期 列表
     */
    List<RespClaTimeRule> getClaTimeList(ReqSearchScClaTimeRule reqSearchScClaTimeRule);

    /**
     * 根据 规则获取上课时间
     * 未排序
     * @param ruleId
     * @return
     */
    List<RespClaTimeRule> getClaTimeListByRuleId(Long ruleId);

    /**
     * 根据 规则获取上课时间
     * 未排序
     * @param itemTime
     * @param holidayMap 节假日信息
     * @return
     */
    List<RespClaTimeRule> getClaTimeListByRule(RespClaTimeRule itemTime, Map<String, String> holidayMap);

    /**
     * 根据 规则获取上课时间
     * 未排序
     * @param claTimeRule
     * @param holidayMap 节假日信息
     * @return
     */
    List<RespClaTimeRule> getClaTimeListByRule(ScClaTimeRule claTimeRule, Map<String, String> holidayMap);

    /**
     * 校验是否排课
     * 与 sc_cla_time 待上课进行对比
     * @param claId
     * @param day 日期 yyyy-MM-dd
     * @param notEqRuleId
     * @return 重复日期
     */
    List<String> checkDayRepeat(Long claId, String[] day, Long notEqRuleId);

    /**
     * 校验排课是否重复
     * 与 sc_cla_time 待上课进行对比
     * @param claTimeRule
     * @return 重复日期
     */
    List<String> checkDayRepeat(ScClaTimeRule claTimeRule);

    /**
     * 获取上课时间
     * @param claId
     * @return
     */
    List<String> selectClaTimeInfo(Long claId);

}
