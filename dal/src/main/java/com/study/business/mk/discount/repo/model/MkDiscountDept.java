package com.study.business.mk.discount.repo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 活动校区
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mk_discount_dept")
public class MkDiscountDept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 折扣编号
     */
    @TableField("discount_id")
    private Long discountId;

    /**
     * 校区 -1为全部校区
     */
    @TableField("dept_id")
    private Long deptId;


}
