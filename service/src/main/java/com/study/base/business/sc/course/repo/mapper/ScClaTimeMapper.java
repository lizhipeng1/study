package com.study.base.business.sc.course.repo.mapper;

import cn.xluobo.business.sc.course.domain.req.time.ReqClaTimeCount;
import cn.xluobo.business.sc.course.domain.req.time.ReqSearchClaTime;
import cn.xluobo.business.sc.course.domain.resp.time.RespClaTime;
import cn.xluobo.business.sc.course.domain.resp.time.RespClaTimeCalendar;
import cn.xluobo.business.sc.course.repo.model.ScClaTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 排课信息 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-09-16
 */
public interface ScClaTimeMapper extends BaseMapper<ScClaTime> {

    /**
     * 获取排课信息
     *
     * @param searchClaTime
     * @return
     */
    List<RespClaTimeCalendar> selectListForCalendar(ReqSearchClaTime searchClaTime);

    /**
     * 获取上课记录
     *
     * @param searchClaTime
     * @return
     */
    List<RespClaTime> selectListForAttend(@Param("searchClaTime") ReqSearchClaTime searchClaTime, @Param("page") Page page);

    /**
     * 数量
     *
     * @param reqClaTimeCount
     * @return
     */
    Integer selectClaTimeCount(ReqClaTimeCount reqClaTimeCount);

}
