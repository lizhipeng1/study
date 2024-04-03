package com.study.business.stock.goods.service.impl;

import com.study.business.stock.goods.repo.model.StockGoods;
import com.study.business.stock.goods.repo.mapper.StockGoodsMapper;
import com.study.business.stock.goods.service.IStockGoodsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 库存-商品 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2021-01-09 01:55:13
 */
@Service
public class StockGoodsServiceImpl extends ServiceImpl<StockGoodsMapper, StockGoods> implements IStockGoodsService {

    @Override
    public Integer selectCntByCategoryId(String categoryId) {
        QueryWrapper<StockGoods> qw = new QueryWrapper();
        qw.eq("category_id",categoryId);
        return this.count(qw);
    }
}
