package com.study.base.business.sc.course.domain.export;

import cn.xluobo.business.tool.export.domain.ExportBaseBean;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 导出课程
 * @author ：zhangbaoyu
 * @date ：Created in 2020/7/29 11:18
 */
@Data
@ExcelIgnoreUnannotated
public class ExpCourse extends ExportBaseBean {

    private Long courseId;
    private Long chargeId;

    @ExcelProperty("课程名称")
    private String courseName;

    @ExcelProperty("课程类型")
    private String courseTypeName;

    @ExcelProperty("授课模式")
    private String teachingMode;

    @ExcelProperty("开班数")
    private Integer claCount;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("课程状态")
    private String sale;

    @ExcelProperty("课程简介")
    private String courseIntro;

    @ExcelProperty("开课校区")
    private String departName;

    @ExcelProperty("收费模式")
    private String chargeTypeName;

    private String chargeType;
    private String dateUnit;

    @ExcelProperty("时间周期")
    private String dateUnitName;

    @ExcelProperty("数量")
    private BigDecimal count;

    @ExcelProperty("总价")
    private BigDecimal totalFee;

    @Override
    public String getPrimaryId() {
        return String.valueOf(courseId);
    }
}
