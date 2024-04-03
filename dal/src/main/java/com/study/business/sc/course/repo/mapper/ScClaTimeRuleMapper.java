package com.study.business.sc.course.repo.mapper;

import com.study.business.sc.course.domain.req.time.ReqSearchScClaTimeRule;
import com.study.business.sc.course.domain.resp.time.RespClaTimeRule;
import com.study.business.sc.course.repo.model.ScClaTimeRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 上课时间配置规则 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-09-14
 */
public interface ScClaTimeRuleMapper extends BaseMapper<ScClaTimeRule> {

    /**
     * 获取一定时间范围内的排课信息
     *
     * @param reqSearchScClaTimeRule
     * @param page
     * @return
     */
    List<RespClaTimeRule> selectByCondition(@Param("reqSearchScClaTimeRule") ReqSearchScClaTimeRule reqSearchScClaTimeRule, @Param("page") Page page);

    /**
     * 获取制定日期的排课信息
     * @param claId
     * @param day
     * @return
     */
    List<RespClaTimeRule> selectByDay(@Param("claId")Long claId, @Param("day")String day);

    /**
     * 根据id获取规则
     * @param ruleId
     * @return
     */
    RespClaTimeRule selectByRuleId(Long ruleId);

    /**
     * 获取班级上课时间
     * @param claId
     * @return
     */
    List<String> selectClaTimeInfo(Long claId);
}
