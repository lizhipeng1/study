package com.study.core.business.sc.base.service;

import cn.xluobo.business.sc.base.domain.req.ReqRoomSelect;
import cn.xluobo.business.sc.base.domain.req.ReqSearchScRoom;
import cn.xluobo.business.sc.base.domain.resp.RespRoomInfo;
import cn.xluobo.business.sc.base.domain.resp.RespRoomSelect;
import cn.xluobo.business.sc.base.repo.mapper.ScRoomMapper;
import cn.xluobo.business.sc.base.repo.model.ScRoom;
import cn.xluobo.business.sc.course.repo.model.ScClaTime;
import cn.xluobo.business.sc.course.service.IScClaTimeService;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.api.ApiResEnums;
import cn.xluobo.core.page.RespPage;
import cn.xluobo.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessScRoomService {

    @Autowired
    private IScRoomService scRoomService;
    @Autowired
    private ScRoomMapper roomMapper;
    @Autowired
    private IScClaTimeService claTimeService;

    /**
     * 查询
     *
     * @param reqSearchScRoom
     * @return
     */
    public APIResponse searchList(ReqSearchScRoom reqSearchScRoom) {
        RespPage<RespRoomInfo> page = new RespPage<>(reqSearchScRoom.getPageNum(), reqSearchScRoom.getPageSize());
        List<RespRoomInfo> roomSelectList = roomMapper.selectList(reqSearchScRoom, page);
        page.setRows(roomSelectList);
        return APIResponse.toAPIResponse(page);
    }

    /**
     * 前端select
     *
     * @return
     */
    public APIResponse select(ReqRoomSelect roomSelect) {
        List<RespRoomSelect> list = scRoomService.selectRoomSelect(roomSelect);
        return APIResponse.toAPIResponse(list);
    }

    /**
     * 详情
     *
     * @param roomId
     * @return
     */
    public APIResponse detailById(Long roomId) {
        if (null == roomId) {
            return APIResponse.toAPIResponse(null);
        }
        ScRoom detailInfo = scRoomService.getById(roomId);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param scRoom
     * @return
     */
    public APIResponse addScRoom(ScRoom scRoom) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        scRoom.setCreateUser(loginUser.getUserId());
        boolean addScRoom = scRoomService.save(scRoom);
        if (addScRoom) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param scRoom
     * @return
     */
    public APIResponse updateScRoom(ScRoom scRoom) {
        if (null == scRoom.getRoomId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        scRoom.setLastUpdateUser(loginUser.getUserId());
        scRoom.setLastUpdateTime(new Date());
        boolean updateScRoom = scRoomService.updateById(scRoom);
        if (updateScRoom) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param roomIds
     * @return
     */
    public APIResponse deleteById(Long[] roomIds) {
        if (null == roomIds || roomIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }

        // 教室有 排课未上课 不允许删除
        QueryWrapper<ScClaTime> qw = new QueryWrapper<>();
        qw.in("room_id", roomIds);
        qw.eq("status", "1");
        int unBeginClaTimeCount = claTimeService.count(qw);
        if (unBeginClaTimeCount != 0) {
            return APIResponse.toExceptionResponse("该教室有'"+unBeginClaTimeCount+"'待上课的排课,无法删除教室");
        }

        boolean deleteScRoom = scRoomService.removeByIds(Arrays.asList(roomIds));
        if (deleteScRoom) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }
}
