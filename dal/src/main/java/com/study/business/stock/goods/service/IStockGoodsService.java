package com.study.business.stock.goods.service;

import com.study.business.stock.goods.repo.model.StockGoods;

/**
 * <p>
 * 库存-商品 服务类
 * </p>
 *
 * @author zhangby
 * @since 2021-01-09 01:55:13
 */
public interface IStockGoodsService extends com.baomidou.mybatisplus.extension.service.IService<StockGoods> {

    /**
     * 根据商品类型查询数量
     * @param categoryId
     * @return
     */
    Integer selectCntByCategoryId(String categoryId);

}
