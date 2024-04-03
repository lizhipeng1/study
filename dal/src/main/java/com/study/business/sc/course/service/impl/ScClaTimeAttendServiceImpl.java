package com.study.business.sc.course.service.impl;

import com.study.business.sc.course.domain.req.time.ReqClaTimeCount;
import com.study.business.sc.course.repo.model.ScClaTimeAttend;
import com.study.business.sc.course.repo.mapper.ScClaTimeAttendMapper;
import com.study.business.sc.course.service.IScClaTimeAttendService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 上课出勤表 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-30 02:33:26
 */
@Service
public class ScClaTimeAttendServiceImpl extends ServiceImpl<ScClaTimeAttendMapper, ScClaTimeAttend> implements IScClaTimeAttendService {

    @Override
    public int studentCourseAttendCount(Long studentCourseId, String beginDate, String endDate) {
        return baseMapper.selectStudentAttendCount(studentCourseId, beginDate, endDate);
    }

    @Override
    public Integer getNeedAttendCount(String beginDate, String endDate) {
        return baseMapper.selectNeedAttendCount(beginDate, endDate);
    }

    @Override
    public Integer getAttendCount(String beginDate, String endDate, String[] attendStatus) {
        return baseMapper.selectAttendCount(beginDate, endDate, attendStatus);
    }

    @Override
    public BigDecimal getAttendCostHour(String beginDate, String endDate, boolean needAttend) {
        if(needAttend) {
            return baseMapper.selectNeedAttendCostHour(beginDate, endDate);
        } else {
            return baseMapper.selectAttendCostHour(beginDate, endDate);
        }
    }

    @Override
    public BigDecimal getTeacherGetHour(ReqClaTimeCount reqClaTimeCount) {
        return baseMapper.selectTeacherSumGetHour(reqClaTimeCount);
    }

    @Override
    public List<ScClaTimeAttend> getAttendList(Long courseTimeId) {
        QueryWrapper<ScClaTimeAttend> qwAttend = new QueryWrapper<>();
        qwAttend.eq("course_time_id", courseTimeId);
        return this.list(qwAttend);
    }
}
