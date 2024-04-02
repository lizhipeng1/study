package com.study.base.business.stock.goods.service.impl;

import cn.xluobo.business.stock.goods.repo.model.StockGoodsProperty;
import cn.xluobo.business.stock.goods.repo.mapper.StockGoodsPropertyMapper;
import cn.xluobo.business.stock.goods.service.IStockGoodsPropertyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2021-01-12 07:46:39
 */
@Service
public class StockGoodsPropertyServiceImpl extends ServiceImpl<StockGoodsPropertyMapper, StockGoodsProperty> implements IStockGoodsPropertyService {

    @Override
    public List<StockGoodsProperty> selectPropertyListByGoodsId(String goodsId) {
        QueryWrapper<StockGoodsProperty> qw = new QueryWrapper<>();
        qw.eq("goods_id", goodsId);
        return this.list(qw);
    }
}
