package com.study.business.sc.base.service;

import com.study.business.sc.base.domain.req.ReqRoomSelect;
import com.study.business.sc.base.domain.resp.RespRoomSelect;
import com.study.business.sc.base.repo.model.ScRoom;

import java.util.List;

/**
 * <p>
 * 教室 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-23 07:36:54
 */
public interface IScRoomService extends com.baomidou.mybatisplus.extension.service.IService<ScRoom> {

    /**
     * 教室 select
     * @param roomSelect
     * @return
     */
    List<RespRoomSelect> selectRoomSelect(ReqRoomSelect roomSelect);
}
