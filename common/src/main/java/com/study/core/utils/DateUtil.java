package com.study.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-18 17:22
 */
public class DateUtil {

    private static final DateTimeFormatter yyyy_MM_dd = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static final DateTimeFormatter yyyyMM = DateTimeFormat.forPattern("yyyyMM");

    public interface DATE_PATTERN {
        String HHMMSS = "HHmmss";
        String HH_MM_SS = "HH:mm:ss";
        String YYYYMMDD = "yyyyMMdd";
        String YYYYMMDDHH_MM = "yyyyMMddHH:mm";
        String YYYY_MM_DD = "yyyy-MM-dd";
        String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
        String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
        String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
        String YYYYMMDDHHMM = "yyyyMMddHHmm";
        String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    }

    public static DateTime yyyMMddDayBegin(String dateStr){
        if(StringUtils.isEmpty(dateStr)){
            return null;
        }else{
            return DateTime.parse(dateStr,yyyy_MM_dd)
                    .withHourOfDay(0)
                    .withMinuteOfHour(0)
                    .withSecondOfMinute(0)
                    .withMillisOfSecond(0);
        }
    }

    public static DateTime yyyMMddDayEnd(String dateStr){
        if(StringUtils.isEmpty(dateStr)){
            return null;
        }else{
            return DateTime.parse(dateStr,yyyy_MM_dd)
                    .withHourOfDay(23)
                    .withMinuteOfHour(59)
                    .withSecondOfMinute(59)
                    .withMillisOfSecond(59);
        }
    }

    public static DateTime yyyMMDay(String dateStr){
        if(StringUtils.isEmpty(dateStr)){
            return null;
        }else{
            return DateTime.parse(dateStr,yyyyMM);
        }
    }

    public static String format(Object date,String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
}
