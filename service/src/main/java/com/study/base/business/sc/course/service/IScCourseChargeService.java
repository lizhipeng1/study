package com.study.base.business.sc.course.service;

import cn.xluobo.business.sc.course.domain.resp.RespBusinessChooseCourseCharge;
import cn.xluobo.business.sc.course.repo.model.ScCourseCharge;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程收费模式 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-07-08
 */
public interface IScCourseChargeService extends IService<ScCourseCharge> {

    /**
     * 收费模式转换
     * @param courseChargeList
     * @return
     */
    List<RespBusinessChooseCourseCharge> transferCourseChargeList(List<ScCourseCharge> courseChargeList);

    /**
     * 课程收费模式
     * @param courseId
     * @param chargeType
     * @return
     */
    List<RespBusinessChooseCourseCharge> courseChargeList(Long courseId, String chargeType);
}
