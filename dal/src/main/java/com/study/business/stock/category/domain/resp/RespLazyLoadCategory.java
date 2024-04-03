package com.study.business.stock.category.domain.resp;

import com.study.business.stock.category.repo.model.StockCategory;
import lombok.Data;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2021/1/14 21:53
 */
@Data
public class RespLazyLoadCategory extends StockCategory {

    private boolean hasChildren;

    public void setHasChildren(Integer hasChildren) {
        this.hasChildren = hasChildren > 0;
    }
}
