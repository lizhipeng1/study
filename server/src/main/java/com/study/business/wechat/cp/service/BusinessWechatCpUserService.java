package com.study.business.wechat.cp.service;

import com.study.business.sc.course.domain.req.ReqSelect;
import com.study.business.wechat.cp.repo.model.WechatCpUser;
import com.study.core.api.APIResponse;
import com.study.core.page.RespPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @projectName: qyxt
 * @package: com.study.business.wechat.cp.service
 * @className: BusinessWechatCpUserService
 * @author: xluobo
 * @description: TODO
 * @date: 2024/1/30 00:30
 */
@Service
@Transactional
public class BusinessWechatCpUserService {

    @Autowired
    private IWechatCpUserService cpUserService;

    /**
     * 企业微信用户select
     *
     * @param reqSelect
     * @return
     */
    public APIResponse cpUserSelect(ReqSelect reqSelect) {
        RespPage<WechatCpUser> page = new RespPage(reqSelect.getPageNum(), reqSelect.getPageSize());

        QueryWrapper<WechatCpUser> qw = new QueryWrapper<>();
        /*if (StringUtils.isNotEmpty(reqSelect.getSearch())) {
            qw.and(i -> i.like("userid", reqSelect.getSearch()).or().like("name", reqSelect.getSearch()));
        }*/
        RespPage<WechatCpUser> respPage = cpUserService.page(page, qw);
        return APIResponse.toAPIResponse(respPage);
    }
}
