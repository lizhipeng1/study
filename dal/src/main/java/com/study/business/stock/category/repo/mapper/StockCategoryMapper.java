package com.study.business.stock.category.repo.mapper;

import com.study.business.stock.category.domain.resp.RespLazyLoadCategory;
import com.study.business.stock.category.repo.model.StockCategory;

import java.util.List;

/**
 * <p>
 * 商品类型 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2021-01-12 08:08:51
 */
public interface StockCategoryMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<StockCategory> {

    /**
     * 获取子节点，以及是否有子节点
     * @param parentId
     * @return
     */
    List<RespLazyLoadCategory> selectCategoryByParentId(Long parentId);

}
