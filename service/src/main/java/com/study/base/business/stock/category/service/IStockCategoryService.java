package com.study.base.business.stock.category.service;

import cn.xluobo.business.stock.category.repo.model.StockCategory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品类型 服务类
 * </p>
 *
 * @author zhangby
 * @since 2021-01-12 08:08:51
 */
public interface IStockCategoryService extends com.baomidou.mybatisplus.extension.service.IService<StockCategory> {
    /**
     * 是否存在子节点
     * @param categoryId
     * @return
     */
    boolean hadChild(String categoryId);

    /**
     * 修改子元素关系
     * @param categoryId 被修改的ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    void updateStockCategoryChildren(String categoryId, String newAncestors, String oldAncestors);

    /**
     * 根据ID 获取详情
     * @param categoryId
     * @return
     */
    StockCategory getCategoryInfoById(String categoryId);

    /**
     * 删除缓存
     * @param categoryId
     * @return
     */
    boolean removeCache(String categoryId);
}
