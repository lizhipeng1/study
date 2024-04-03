package com.study.business.stock.info.domain.req;

import com.study.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchStockInfo extends ReqPageBase implements Serializable {

    private String categoryId;

    private String goodsId;

    private Integer maxStockCnt;

}
