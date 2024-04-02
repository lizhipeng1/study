package com.study.base.business.stock.goods.repo.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * 库存变动
 * </p>
 *
 * @author zhangby
 * @since 2021-01-09 02:10:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("stock_info_change")
public class StockInfoChange implements Serializable {


    /**
     * 库存编号
     */
    @TableId(value = "stock_change_id", type = IdType.AUTO)
    private Long stockChangeId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 库存编号
     */
    @TableField("stock_id")
    private Long stockId;

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
     * 1:入库 2:出库 3:转库
     */
    @TableField("change_type")
    private String changeType;

    /**
     * 变动数量
     */
    @TableField("change_count")
    private Integer changeCount;

    /**
     * 变动日期
     */
    @TableField("change_date")
    private Date changeDate;

    /**
     * 经办人
     */
    @TableField("change_staff_id")
    private Date changeStaffId;

    /**
     * 经办人
     */
    @TableField("change_staff_name")
    private Date changeStaffName;

    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;

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
     * 学生
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 学生
     */
    @TableField("student_name")
    private String studentName;

    /**
     * 订单
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 订单明细
     */
    @TableField("order_detail_id")
    private Long orderDetailId;

    /**
     * 备注
     */
    @TableField("memo")
    private String memo;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
}
