package com.study.business.wechat.cp.service;

import com.study.business.wechat.cp.domain.req.ReqSearchWechatCpGroupTag;
import com.study.business.wechat.cp.repo.model.WechatCpGroup;
import com.study.business.wechat.cp.repo.model.WechatCpGroupTag;
import com.study.config.login.LoginUser;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.core.select.GroupSelect;
import com.study.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.study.core.page.RespPage;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessWechatCpGroupTagService {

    @Autowired
    private IWechatCpGroupService wechatCpGroupService;
    @Autowired
    private IWechatCpGroupTagService wechatCpGroupTagService;

    /**
     * 查询
     *
     * @param reqSearchWechatCpGroupTag
     * @return
     */
    public APIResponse searchList(ReqSearchWechatCpGroupTag reqSearchWechatCpGroupTag) {
        QueryWrapper qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(reqSearchWechatCpGroupTag.getGroupId())){
            qw.eq("group_id",reqSearchWechatCpGroupTag.getGroupId());
        }
        RespPage<WechatCpGroupTag> page = new RespPage(reqSearchWechatCpGroupTag.getPageNum(), reqSearchWechatCpGroupTag.getPageSize());
        RespPage<WechatCpGroupTag> listPage = wechatCpGroupTagService.page(page, qw);
        return APIResponse.toAPIResponse(listPage);
    }

    /**
     * group select
     *
     * @return
     */
    public APIResponse groupSelect() {
        // 全部group
        QueryWrapper<WechatCpGroup> qwGroup = new QueryWrapper<>();
        qwGroup.eq("delete_flag", "0");
        List<WechatCpGroup> groupList = wechatCpGroupService.list(qwGroup);

        // group id name map
        Map<String, String> groupMap = Maps.newHashMap();
        for (WechatCpGroup wechatCpGroup : groupList) {
            groupMap.put(wechatCpGroup.getGroupId(), wechatCpGroup.getGroupName());
        }

        // 全部tag
        QueryWrapper<WechatCpGroupTag> qw = new QueryWrapper<>();
        qw.eq("delete_flag", "0");
        List<WechatCpGroupTag> groupTagList = wechatCpGroupTagService.list(qw);

        // 转换为map
        Map<String, List<WechatCpGroupTag>> groupTagMap = Maps.newHashMap();
        for (WechatCpGroupTag groupTag : groupTagList) {
            if (!groupTagMap.containsKey(groupTag.getGroupId())) {
                groupTagMap.put(groupTag.getGroupId(), Lists.newArrayList());
            }
            groupTagMap.get(groupTag.getGroupId()).add(groupTag);
        }

        // 转换为前段需要的格式
        List<GroupSelect> groupSelectList = groupTagMap.keySet().stream().map(groupKey -> {
            GroupSelect groupSelect = new GroupSelect();
            groupSelect.setGroupName(groupMap.get(groupKey));

            List<GroupSelect.GroupItem> groupItemList = groupTagMap.get(groupKey).stream().map(WechatCpGroupTag::transferGroupSelectItem).collect(Collectors.toList());
            groupSelect.setItemList(groupItemList);

            return groupSelect;
        }).collect(Collectors.toList());


        return APIResponse.toAPIResponse(groupSelectList);
    }

    /**
     * 详情
     *
     * @param tagId
     * @return
     */
    public APIResponse detailById(String tagId) {
        if (null == tagId) {
            return APIResponse.toAPIResponse(null);
        }
        WechatCpGroupTag detailInfo = wechatCpGroupTagService.getById(tagId);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param wechatCpGroupTag
     * @return
     */
    public APIResponse addWechatCpGroupTag(WechatCpGroupTag wechatCpGroupTag) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        wechatCpGroupTag.setCreateUser(loginUser.getUserId());
        boolean addWechatCpGroupTag = wechatCpGroupTagService.save(wechatCpGroupTag);
        if (addWechatCpGroupTag) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param wechatCpGroupTag
     * @return
     */
    public APIResponse updateWechatCpGroupTag(WechatCpGroupTag wechatCpGroupTag) {
        if (null == wechatCpGroupTag.getTagId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        wechatCpGroupTag.setLastUpdateUser(loginUser.getUserId());
        wechatCpGroupTag.setLastUpdateTime(new Date());
        boolean updateWechatCpGroupTag = wechatCpGroupTagService.updateById(wechatCpGroupTag);
        if (updateWechatCpGroupTag) {
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
    public APIResponse deleteById(String[] tagIds) {
        if (null == tagIds || tagIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        boolean deleteWechatCpGroupTag = wechatCpGroupTagService.removeByIds(Arrays.asList(tagIds));
        if (deleteWechatCpGroupTag) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }
}
