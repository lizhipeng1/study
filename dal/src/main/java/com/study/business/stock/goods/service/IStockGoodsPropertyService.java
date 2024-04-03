package com.study.business.stock.goods.service;

import com.study.business.stock.goods.repo.model.StockGoodsProperty;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author zhangby
 * @since 2021-01-12 07:46:39
 */
public interface IStockGoodsPropertyService extends com.baomidou.mybatisplus.extension.service.IService<StockGoodsProperty> {

    /**
     * 获取商品自定义属性值
     * @param goodsId
     * @return
     */
    List<StockGoodsProperty> selectPropertyListByGoodsId(String goodsId);

}
