package com.study.business.sc.base.repo.mapper;

import com.study.business.sc.base.domain.req.ReqSchoolSelect;
import com.study.business.sc.base.domain.resp.RespSchoolSelect;
import com.study.business.sc.base.repo.model.ScSchool;

import java.util.List;

/**
 * <p>
 * 学校信息 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-04-27 07:13:36
 */
public interface ScSchoolMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<ScSchool> {

    /**
     * 查询
     *
     * @param schoolSelect
     * @return
     */
    List<RespSchoolSelect> selectForSelect(ReqSchoolSelect schoolSelect);

}
