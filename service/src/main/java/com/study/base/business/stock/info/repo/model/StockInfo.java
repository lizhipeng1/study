package com.study.base.business.stock.info.repo.model;

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
 * 当前库存
 * </p>
 *
 * @author zhangby
 * @since 2021-01-09 02:05:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("stock_info")
public class StockInfo implements Serializable {


    /**
     * 库存编号
     */
    @TableId(value = "stock_id", type = IdType.ASSIGN_ID)
    private Long stockId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 商品编号
     */
    @TableField("goods_id")
    private Long goodsId;

    /**
     * 校区
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 成本价格
     */
    @TableField("cost_price")
    private BigDecimal costPrice;

    /**
     * 销售价格
     */
    @TableField("sale_price")
    private BigDecimal salePrice;

    /**
     * 库存数量
     */
    @TableField("stock_cnt")
    private Integer stockCnt;

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
    private String goodsName;
    @TableField(exist = false)
    private String deptName;
    @TableField(exist = false)
    private String categoryName;
    @TableField(exist = false)
    private List<Object> extendValues = Lists.newArrayList();
}
