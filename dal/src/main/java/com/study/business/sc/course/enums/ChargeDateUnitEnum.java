package com.study.business.sc.course.enums;

/**
 * 按时间收费周期枚举
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/5 13:23
 */
public enum ChargeDateUnitEnum {

    DAY("day","天"),
    MONTH("month","月"),
    SEASON("season","季"),
    YEAR("year","年"),
    ;

    ChargeDateUnitEnum(String dateUnit, String dateUnitLabel) {
        this.dateUnit = dateUnit;
        this.dateUnitLabel = dateUnitLabel;
    }

    private final String dateUnit;

    private final String dateUnitLabel;

    public String getDateUnit() {
        return dateUnit;
    }

    public String getDateUnitLabel() {
        return dateUnitLabel;
    }

    public static String getDateUnitLabel(String dateUnit) {
        for (ChargeDateUnitEnum dateUnitEnum : ChargeDateUnitEnum.values()) {
            if (dateUnitEnum.getDateUnit().equals(dateUnit)) {
                return dateUnitEnum.getDateUnitLabel();
            }
        }
        return "";
    }
}
