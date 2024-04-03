package com.study.business.stock.info.repo.mapper;

import com.study.business.stock.info.domain.req.ReqSearchStockInfo;
import com.study.business.stock.info.repo.model.StockInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 当前库存 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2021-01-09 02:05:31
 */
public interface StockInfoMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<StockInfo> {

    /**
     * 查询
     * @param reqSearchStockInfo
     * @param page
     * @return
     */
    List<StockInfo> selectByCondition(@Param("reqSearchStockInfo") ReqSearchStockInfo reqSearchStockInfo,
                                                  @Param("page") Page page);

}
