package com.study.business.sc.order.controller;

import com.study.business.sc.order.domain.req.ReqBusinessSignUp;
import com.study.business.sc.order.domain.req.ReqSearchScOrder;
import com.study.business.sc.order.service.BusinessScOrderService;
import com.study.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单 Controller
 * </p>
 *
 * @author zhangby
 * @since 2020-08-24 10:22:19
 */
@RestController
@RequestMapping("/api/sc/order")
public class ScOrderController {
    @Autowired
    private BusinessScOrderService scOrderService;

    /**
     * 列表
     *
     * @param reqSearchScOrder
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(ReqSearchScOrder reqSearchScOrder) {
        return scOrderService.searchList(reqSearchScOrder);
    }

    /**
     * 详情
     *
     * @param orderId
     * @return
     */
    @GetMapping("/info/detailById/{orderId}")
    public APIResponse detailById(@PathVariable("orderId") Long orderId) {
        return scOrderService.detailById(orderId);
    }

    /**
     * 新办
     *
     * @param reqBusinessSignUp
     * @return
     */
    @PostMapping("/add/signUp")
    public APIResponse signUp(@RequestBody ReqBusinessSignUp reqBusinessSignUp) {
        return scOrderService.signUp(reqBusinessSignUp);
    }

    /**
     * 作废订单
     *
     * @param orderIds
     * @return
     */
    @PostMapping("/delete/invalidById/{orderIds}")
    public APIResponse invalidById(@PathVariable("orderIds") Long[] orderIds) {
        return scOrderService.invalidById(orderIds);
    }
}
