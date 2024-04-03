package com.study.business.stock.category.repo.mapper;

import com.study.business.stock.category.domain.req.ReqSearchStockCategoryProperty;
import com.study.business.stock.category.repo.model.StockCategoryProperty;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品类型属性配置 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2021-01-12 07:30:24
 */
public interface StockCategoryPropertyMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<StockCategoryProperty> {

    /**
     * 查询
     * @param reqSearchStockCategoryProperty
     * @param page
     * @return
     */
    List<StockCategoryProperty> selectByCondition(@Param("reqSearchStockCategoryProperty") ReqSearchStockCategoryProperty reqSearchStockCategoryProperty,
                                                  @Param("page") Page page);

}
