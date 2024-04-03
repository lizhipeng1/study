package com.study.business.sys.admin.service;

import com.study.business.sys.admin.domain.req.ReqSearchSysDictType;
import com.study.business.sys.admin.repo.model.SysDictType;
import com.study.config.login.LoginUser;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.core.page.RespPage;
import com.study.utils.LoginUserUtil;
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
public class BusinessSysDictTypeService {

    @Autowired
    private ISysDictTypeService sysDictTypeService;

    /**
     * 查询列表
     *
     * @param reqSearchDictType
     * @return
     */
    public RespPage<SysDictType> searchList(ReqSearchSysDictType reqSearchDictType) {
        QueryWrapper qw = new QueryWrapper();
        if (StringUtils.isNotEmpty(reqSearchDictType.getDictName())) {
            qw.like("dict_name", reqSearchDictType.getDictName());
        }
        if (StringUtils.isNotEmpty(reqSearchDictType.getDictType())) {
            qw.eq("dict_type", reqSearchDictType.getDictType());
        }
        if (StringUtils.isNotEmpty(reqSearchDictType.getStatus())) {
            qw.eq("status", reqSearchDictType.getStatus());
        }
        if (StringUtils.isNoneEmpty(reqSearchDictType.getBeginTime(), reqSearchDictType.getEndTime())) {
            qw.between("create_time", reqSearchDictType.getBeginTime() + " 00:00:00", reqSearchDictType.getEndTime() + "23:59:59");
        }

        RespPage<SysDictType> page = new RespPage(reqSearchDictType.getPageNum(), reqSearchDictType.getPageSize());
        RespPage<SysDictType> listPage = sysDictTypeService.page(page, qw);
        return listPage;
    }

    /**
     * 查询列表 for select
     *
     * @return
     */
    public List<SysDictType> optionSelect() {
        QueryWrapper qw = new QueryWrapper();
        qw.orderByAsc("create_time");
        List<SysDictType> list = sysDictTypeService.list(qw);
        return list;
    }

    /**
     * 详情
     *
     * @param dictTypeId
     * @return
     */
    public SysDictType detailById(String dictTypeId) {
        if (StringUtils.isEmpty(dictTypeId)) {
            return null;
        }
        return sysDictTypeService.getById(dictTypeId);
    }

    /**
     * 添加
     *
     * @param sysDictType
     * @return
     */
    public boolean addDictType(SysDictType sysDictType) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        sysDictType.setCreateUser(loginUser.getUserId());
        return sysDictTypeService.save(sysDictType);
    }

    /**
     * 更新
     *
     * @param sysDictType
     * @return
     */
    public APIResponse updateDictType(SysDictType sysDictType) {
        if (StringUtils.isEmpty(sysDictType.getDictTypeId())) {
            return APIResponse.toExceptionResponse(ApiResEnums.BUSINESS_FAILURE);
        }
        SysDictType dbDictType = sysDictTypeService.getById(sysDictType.getDictTypeId());
        if (!dbDictType.modifiable()) {
            return APIResponse.toExceptionResponse("本数据不允许修改");
        }

        LoginUser loginUser = LoginUserUtil.getLoginUser();
        sysDictType.setLastUpdateUser(loginUser.getUserId());
        sysDictType.setLastUpdateTime(new Date());
        boolean update = sysDictTypeService.updateById(sysDictType);
        if (update) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param dictTypeIds
     * @return
     */
    public APIResponse deleteById(String[] dictTypeIds) {
        if (null == dictTypeIds || dictTypeIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.BUSINESS_FAILURE);
        }
        List<SysDictType> dbDictTypeList = sysDictTypeService.listByIds(Arrays.asList(dictTypeIds));
        for (SysDictType dbDictType : dbDictTypeList) {
            if (!dbDictType.modifiable()) {
                return APIResponse.toExceptionResponse(dbDictType.getDictName() + "数据不允许修改");
            }
        }
        boolean remove = sysDictTypeService.removeByIds(Arrays.asList(dictTypeIds));
        if (remove) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }
}
