package com.study.business.tool.impt.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 导入课程
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/3 21:43
 */
@Data
@ExcelIgnoreUnannotated
public class ImportCourse {

    // A列 课程名称
    @ExcelProperty(index = 0)
    private String courseName;

    @ExcelProperty(index = 1)
    private String courseTypeName;

    @ExcelProperty(index = 2)
    private String teachingMode;

    @ExcelProperty(index = 3)
    private String courseIntro;

    // E列 是否开售
    @ExcelProperty(index = 4)
    private String sale;

    // F列 开课校区
    @ExcelProperty(index = 5)
    private String departName;

    // 收费模式
    @ExcelProperty(index = 6)
    private String chargeType;

    // H 包含课时数
    @ExcelProperty(index = 7)
    private BigDecimal count;

    // I 学费标准
    @ExcelProperty(index = 8)
    private BigDecimal totalFee;

    // J 时间周期
    @ExcelProperty(index = 9)
    private String dateUnit;
}
