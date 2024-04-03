package com.study.business.sc.course.service.impl;

import com.study.business.sc.course.repo.enums.ClaTimeStatusEnums;
import com.study.business.sc.course.repo.mapper.ScCourseClaMapper;
import com.study.business.sc.course.repo.model.ScClaTime;
import com.study.business.sc.course.repo.model.ScCourseCla;
import com.study.business.sc.course.service.IScClaTimeService;
import com.study.business.sc.course.service.IScCourseClaService;
import com.study.business.sc.student.repo.model.ScStudentCourse;
import com.study.business.sc.student.service.IScStudentCourseService;
import com.study.core.api.APIBaseResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程班级信息 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-03-17 01:11:06
 */
@Service
public class ScCourseClaServiceImpl extends ServiceImpl<ScCourseClaMapper, ScCourseCla> implements IScCourseClaService {

    @Autowired
    private IScStudentCourseService studentCourseService;
    @Autowired
    private IScClaTimeService claTimeService;

    @Override
    public Integer selectStudentCnt(Long claId) {
        return baseMapper.selectStudentCnt(claId);
    }

    @Override
    public APIBaseResponse canChangeCourse(Long claId) {
        QueryWrapper<ScStudentCourse> qw = new QueryWrapper<>();
        qw.eq("cla_id", claId);
        int count = studentCourseService.count(qw);
        if (count != 0) {
            return APIBaseResponse.fail("当前班级有报读学员,无法变更所属课程.");
        }

        QueryWrapper<ScClaTime> qwClaTime = new QueryWrapper<>();
        qwClaTime.eq("cla_id", claId);
        qwClaTime.eq("status", ClaTimeStatusEnums.HAD_CLASS.getStatus());
        int hadClaTimeCount = claTimeService.count(qwClaTime);
        if (hadClaTimeCount != 0) {
            return APIBaseResponse.fail("当前班级已有上课记录,无法变更所属课程.");
        }
        return APIBaseResponse.success();
    }
}
