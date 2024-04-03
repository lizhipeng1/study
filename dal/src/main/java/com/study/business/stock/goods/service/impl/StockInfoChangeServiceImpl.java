package com.study.business.stock.goods.service.impl;

import com.study.business.stock.goods.repo.model.StockInfoChange;
import com.study.business.stock.goods.repo.mapper.StockInfoChangeMapper;
import com.study.business.stock.goods.service.IStockInfoChangeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 库存变动 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2021-01-09 02:10:14
 */
@Service
public class StockInfoChangeServiceImpl extends ServiceImpl<StockInfoChangeMapper, StockInfoChange> implements IStockInfoChangeService {

}
