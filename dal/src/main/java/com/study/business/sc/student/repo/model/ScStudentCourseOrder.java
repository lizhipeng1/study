package com.study.business.sc.student.repo.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学生课程关联订单
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_student_course_order")
public class ScStudentCourseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "course_order_id", type = IdType.ASSIGN_ID)
    private Long courseOrderId;

    @TableField("student_course_id")
    private Long studentCourseId;

    /**
     * 订单
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 购买课程订单
     */
    @TableField("order_detail_id")
    private Long orderDetailId;

    /**
     * 购买课时数量
     */
    @TableField("total_hour")
    private BigDecimal totalHour;

    /**
     * 剩余课时数量
     */
    @TableField("balance_hour")
    private BigDecimal balanceHour;

    /**
     * 购买天数
     */
    @TableField("total_day")
    private BigDecimal totalDay;

    /**
     * 开始时间 按时间收费
     */
    @TableField("begin_date")
    private String beginDate;

    /**
     * 结束时间 按时间收费
     */
    @TableField("end_date")
    private String endDate;

    /**
     * 失效时间
     */
    @TableField("expire_date")
    private String expireDate;

    /**
     * 总金额
     */
    @TableField("total_fee")
    private BigDecimal totalFee;

    /**
     * 单价
     */
    @TableField("unit_fee")
    private BigDecimal unitFee;

    /**
     * 是否有效 1有效 0失效
     */
    @TableField("valid")
    private Boolean valid;

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


}
