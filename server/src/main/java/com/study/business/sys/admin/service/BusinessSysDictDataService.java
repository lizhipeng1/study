package com.study.business.sys.admin.service;

import com.study.business.sys.admin.domain.req.ReqPageSelect;
import com.study.business.sys.admin.domain.req.ReqSearchSysDictData;
import com.study.business.sys.admin.repo.model.SysDictData;
import com.study.business.sys.admin.repo.model.SysDictType;
import com.study.config.login.LoginUser;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.core.page.RespPage;
import com.study.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
public class BusinessSysDictDataService {

    @Autowired
    private ISysDictDataService sysDictDataService;
    @Autowired
    private ISysDictTypeService sysDictTypeService;

    /**
     * 查询列表
     *
     * @param reqSearchDictData
     * @return
     */
    public RespPage<SysDictData> searchList(ReqSearchSysDictData reqSearchDictData) {
        QueryWrapper qw = new QueryWrapper();
        if (StringUtils.isNotEmpty(reqSearchDictData.getDictLabel())) {
            qw.like("dict_label", reqSearchDictData.getDictLabel());
        }
        if (StringUtils.isNotEmpty(reqSearchDictData.getDictType())) {
            qw.eq("dict_type", reqSearchDictData.getDictType());
        }
        if (StringUtils.isNotEmpty(reqSearchDictData.getStatus())) {
            qw.eq("status", reqSearchDictData.getStatus());
        }
        RespPage<SysDictData> page = new RespPage(reqSearchDictData.getPageNum(), reqSearchDictData.getPageSize());
        RespPage<SysDictData> listPage = sysDictDataService.page(page, qw);
        return listPage;
    }

    /**
     * 详情
     *
     * @param dictTypeId
     * @return
     */
    public SysDictData detailById(String dictTypeId) {
        if (StringUtils.isEmpty(dictTypeId)) {
            return null;
        }
        return sysDictDataService.getById(dictTypeId);
    }

    /**
     * 添加
     *
     * @param sysDictData
     * @return
     */
    @CacheEvict(value = "SYS_DICT_DATA", key = "#sysDictData.dictType")
    public boolean addDictData(SysDictData sysDictData) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        sysDictData.setCreateUser(loginUser.getUserId());
        return sysDictDataService.save(sysDictData);
    }

    /**
     * 更新
     *
     * @param sysDictData
     * @return
     */
    @CacheEvict(value = "SYS_DICT_DATA", key = "#sysDictData.dictType")
    public APIResponse updateDictData(SysDictData sysDictData) {
        if (StringUtils.isEmpty(sysDictData.getDictDataId())) {
            return APIResponse.toExceptionResponse(ApiResEnums.BUSINESS_FAILURE);
        }
        SysDictType dbDictType = sysDictTypeService.selectByDictType(sysDictData.getDictType());
        if (!dbDictType.modifiable()) {
            return APIResponse.toExceptionResponse("本数据不允许修改");
        }

        LoginUser loginUser = LoginUserUtil.getLoginUser();
        sysDictData.setLastUpdateUser(loginUser.getUserId());
        sysDictData.setLastUpdateTime(new Date());
        boolean update = sysDictDataService.updateById(sysDictData);
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
        List<SysDictData> dictData = sysDictDataService.listByIds(Arrays.asList(dictTypeIds));
        for (SysDictData dictDatum : dictData) {
            SysDictType dbDictType = sysDictTypeService.selectByDictType(dictDatum.getDictType());
            if (!dbDictType.modifiable()) {
                return APIResponse.toExceptionResponse("本数据不允许修改");
            }
            sysDictDataService.removeCache(dbDictType.getDictType());
        }

        boolean remove = sysDictDataService.removeByIds(Arrays.asList(dictTypeIds));
        if (remove) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 某字典数据
     *
     * @param dictType
     * @return
     */
    public List<SysDictData> dictTypeDataList(String dictType) {
        return sysDictDataService.dictTypeDataList(dictType);
    }

    /**
     * 某字典数据
     * 异步分页查询
     *
     * @param dictType
     * @return
     */
    public RespPage<SysDictData> dictTypeDataList(String dictType, ReqPageSelect reqPageSelect) {
        return sysDictDataService.dictTypeDataList(dictType, reqPageSelect);
    }

    /**
     * 某字典数据
     *
     * @param dictType
     * @return
     */
    public List<SysDictData> dictTypeDataListByParentValue(String dictType, String parentValue) {
        return sysDictDataService.dictTypeDataListByParentValue(dictType, parentValue);
    }
}
