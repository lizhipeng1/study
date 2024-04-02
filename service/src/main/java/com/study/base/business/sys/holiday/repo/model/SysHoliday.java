package com.study.base.business.sys.holiday.repo.model;

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
 * 节假日表
 * </p>
 *
 * @author zhangby
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_holiday")
public class SysHoliday implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期 格式yyyy-MM-dd
     */
    @TableId(value = "day", type = IdType.INPUT)
    private String day;

    /**
     * 日期类型 0工作日 1 假日 2节日
     */
    @TableField("day_type")
    private String dayType;

    public SysHoliday() {
    }

    public SysHoliday(String day, String dayType) {
        this.day = day;
        this.dayType = dayType;
    }
}
