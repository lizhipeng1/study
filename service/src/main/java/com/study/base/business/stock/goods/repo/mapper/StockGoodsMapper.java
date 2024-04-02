package com.study.base.business.stock.goods.repo.mapper;

import cn.xluobo.business.stock.goods.domain.req.ReqSearchStockGoods;
import cn.xluobo.business.stock.goods.repo.model.StockGoods;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 库存-商品 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2021-01-09 01:55:13
 */
public interface StockGoodsMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<StockGoods> {

    /**
     * 查询列表
     * @param reqSearchStockGoods
     * @param page
     * @return
     */
    List<StockGoods> selectByCondition(@Param("reqSearchStockGoods") ReqSearchStockGoods reqSearchStockGoods,
                                       @Param("page") Page page);

}
