package com.study.core.business.sys.tag.service;

import cn.xluobo.business.sys.tag.domain.req.ReqSearchSysTag;
import cn.xluobo.business.sys.tag.repo.model.SysTag;
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

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessSysTagService {

    @Autowired
    private ISysTagService sysTagService;

    /**
     * 查询
     *
     * @param reqSearchSysTag
     * @return
     */
    public APIResponse searchList(ReqSearchSysTag reqSearchSysTag) {
        QueryWrapper qw = new QueryWrapper();
        qw.select("tag_id", "tag_name", "tag_type", "create_time");
        if (StringUtils.isNotEmpty(reqSearchSysTag.getTenantId())) {
            qw.eq("tenant_id", reqSearchSysTag.getTenantId());
        }
        if (StringUtils.isNotEmpty(reqSearchSysTag.getTagName())) {
            qw.like("tag_name", reqSearchSysTag.getTagName());
        }
        if (StringUtils.isNotEmpty(reqSearchSysTag.getTagType())) {
            qw.eq("tag_type", reqSearchSysTag.getTagType());
        }
        RespPage<SysTag> page = new RespPage(reqSearchSysTag.getPageNum(), reqSearchSysTag.getPageSize());
        RespPage<SysTag> listPage = sysTagService.page(page, qw);
        return APIResponse.toAPIResponse(listPage);
    }

    /**
     * 详情
     *
     * @param tagId
     * @return
     */
    public APIResponse detailById(Long tagId) {
        if (null == tagId) {
            return APIResponse.toAPIResponse(null);
        }
        SysTag detailInfo = sysTagService.getById(tagId);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param sysTag
     * @return
     */
    public APIResponse addSysTag(SysTag sysTag) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        sysTag.setCreateUser(loginUser.getUserId());
        boolean addSysTag = sysTagService.save(sysTag);
        if (addSysTag) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param sysTag
     * @return
     */
    public APIResponse updateSysTag(SysTag sysTag) {
        if (null == sysTag.getTagId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        sysTag.setLastUpdateUser(loginUser.getUserId());
        sysTag.setLastUpdateTime(new Date());
        boolean updateSysTag = sysTagService.updateById(sysTag);
        if (updateSysTag) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param tagIds
     * @return
     */
    public APIResponse deleteById(Long[] tagIds) {
        if (null == tagIds || tagIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        boolean deleteSysTag = sysTagService.removeByIds(Arrays.asList(tagIds));
        if (deleteSysTag) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }
}
