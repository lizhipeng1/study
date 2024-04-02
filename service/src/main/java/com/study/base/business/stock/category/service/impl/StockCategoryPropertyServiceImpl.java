package com.study.base.business.stock.category.service.impl;

import cn.xluobo.business.stock.category.repo.model.StockCategoryProperty;
import cn.xluobo.business.stock.category.repo.mapper.StockCategoryPropertyMapper;
import cn.xluobo.business.stock.category.service.IStockCategoryPropertyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品类型属性配置 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2021-01-12 07:30:24
 */
@Service
public class StockCategoryPropertyServiceImpl extends ServiceImpl<StockCategoryPropertyMapper, StockCategoryProperty> implements IStockCategoryPropertyService {

    @Override
    public Integer selectCntByCategoryId(String categoryId) {
        QueryWrapper<StockCategoryProperty> qw = new QueryWrapper<>();
        qw.eq("category_id", categoryId);
        return this.count(qw);
    }

    @Override
    public List<StockCategoryProperty> selectCategoryPropertyList(String categoryId) {
        QueryWrapper<StockCategoryProperty> qw = new QueryWrapper<>();
        qw.eq("category_id", categoryId);
        return this.list(qw);
    }
}
