package com.study.business.tool.impt.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 导入班级
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/10 16:46
 */
@Data
@ExcelIgnoreUnannotated
public class ImportCourseCla extends ImportBase{

    // A列 班级名称
    @ExcelProperty(index = 0)
    private String claName;

    // B列 课程名称
    @ExcelProperty(index = 1)
    private String courseName;

    // C列 校区名
    @ExcelProperty(index = 2)
    private String deptName;

    // D列 任课教师
    @ExcelProperty(index = 3)
    private String teacherName;

    // E列 满班人数
    @ExcelProperty(index = 4)
    private String capacity;

    // F列 招生状态
    @ExcelProperty(index = 5)
    private String recruitStatus;

    // G列 每次上课学员扣除课时
    @ExcelProperty(index = 6)
    private String everyStuLoseHour;

    // H列 开班日期
    @ExcelProperty(index = 7)
    private String openDate;

    // I列 结班日期
    @ExcelProperty(index = 8)
    private String closeDate;

    // J列 备注
    @ExcelProperty(index = 9)
    private String memo;

    // K列 排课开始日期
    @ExcelProperty(index = 10)
    private String beginDate;

    // L列 排课结束日期
    @ExcelProperty(index = 11)
    private String endDate;

    // M列 排课重复方式
    @ExcelProperty(index = 12)
    private String repeatType;

    // N列 上课星期
    @ExcelProperty(index = 13)
    private String weekDay;

    // O列 是否过滤节假日
    @ExcelProperty(index = 14)
    private String filterHoliday;

    // P列 上课时间
    @ExcelProperty(index = 15)
    private String startTime;

    // Q列 下课时间
    @ExcelProperty(index = 16)
    private String endTime;

    // R列 上课教室
    @ExcelProperty(index = 17)
    private String roomName;

}
