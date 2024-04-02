package com.study.base.business.stock.goods.repo.model;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author zhangby
 * @since 2021-01-12 07:46:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("stock_goods_property")
public class StockGoodsProperty implements Serializable {

    /**
     * 商品编号
     */
    @TableId(value = "goods_id", type = IdType.INPUT)
    private Long goodsId;

    /**
     * 属性code
     */
    @TableId(value = "property_code", type = IdType.INPUT)
    private String propertyCode;

    /**
     * 属性名称
     */
    @TableField("property_name")
    private String propertyName;

    /**
     * 属性值
     */
    @TableField("property_value")
    private String propertyValue;
}
