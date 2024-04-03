package com.study.business.sc.course.service.impl;

import com.study.business.sc.course.domain.req.ReqSearchScCourse;
import com.study.business.sc.course.domain.resp.course.RespSearchCourse;
import com.study.business.sc.course.repo.mapper.ScCourseChargeMapper;
import com.study.business.sc.course.repo.model.ScCourse;
import com.study.business.sc.course.repo.mapper.ScCourseMapper;
import com.study.business.sc.course.service.IScCourseService;
import com.study.core.page.RespPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
