package com.study.business.stock.category.service;

import com.study.business.stock.category.repo.model.StockCategoryProperty;

import java.util.List;

/**
 * <p>
 * 商品类型属性配置 服务类
 * </p>
 *
 * @author zhangby
 * @since 2021-01-12 07:30:24
 */
public interface IStockCategoryPropertyService extends com.baomidou.mybatisplus.extension.service.IService<StockCategoryProperty> {

    /**
     * 根据商品类型查询属性数量
     * @param categoryId
     * @return
     */
    Integer selectCntByCategoryId(String categoryId);

    /**
     * 获取 商品类型 属性配置
     * @param categoryId
     * @return
     */
    List<StockCategoryProperty> selectCategoryPropertyList(String categoryId);

}
