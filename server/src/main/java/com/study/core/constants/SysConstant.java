package com.study.core.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/24 14:44
 */
public class SysConstant {

    /**
     * 星期 与 中文 对应关系
     */
    public static final Map<Integer, String> WEEK_DAY_MAP = new HashMap<>();

    /**
     * 上课时间
     * key:小时
     * value: 时间段
     */
    public static final Map<Integer, String> CLA_TIME_MAP = new HashMap<>();

    static {
        WEEK_DAY_MAP.clear();
        WEEK_DAY_MAP.put(1, "星期一");
        WEEK_DAY_MAP.put(2, "星期二");
        WEEK_DAY_MAP.put(3, "星期三");
        WEEK_DAY_MAP.put(4, "星期四");
        WEEK_DAY_MAP.put(5, "星期五");
        WEEK_DAY_MAP.put(6, "星期六");
        WEEK_DAY_MAP.put(7, "星期日");

        CLA_TIME_MAP.clear();
        CLA_TIME_MAP.put(8, "08:00 ~ 10:00");
        CLA_TIME_MAP.put(10, "10:00 ~ 12:00");
        CLA_TIME_MAP.put(12, "12:00 ~ 14:00");
        CLA_TIME_MAP.put(14, "14:00 ~ 16:00");
        CLA_TIME_MAP.put(16, "16:00 ~ 18:00");
        CLA_TIME_MAP.put(18, "18:00 ~ 20:00");
    }
}
