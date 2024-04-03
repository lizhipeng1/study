package com.study.business.sc.course.service;

import com.study.business.sc.course.domain.req.time.ReqClaTimeCount;
import com.study.business.sc.course.repo.model.ScClaTime;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 排课信息 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-16
 */
public interface IScClaTimeService extends IService<ScClaTime> {

    /**
     * 删除 状态为 待上课 的排课信息
     * @param ruleId
     * @param claId
     * @param tenantId
     * @return
     */
    boolean deleteUnBeginTime(Long ruleId, Long claId, String tenantId);

    /**
     * 排课总数量
     * @param reqClaTimeCount
     * @return
     */
    Integer claTimeCount(ReqClaTimeCount reqClaTimeCount);
}
