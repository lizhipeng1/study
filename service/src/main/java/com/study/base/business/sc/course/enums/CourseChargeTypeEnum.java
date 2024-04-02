package com.study.base.business.sc.course.enums;

/**
 * 课程收费模式
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/4 15:30
 */
public enum CourseChargeTypeEnum {

    // 按课时
    HOUR("hour", "按课时"),
    // 按时间
    DATE("date", "按时间"),
    // 按期
    CYCLE("cycle", "按期");

    private final String chargeType;

    private final String chargeTypeName;

    CourseChargeTypeEnum(String chargeType, String chargeTypeName) {
        this.chargeType = chargeType;
        this.chargeTypeName = chargeTypeName;
    }

    public String getChargeType() {
        return chargeType;
    }

    public String getChargeTypeName() {
        return chargeTypeName;
    }

    public static String getChargeType(String chargeType) {
        for (CourseChargeTypeEnum chargeTypeEnum : CourseChargeTypeEnum.values()) {
            if (chargeTypeEnum.getChargeType().equals(chargeType)){
                return chargeTypeEnum.getChargeTypeName();
            }
        }
        return "未知";
    }
}
