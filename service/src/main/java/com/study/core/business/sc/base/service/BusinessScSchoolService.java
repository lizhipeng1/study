package com.study.core.business.sc.base.service;

import cn.xluobo.business.sc.base.domain.req.ReqSchoolSelect;
import cn.xluobo.business.sc.base.domain.req.ReqSearchScSchool;
import cn.xluobo.business.sc.base.domain.resp.RespSchoolSelect;
import cn.xluobo.business.sc.base.repo.model.ScSchool;
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
import java.util.Date;
import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessScSchoolService {

    @Autowired
    private IScSchoolService scSchoolService;

    /**
     * 查询
     *
     * @param reqSearchScSchool
     * @return
     */
    public APIResponse searchList(ReqSearchScSchool reqSearchScSchool) {
        QueryWrapper qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(reqSearchScSchool.getProvinceCode())){
            qw.eq("province_code",reqSearchScSchool.getProvinceCode());
        }
        if(StringUtils.isNotEmpty(reqSearchScSchool.getCityCode())){
            qw.eq("city_code",reqSearchScSchool.getCityCode());
        }
        if(StringUtils.isNotEmpty(reqSearchScSchool.getSchoolName())){
            qw.like("school_name",reqSearchScSchool.getSchoolName());
        }
        RespPage<ScSchool> page = new RespPage(reqSearchScSchool.getPageNum(), reqSearchScSchool.getPageSize());
        RespPage<ScSchool> listPage = scSchoolService.page(page, qw);
        return APIResponse.toAPIResponse(listPage);
    }

    /**
     * 前端select
     *
     * @return
     */
    public APIResponse select(ReqSchoolSelect schoolSelect) {
        List<RespSchoolSelect> list = scSchoolService.selectSchoolSelect(schoolSelect);
        return APIResponse.toAPIResponse(list);
    }

    /**
     * 详情
     *
     * @param schoolId
     * @return
     */
    public APIResponse detailById(Long schoolId) {
        if (null == schoolId) {
            return APIResponse.toAPIResponse(null);
        }
        ScSchool detailInfo = scSchoolService.getById(schoolId);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param scSchool
     * @return
     */
    public APIResponse addScSchool(ScSchool scSchool) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        scSchool.setCreateUser(loginUser.getUserId());
        boolean addScSchool = scSchoolService.save(scSchool);
        if (addScSchool) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param scSchool
     * @return
     */
    public APIResponse updateScSchool(ScSchool scSchool) {
        if (null == scSchool.getSchoolId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        scSchool.setLastUpdateUser(loginUser.getUserId());
        scSchool.setLastUpdateTime(new Date());
        boolean updateScSchool = scSchoolService.updateById(scSchool);
        if (updateScSchool) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param schoolIds
     * @return
     */
    public APIResponse deleteById(Long[] schoolIds) {
        if (null == schoolIds || schoolIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        boolean deleteScSchool = scSchoolService.removeByIds(Arrays.asList(schoolIds));
        if (deleteScSchool) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }
}
