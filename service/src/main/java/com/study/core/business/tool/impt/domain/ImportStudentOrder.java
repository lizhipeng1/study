package com.study.core.business.tool.impt.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 学生订单导入
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/12 16:08
 */
@Data
@ExcelIgnoreUnannotated
public class ImportStudentOrder extends ImportBase{

    // A列 学生姓名
    @ExcelProperty(index = 0)
    private String studentName;

    // B列 主要联系人关系
    @ExcelProperty(index = 1)
    private String contactRelation;

    // C列 联系电话
    @ExcelProperty(index = 2)
    private String contactPhone;

    // D列 性别
    @ExcelProperty(index = 3)
    private String sex;

    // E列 学校
    @ExcelProperty(index = 4)
    private String schoolName;

    // F列 入校时间
    @ExcelProperty(index = 5)
    private String inTime;

    // G列 订单类型
    @ExcelProperty(index = 6)
    private String orderDetailTag;

    // H列 报读校区
    @ExcelProperty(index = 7)
    private String deptName;

    // I列 报读课程
    @ExcelProperty(index = 8)
    private String courseName;

    // J列 报读班级
    @ExcelProperty(index = 9)
    private String claName;

    // K列 购买课时
    @ExcelProperty(index = 10)
    private String buyHour;

    // L列 剩余课时
    @ExcelProperty(index = 11)
    private String balanceHour;

    // M列 按时间缴费开始日期
    @ExcelProperty(index = 12)
    private String beginDate;

    // N列 按时间缴费 购买数量
    @ExcelProperty(index = 13)
    private String buyCount;

    // O列 实缴学费（收款金额）
    @ExcelProperty(index = 14)
    private String receiptFee;

    // P列 应收学费
    @ExcelProperty(index = 15)
    private String actualFee;

    // Q列 经办校区
    @ExcelProperty(index = 16)
    private String handleDeptName;

    // R列 经办日期
    @ExcelProperty(index = 17)
    private String handleDate;

    // S列 课程到期日期
    @ExcelProperty(index = 18)
    private String expireDate;

    // T列 收款账户
    @ExcelProperty(index = 19)
    private String accountName;

    // U列 销售员工
    @ExcelProperty(index = 20)
    private String saleStaffName;

    // V列 订单备注
    @ExcelProperty(index = 21)
    private String memo;
}
