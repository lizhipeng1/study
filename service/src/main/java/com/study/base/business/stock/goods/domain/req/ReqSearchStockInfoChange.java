package com.study.base.business.stock.goods.domain.req;

import cn.xluobo.business.stock.goods.repo.model.StockInfoChange;
import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchStockInfoChange extends ReqPageBase implements Serializable {
    private String changeType;
    private String changeCount;
    private String changeDate;
    private String changeStaffId;
    private String changeStaffName;
    private String goodsName;
    private String costPrice;
    private String salePrice;
    private String studentId;
    private String studentName;
    private String orderId;
    private String orderDetailId;
    private String memo;
}
