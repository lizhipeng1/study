package com.study.core.business.sc.base.controller;

import cn.xluobo.business.sc.base.domain.req.ReqRoomSelect;
import cn.xluobo.business.sc.base.domain.req.ReqSearchScRoom;
import cn.xluobo.business.sc.base.repo.model.ScRoom;
import cn.xluobo.business.sc.base.service.BusinessScRoomService;
import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 教室 Controller
 * </p>
 *
 * @author zhangby
 * @since 2020-09-23 07:36:54
 */
@RestController
@RequestMapping("/api/sc/room")
public class ScRoomController {
    @Autowired
    private BusinessScRoomService scRoomService;

    /**
     * 列表
     *
     * @param reqSearchScRoom
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchScRoom reqSearchScRoom) {
        return scRoomService.searchList(reqSearchScRoom);
    }

    /**
     * 前端select
     *
     * @return
     */
    @GetMapping("/list/select")
    public APIResponse select(ReqRoomSelect roomSelect) {
        return scRoomService.select(roomSelect);
    }

    /**
     * 详情
     *
     * @param roomId
     * @return
     */
    @GetMapping("/info/detailById/{roomId}")
    public APIResponse detailById(@PathVariable("roomId") Long roomId) {
        return scRoomService.detailById(roomId);
    }

    /**
     * 添加
     *
     * @param scRoom
     * @return
     */
    @PostMapping("/add/addScRoom")
    public APIResponse addScRoom(@RequestBody ScRoom scRoom) {
        return scRoomService.addScRoom(scRoom);
    }

    /**
     * 修改
     *
     * @param scRoom
     * @return
     */
    @PutMapping("/update/updateScRoom")
    public APIResponse updateScRoom(@RequestBody ScRoom scRoom) {
        return scRoomService.updateScRoom(scRoom);
    }

    /**
     * 删除
     *
     * @param roomIds
     * @return
     */
    @DeleteMapping("/delete/deleteById/{roomIds}")
    public APIResponse deleteById(@PathVariable("roomIds") Long[] roomIds) {
        return scRoomService.deleteById(roomIds);
    }
}
