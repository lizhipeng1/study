package com.study.business.stock.info.service.impl;

import com.study.business.stock.info.repo.model.StockInfo;
import com.study.business.stock.info.repo.mapper.StockInfoMapper;
import com.study.business.stock.info.service.IStockInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 当前库存 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2021-01-09 02:05:31
 */
@Service
public class StockInfoServiceImpl extends ServiceImpl<StockInfoMapper, StockInfo> implements IStockInfoService {

}
