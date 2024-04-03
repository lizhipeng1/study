package com.study.business.sc.course.service;

import com.study.business.sc.course.domain.req.ReqSearchScCourse;
import com.study.business.sc.course.domain.resp.course.RespSearchCourse;
import com.study.business.sc.course.repo.model.ScCourse;
import com.study.core.page.RespPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程信息 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-03-17 11:25:55
 */
public interface IScCourseService extends IService<ScCourse> {

    /**
     * 查询课程列表
     * @param reqSearchScCourse
     * @return
     */
    RespPage<RespSearchCourse> searchCourse(ReqSearchScCourse reqSearchScCourse);
}
