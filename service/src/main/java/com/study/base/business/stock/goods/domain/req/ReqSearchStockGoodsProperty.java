package com.study.base.business.stock.goods.domain.req;

import cn.xluobo.business.stock.goods.repo.model.StockGoodsProperty;
import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchStockGoodsProperty extends ReqPageBase implements Serializable {

    private String goodsId;

}
