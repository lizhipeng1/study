package com.study.base.business.sc.base.repo.model;

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
 * 教室
 * </p>
 *
 * @author zhangby
 * @since 2020-09-23 07:36:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sc_room")
public class ScRoom implements Serializable {


    /**
     * 教室
     */
    @TableId(value = "room_id", type = IdType.ASSIGN_ID)
    private Long roomId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 所属校区
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 教室名
     */
    @TableField("room_name")
    private String roomName;

    /**
     * 备注
     */
    @TableField("memo")
    private String memo;

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
