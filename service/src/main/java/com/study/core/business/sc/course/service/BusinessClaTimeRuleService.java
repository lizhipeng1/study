package com.study.core.business.sc.course.service;

import cn.xluobo.business.sc.base.repo.model.ScRoom;
import cn.xluobo.business.sc.base.service.IScRoomService;
import cn.xluobo.business.sc.course.domain.req.time.ReqSearchScClaTimeRule;
import cn.xluobo.business.sc.course.domain.resp.time.RespClaTimeRule;
import cn.xluobo.business.sc.course.repo.mapper.ScClaTimeRuleMapper;
import cn.xluobo.business.sc.course.repo.model.ScClaTime;
import cn.xluobo.business.sc.course.repo.model.ScClaTimeRule;
import cn.xluobo.business.sc.course.repo.model.ScCourseCla;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.api.ApiResEnums;
import cn.xluobo.core.page.RespPage;
import cn.xluobo.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/18 15:38
 */
@Service
@Transactional
public class BusinessClaTimeRuleService {

    @Autowired
    private IScClaTimeService claTimeService;
    @Autowired
    private IScClaTimeRuleService claTimeRuleService;
    @Autowired
    private ScClaTimeRuleMapper claTimeRuleMapper;
    @Autowired
    private IScCourseClaService courseClaService;
    @Autowired
    private IScRoomService roomService;

    /**
     * 查询
     *
     * @param reqSearchScClaTimeRule
     * @return
     */
    public APIResponse searchList(ReqSearchScClaTimeRule reqSearchScClaTimeRule) {
        RespPage<RespClaTimeRule> respPage = new RespPage<>(reqSearchScClaTimeRule.getPageNum(),reqSearchScClaTimeRule.getPageSize());
        List<RespClaTimeRule> claTimeRuleList = claTimeRuleMapper.selectByCondition(reqSearchScClaTimeRule, respPage);
        respPage.setRows(claTimeRuleList);
        return APIResponse.toAPIResponse(respPage);
    }

    /**
     * 添加
     *
     * @param claTimeRule
     * @return
     */
    public APIResponse addClaTimeRule(ScClaTimeRule claTimeRule) {
        if (!claTimeRule.checkParam()) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }
        // 教室
        if(null != claTimeRule.getRoomId()) {
            ScRoom room = roomService.getById(claTimeRule.getRoomId());
            claTimeRule.setRoomName(room.getRoomName());
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        claTimeRule.setCreateUser(loginUser.getUserId());
        claTimeRule.setCreateTime(new Date());
        claTimeRuleService.save(claTimeRule);
        saveBatchClaTime(claTimeRule);
        return APIResponse.toOkResponse();
    }

    /**
     * 详情
     *
     * @param ruleId
     * @return
     */
    public APIResponse detailById(Long ruleId) {
        if (null == ruleId) {
            return APIResponse.toAPIResponse(null);
        }
        ScClaTimeRule claTimeRule = claTimeRuleService.getById(ruleId);
        // 设置deptId
        Long claId = claTimeRule.getClaId();
        ScCourseCla courseCla = courseClaService.getById(claId);
        claTimeRule.setDeptId(courseCla.getDepartId());

        return APIResponse.toAPIResponse(claTimeRule);
    }


    /**
     * 更新
     *
     * @param claTimeRule
     * @return
     */
    public APIResponse updateClaTimeRule(ScClaTimeRule claTimeRule) {
        if (null == claTimeRule.getRuleId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }
        if (!claTimeRule.checkParam()) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }
        // 教室
        if(null != claTimeRule.getRoomId()) {
            ScRoom room = roomService.getById(claTimeRule.getRoomId());
            claTimeRule.setRoomName(room.getRoomName());
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        claTimeRule.setLastUpdateUser(loginUser.getUserId());
        claTimeRule.setLastUpdateTime(new Date());
        boolean updateScCourseType = claTimeRuleService.updateById(claTimeRule);
        claTimeService.deleteUnBeginTime(claTimeRule.getRuleId(), claTimeRule.getClaId(), loginUser.getNowTenantId());

        saveBatchClaTime(claTimeRule);
        return APIResponse.toOkResponse();
    }

    /**
     * 删除
     *
     * @param ruleId
     * @return
     */
    public APIResponse deleteById(Long ruleId) {
        if (null == ruleId) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        ScClaTimeRule claTimeRule = claTimeRuleService.getById(ruleId);

        claTimeRuleService.removeById(ruleId);

        LoginUser loginUser = LoginUserUtil.getLoginUser();
        claTimeService.deleteUnBeginTime(claTimeRule.getRuleId(), claTimeRule.getClaId(), loginUser.getNowTenantId());

        return APIResponse.toOkResponse();
    }

    /**
     * 保存 具体上课日期
     *
     * @param claTimeRule
     */
    private void saveBatchClaTime(ScClaTimeRule claTimeRule) {
        Long claId = claTimeRule.getClaId();
        ScCourseCla courseCla = courseClaService.getById(claId);
        BigDecimal everyStuLoseHour = courseCla.getEveryStuLoseHour();

        List<RespClaTimeRule> claTimeList = claTimeRuleService.getClaTimeListByRule(claTimeRule, null);

        List<ScClaTime> timeList = claTimeList.stream().map(item -> {
            ScClaTime claTime = new ScClaTime();
            claTime.setRuleId(claTimeRule.getRuleId());
            claTime.setClaId(claTimeRule.getClaId());
            claTime.setClaDate(item.getClaDate());
            claTime.setStartTime(item.getClaTimeBegin());
            claTime.setEndTime(item.getClaTimeEnd());
            claTime.setPayHour(everyStuLoseHour);
            // 排课
            claTime.setSource("1");
            // 待上课
            claTime.setStatus("1");
            claTime.setRoomId(claTimeRule.getRoomId());
            claTime.setRoomName(claTimeRule.getRoomName());
            claTime.setClassTheme(claTimeRule.getClassTheme());
            claTime.setTeacherId(claTimeRule.getTeacherId());
            claTime.setCreateUser(claTimeRule.getCreateUser());
            return claTime;
        }).collect(Collectors.toList());
        claTimeService.saveBatch(timeList);
    }

}
