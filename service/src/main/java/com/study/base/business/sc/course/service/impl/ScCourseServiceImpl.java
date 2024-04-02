package com.study.base.business.sc.course.service.impl;

import cn.xluobo.business.sc.course.domain.req.ReqSearchScCourse;
import cn.xluobo.business.sc.course.domain.resp.course.RespSearchCourse;
import cn.xluobo.business.sc.course.enums.CourseChargeTypeEnum;
import cn.xluobo.business.sc.course.repo.mapper.ScCourseChargeMapper;
import cn.xluobo.business.sc.course.repo.model.ScCourse;
import cn.xluobo.business.sc.course.repo.mapper.ScCourseMapper;
import cn.xluobo.business.sc.course.repo.model.ScCourseCharge;
import cn.xluobo.business.sc.course.service.IScCourseService;
import cn.xluobo.business.sc.student.repo.model.ScStudentCourse;
import cn.xluobo.core.api.APIBaseResponse;
import cn.xluobo.core.page.RespPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程信息 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-03-17 11:25:55
 */
@Service
public class ScCourseServiceImpl extends ServiceImpl<ScCourseMapper, ScCourse> implements IScCourseService {

    @Autowired
    private ScCourseMapper courseMapper;
    @Autowired
    private ScCourseChargeMapper courseChargeMapper;

    @Override
    public RespPage<RespSearchCourse> searchCourse(ReqSearchScCourse reqSearchScCourse) {
        RespPage respPage = new RespPage(reqSearchScCourse.getPageNum(),reqSearchScCourse.getPageSize());
        List<RespSearchCourse> courseList = baseMapper.selectCourseList(reqSearchScCourse, respPage);
        respPage.setRows(courseList);
        return respPage;
    }
}
