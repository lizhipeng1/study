package com.study.business.sc.base.service;

import com.study.business.sc.base.domain.req.ReqSchoolSelect;
import com.study.business.sc.base.domain.resp.RespSchoolSelect;
import com.study.business.sc.base.repo.model.ScSchool;

import java.util.List;

/**
 * <p>
 * 学校信息 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-04-27 07:13:36
 */
public interface IScSchoolService extends com.baomidou.mybatisplus.extension.service.IService<ScSchool> {

    /**
     * 学校select
     *
     * @param schoolSelect
     * @return
     */
    List<RespSchoolSelect> selectSchoolSelect(ReqSchoolSelect schoolSelect);

    /**
     * 根据名称获取学校,如果不存在自动保存
     *
     * @param schoolName
     * @return
     */
    Long getSchoolId(String schoolName);
}
