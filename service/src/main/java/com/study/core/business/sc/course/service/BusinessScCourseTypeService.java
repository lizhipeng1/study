package com.study.core.business.sc.course.service;

import cn.xluobo.business.sc.course.domain.req.ReqSearchScCourseType;
import cn.xluobo.business.sc.course.repo.model.ScCourse;
import cn.xluobo.business.sc.course.repo.model.ScCourseType;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.api.ApiResEnums;
import cn.xluobo.core.page.RespPage;
import cn.xluobo.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessScCourseTypeService {

    @Autowired
    private IScCourseTypeService scCourseTypeService;
    @Autowired
    private IScCourseService courseService;

    /**
     * 查询
     *
     * @param reqSearchScCourseType
     * @return
     */
    public APIResponse searchList(ReqSearchScCourseType reqSearchScCourseType) {
        QueryWrapper qw = new QueryWrapper();
        if (StringUtils.isNotEmpty(reqSearchScCourseType.getCourseType())) {
            qw.eq("course_type", reqSearchScCourseType.getCourseType());
        }
        RespPage<ScCourseType> page = new RespPage(reqSearchScCourseType.getPageNum(), reqSearchScCourseType.getPageSize());
        RespPage<ScCourseType> listPage = scCourseTypeService.page(page, qw);
        return APIResponse.toAPIResponse(listPage);
    }

    /**
     * select
     * @param reqSearchScCourseType
     * @return
     */
    public APIResponse select(ReqSearchScCourseType reqSearchScCourseType) {
        QueryWrapper qw = new QueryWrapper();
        qw.select("course_type", "course_type_id");
        if (StringUtils.isNotEmpty(reqSearchScCourseType.getCourseType())) {
            qw.like("course_type", reqSearchScCourseType.getCourseType());
        }
        qw.eq("in_use", "1");
        qw.orderByDesc("create_time");
        List<ScCourseType> list = scCourseTypeService.list(qw);
        return APIResponse.toAPIResponse(list);
    }

    /**
     * 详情
     *
     * @param courseTypeId
     * @return
     */
    public APIResponse detailById(Long courseTypeId) {
        if (null == courseTypeId) {
            return APIResponse.toAPIResponse(null);
        }
        ScCourseType detailInfo = scCourseTypeService.getById(courseTypeId);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param scCourseType
     * @return
     */
    public APIResponse addScCourseType(ScCourseType scCourseType) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        scCourseType.setCreateUser(loginUser.getUserId());
        boolean addScCourseType = scCourseTypeService.save(scCourseType);
        if (addScCourseType) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param scCourseType
     * @return
     */
    public APIResponse updateScCourseType(ScCourseType scCourseType) {
        if (null == scCourseType.getCourseTypeId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        boolean updateScCourseType = scCourseTypeService.updateById(scCourseType);
        if (updateScCourseType) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param courseTypeIds
     * @return
     */
    public APIResponse deleteById(Long[] courseTypeIds) {
        if (null == courseTypeIds || courseTypeIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }

        QueryWrapper<ScCourse> qw = new QueryWrapper<>();
        qw.eq("course_type_id", courseTypeIds);
        int courseCount = courseService.count(qw);
        if (courseCount != 0) {
            return APIResponse.toExceptionResponse("已有课程使用,无法删除");
        }

        boolean deleteScCourseType = scCourseTypeService.removeByIds(Arrays.asList(courseTypeIds));
        if (deleteScCourseType) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }
}
