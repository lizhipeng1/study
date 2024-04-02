package com.study.base.business.sc.course.service;

import cn.xluobo.business.sc.course.repo.model.ScCourseCla;
import cn.xluobo.core.api.APIBaseResponse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程班级信息 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-03-17 01:11:06
 */
public interface IScCourseClaService extends IService<ScCourseCla> {

    /**
     * 班级在读学员数量
     * @param claId
     * @return
     */
    Integer selectStudentCnt(Long claId);

    /**
     * 是否允许变更 班级所属课程
     * @param claId
     * @return
     */
    APIBaseResponse canChangeCourse(Long claId);

}
