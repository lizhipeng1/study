package com.study.base.business.sc.base.service.impl;

import cn.xluobo.business.sc.base.domain.req.ReqRoomSelect;
import cn.xluobo.business.sc.base.domain.resp.RespRoomSelect;
import cn.xluobo.business.sc.base.repo.mapper.ScRoomMapper;
import cn.xluobo.business.sc.base.repo.model.ScRoom;
import cn.xluobo.business.sc.base.service.IScRoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 教室 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-23 07:36:54
 */
@Service
public class ScRoomServiceImpl extends ServiceImpl<ScRoomMapper, ScRoom> implements IScRoomService {

    @Override
    public List<RespRoomSelect> selectRoomSelect(ReqRoomSelect roomSelect) {
        return baseMapper.selectForSelect(roomSelect);
    }
}
