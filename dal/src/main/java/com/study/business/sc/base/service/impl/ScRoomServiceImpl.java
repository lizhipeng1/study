package com.study.business.sc.base.service.impl;

import com.study.business.sc.base.domain.req.ReqRoomSelect;
import com.study.business.sc.base.domain.resp.RespRoomSelect;
import com.study.business.sc.base.repo.mapper.ScRoomMapper;
import com.study.business.sc.base.repo.model.ScRoom;
import com.study.business.sc.base.service.IScRoomService;
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
