package com.study.business.stock.category.repo.model;

import java.io.Serializable;
import java.util.*;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 商品类型
 * </p>
 *
 * @author zhangby
 * @since 2021-01-12 08:08:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("stock_category")
public class StockCategory implements Serializable {


    /**
     * 类型编码
     */
    @TableId(value = "category_id", type = IdType.ASSIGN_ID)
    private Long categoryId;

    /**
     * 父编码
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 祖级列表
     */
    @TableField("ancestors")
    private String ancestors;

    /**
     * 类型名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;



    @TableField(exist = false)
    private List<StockCategory> children;

    private static Map<Long, List<StockCategory>> subTreeMap = new HashMap<>();

    /**
     * 树转换
     * @param stockCategoryList
     */
    public void converterTree(List<StockCategory> stockCategoryList) {
        subTreeMap.clear();
        for (StockCategory menu : stockCategoryList) {
            List<StockCategory> subTreeList = subTreeMap.get(menu.getParentId());
            if (null == subTreeList) {
                subTreeList = new LinkedList<>();
                subTreeMap.put(menu.getParentId(),subTreeList);
            }
            subTreeList.add(menu);
        }

        this.children = treecategory(this).getChildren();
        if(this.children.isEmpty()){
            this.children = stockCategoryList;
        }
    }

    /**
     * 深度优先 遍历树
     * @param stockCategory
     * @return
     */
    private StockCategory treecategory(StockCategory stockCategory) {
        Long deptId = stockCategory.getCategoryId();
        List<StockCategory> childMenuList = subTreeMap.get(deptId);
        if(null != childMenuList){
            List<StockCategory> childList = Lists.newLinkedList();
            for (StockCategory childcategory : childMenuList) {
                StockCategory child = treecategory(childcategory);
                childList.add(child);
            }
            stockCategory.setChildren(childList);
            return stockCategory;
        }else{
            stockCategory.setChildren(Lists.newLinkedList());
            return stockCategory;
        }
    }
}
