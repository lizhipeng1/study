package com.study.business.stock.category.domain.req;

import com.study.core.page.ReqPageBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchStockCategoryProperty extends ReqPageBase implements Serializable {
    private String categoryId;
    private String categoryName;
    private String propertyCode;
    private String propertyName;
}
