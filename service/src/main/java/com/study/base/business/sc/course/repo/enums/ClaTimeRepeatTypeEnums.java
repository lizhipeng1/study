package com.study.base.business.sc.course.repo.enums;

/**
 * 排课 重复方式
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/14 22:20
 */
public enum ClaTimeRepeatTypeEnums {
    EVERY_WEEK("1", "每周重复"),
    EVERY_SECOND_DAY("2", "隔天重复"),
    EVERY_SECOND_WEEK("3", "隔周重复"),
    ;

    private String repeatType;
    private String repeatTypeName;

    ClaTimeRepeatTypeEnums(String repeatType, String repeatTypeName) {
        this.repeatType = repeatType;
        this.repeatTypeName = repeatTypeName;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public String getRepeatTypeName() {
        return repeatTypeName;
    }
}
