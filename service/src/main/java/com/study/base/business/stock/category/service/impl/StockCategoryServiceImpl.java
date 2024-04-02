package com.study.base.business.stock.category.service.impl;

import cn.xluobo.business.stock.category.repo.model.StockCategory;
import cn.xluobo.business.stock.category.repo.mapper.StockCategoryMapper;
import cn.xluobo.business.stock.category.service.IStockCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品类型 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2021-01-12 08:08:51
 */
@Service
public class StockCategoryServiceImpl extends ServiceImpl<StockCategoryMapper, StockCategory> implements IStockCategoryService {

    /**
     * 是否存在子节点
     * @param categoryId
     * @return
     */
    public boolean hadChild(String categoryId){
        QueryWrapper qw = new QueryWrapper();
        qw.eq("parent_id",categoryId);
        List list = list(qw);
        if(list.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void updateStockCategoryChildren(String categoryId, String newAncestors, String oldAncestors) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("category_id",categoryId);
        List<StockCategory> childrenList = list(qw);
        if(!childrenList.isEmpty()){
            for (StockCategory children : childrenList) {
                children.setAncestors(children.getAncestors().replace(oldAncestors, newAncestors));
            }
            updateBatchById(childrenList);
        }
     }

    @Override
    @Cacheable(value = "STOCK_CATEGORY", key = "#categoryId", sync = true)
    public StockCategory getCategoryInfoById(String categoryId) {
        return this.getById(categoryId);
    }

    @Override
    @CacheEvict(value = "STOCK_CATEGORY", key = "#categoryId")
    public boolean removeCache(String categoryId) {
        return true;
    }
}
