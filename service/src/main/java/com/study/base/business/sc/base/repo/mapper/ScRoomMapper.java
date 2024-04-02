package com.study.base.business.sc.base.repo.mapper;

import cn.xluobo.business.sc.base.domain.req.ReqRoomSelect;
import cn.xluobo.business.sc.base.domain.req.ReqSearchScRoom;
import cn.xluobo.business.sc.base.domain.resp.RespRoomInfo;
import cn.xluobo.business.sc.base.domain.resp.RespRoomSelect;
import cn.xluobo.business.sc.base.repo.model.ScRoom;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 教室 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-09-23 07:36:54
 */
public interface ScRoomMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<ScRoom> {


    /**
     * 根据条件 获取教室
     * @param reqSearchScRoom
     * @param page
     * @return
     */
    List<RespRoomInfo> selectList(@Param("reqSearchScRoom")ReqSearchScRoom reqSearchScRoom, @Param("page")Page page);

    /**
     * 查询
     *
     * @param roomSelect
     * @return
     */
    List<RespRoomSelect> selectForSelect(ReqRoomSelect roomSelect);

}
