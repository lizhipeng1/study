package com.study.base.business.sc.course.repo.enums;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/14 21:55
 */
public enum ClaTimeRuleTypeEnums {


    REPEAT_RULE("1", "重复排课"),
    ONCE_RULE("2", "单次排课");


    private String ruleType;

    private String ruleTypeName;

    ClaTimeRuleTypeEnums(String ruleType, String ruleTypeName) {
        this.ruleType = ruleType;
        this.ruleTypeName = ruleTypeName;
    }

    public String getRuleType() {
        return ruleType;
    }

    public String getRuleTypeName() {
        return ruleTypeName;
    }
}
