package com.study.business.stock.category.repo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品类型属性配置
 * </p>
 *
 * @author zhangby
 * @since 2021-01-12 07:30:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("stock_category_property")
public class StockCategoryProperty implements Serializable {


    /**
     * 属性id
     */
    @TableId(value = "property_id", type = IdType.ASSIGN_ID)
    private Long propertyId;

    /**
     * 商品类型属性
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 属性code
     */
    @TableField("property_code")
    private String propertyCode;

    /**
     * 属性名称
     */
    @TableField("property_name")
    private String propertyName;

    /**
     * input radio checkbox select
     */
    @TableField("property_type")
    private String propertyType;

    /**
     * 字典类型 radio checkbox select
     */
    @TableField("dict_type")
    private String dictType;

    /**
     * 是否必填
     */
    @TableField("necessary")
    private Integer necessary;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 商品类型名称
     */
    @TableField(exist = false)
    private String categoryName;
}
