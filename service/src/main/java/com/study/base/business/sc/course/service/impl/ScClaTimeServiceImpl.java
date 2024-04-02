package com.study.base.business.sc.course.service.impl;

import cn.xluobo.business.sc.course.domain.req.time.ReqClaTimeCount;
import cn.xluobo.business.sc.course.repo.mapper.ScClaTimeMapper;
import cn.xluobo.business.sc.course.repo.model.ScClaTime;
import cn.xluobo.business.sc.course.service.IScClaTimeService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 排课信息 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-16
 */
@Service
public class ScClaTimeServiceImpl extends ServiceImpl<ScClaTimeMapper, ScClaTime> implements IScClaTimeService {

    @Override
    public boolean deleteUnBeginTime(Long ruleId, Long claId, String tenantId) {
        UpdateWrapper uw = new UpdateWrapper();
        uw.eq("rule_id", ruleId);
        uw.eq("cla_id", claId);
        uw.in("status", "1");
        uw.exists("select 1 from sc_course_cla b where sc_cla_time.cla_id = b.cla_id and b.tenant_id='" + tenantId + "'");
        return this.remove(uw);
    }

    @Override
    public Integer claTimeCount(ReqClaTimeCount reqClaTimeCount) {
        return baseMapper.selectClaTimeCount(reqClaTimeCount);
    }
}
