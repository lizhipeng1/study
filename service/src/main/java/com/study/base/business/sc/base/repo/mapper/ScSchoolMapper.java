package com.study.base.business.sc.base.repo.mapper;

import cn.xluobo.business.sc.base.domain.req.ReqSchoolSelect;
import cn.xluobo.business.sc.base.domain.resp.RespSchoolSelect;
import cn.xluobo.business.sc.base.repo.model.ScSchool;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;

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
