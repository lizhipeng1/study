package com.study.base.business.stock.category.enums;

/**
 * 商品类型属性类型枚举
 * 2022/3/24 5:15 下午
 *
 * @author zhangby
 **/
public enum StockCategoryPropertyEnum {

    INPUT("input"),
    RADIO("radio", true),
    CHECKBOX("checkbox", true),
    SELECT("select", true),

    ;

    private String propertyType;

    private boolean dictPropertyType;

    StockCategoryPropertyEnum(String propertyType) {
        this.propertyType = propertyType;
    }

    StockCategoryPropertyEnum(String propertyType, boolean dictPropertyType) {
        this.propertyType = propertyType;
        this.dictPropertyType = dictPropertyType;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public boolean isDictPropertyType() {
        return dictPropertyType;
    }

    /**
     * 是否需要查询字典
     * @param propertyType
     * @return
     */
    public static boolean needSearchDict(String propertyType) {
        for (StockCategoryPropertyEnum value : StockCategoryPropertyEnum.values()) {
            if (value.getPropertyType().equals(propertyType)) {
                return value.isDictPropertyType();
            }
        }
        return false;
    }
}
