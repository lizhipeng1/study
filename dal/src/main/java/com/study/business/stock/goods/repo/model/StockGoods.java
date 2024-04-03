package com.study.business.stock.goods.repo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 库存-商品
 * </p>
 *
 * @author zhangby
 * @since 2021-01-09 01:55:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("stock_goods")
public class StockGoods implements Serializable {


    /**
     * 商品编号
     */
    @TableId(value = "goods_id", type = IdType.ASSIGN_ID)
    private Long goodsId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 类型编码
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;

    /**
     * 展示价格
     */
    @TableField("sale_price")
    private BigDecimal salePrice;

    /**
     * 销售状态 1:销售 0:下架
     */
    @TableField("sale")
    private String sale;

    /**
     * 删除标志（1删除 0在用）
     */
    @TableField("delete_flag")
    @TableLogic
    private String deleteFlag;

    /**
     * 创建者
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField("last_update_user")
    private String lastUpdateUser;

    /**
     * 更新时间
     */
    @TableField("last_update_time")
    private Date lastUpdateTime;

    @TableField(exist = false)
    private String categoryName;
    @TableField(exist = false)
    private List<Object> extendValues = Lists.newArrayList();
}
