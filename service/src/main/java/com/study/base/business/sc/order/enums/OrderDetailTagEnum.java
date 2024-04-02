package com.study.base.business.sc.order.enums;

/**
 * 订单明细 标签
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/12 15:13
 */
public enum OrderDetailTagEnum {

    NEW_COURSE("1", "新报"),
    CONTINUE_COURSE("2", "续报"),
    EXTEND_COURSE("3", "扩科"),
    ;

    private String tag;
    private String tagName;

    OrderDetailTagEnum(String tag, String tagName) {
        this.tag = tag;
        this.tagName = tagName;
    }

    public String getTag() {
        return tag;
    }

    public String getTagName() {
        return tagName;
    }
}
