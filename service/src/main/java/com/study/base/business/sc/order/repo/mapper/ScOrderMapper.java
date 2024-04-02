package com.study.base.business.sc.order.repo.mapper;

import cn.xluobo.business.sc.order.domain.req.ReqSearchScOrder;
import cn.xluobo.business.sc.order.domain.resp.RespOrder;
import cn.xluobo.business.sc.order.repo.model.ScOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-08-24 10:22:19
 */
public interface ScOrderMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<ScOrder> {

    List<RespOrder> selectFroSearchTable(@Param("reqSearchScOrder")ReqSearchScOrder reqSearchScOrder, @Param("page")Page page);

    /**
     * 获取欠费学员数量
     * @return
     */
    Integer selectArrearsStudentCount();
}
