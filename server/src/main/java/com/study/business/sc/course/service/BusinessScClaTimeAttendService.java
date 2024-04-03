package com.study.business.sc.course.service;

import com.study.business.sc.course.domain.req.time.ReqSearchScClaTimeAttend;
import com.study.business.sc.course.domain.resp.time.RespClaTimeAttend;
import com.study.business.sc.course.repo.mapper.ScClaTimeAttendMapper;
import com.study.business.sc.course.repo.model.ScClaTimeAttend;
import com.study.config.login.LoginUser;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.core.page.RespPage;
import com.study.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class BusinessScClaTimeAttendService {

    @Autowired
    private IScClaTimeAttendService scClaTimeAttendService;

    @Autowired
    private ScClaTimeAttendMapper attendMapper;

    /**
     * 查询
     *
     * @param reqSearchScClaTimeAttend
     * @return
     */
    public RespPage<RespClaTimeAttend> searchList(ReqSearchScClaTimeAttend reqSearchScClaTimeAttend) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        reqSearchScClaTimeAttend.setTenantId(loginUser.getNowTenantId());
        RespPage<RespClaTimeAttend> page = new RespPage(reqSearchScClaTimeAttend.getPageNum(), reqSearchScClaTimeAttend.getPageSize());
        List<RespClaTimeAttend> attendList = attendMapper.selectTimeAttendList(reqSearchScClaTimeAttend, page);
        page.setRows(attendList);
        return page;
    }

    /**
     * 已上课 出席详情
     *
     * @param courseTimeId
     * @return
     */
    public APIResponse hadClaTimeAttendDetail(Long courseTimeId) {
        QueryWrapper<ScClaTimeAttend> qwAttend = new QueryWrapper<>();
        qwAttend.select("student_course_id", "attend_status", "memo", "pay_hour");
        qwAttend.eq("course_time_id", courseTimeId);
        List<ScClaTimeAttend> claTimeAttendList = scClaTimeAttendService.list(qwAttend);
        return APIResponse.toAPIResponse(claTimeAttendList);
    }

    /**
     * 详情
     *
     * @param attendId
     * @return
     */
    public APIResponse detailById(Long attendId) {
        if (null == attendId) {
            return APIResponse.toAPIResponse(null);
        }
        ScClaTimeAttend detailInfo = scClaTimeAttendService.getById(attendId);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param scClaTimeAttend
     * @return
     */
    public APIResponse addScClaTimeAttend(ScClaTimeAttend scClaTimeAttend) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        scClaTimeAttend.setCreateUser(loginUser.getUserId());
        boolean addScClaTimeAttend = scClaTimeAttendService.save(scClaTimeAttend);
        if (addScClaTimeAttend) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param scClaTimeAttend
     * @return
     */
    public APIResponse updateScClaTimeAttend(ScClaTimeAttend scClaTimeAttend) {
        if (null == scClaTimeAttend.getAttendId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        boolean updateScClaTimeAttend = scClaTimeAttendService.updateById(scClaTimeAttend);
        if (updateScClaTimeAttend) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param attendIds
     * @return
     */
    public APIResponse deleteById(Long[] attendIds) {
        if (null == attendIds || attendIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        boolean deleteScClaTimeAttend = scClaTimeAttendService.removeByIds(Arrays.asList(attendIds));
        if (deleteScClaTimeAttend) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }
}
