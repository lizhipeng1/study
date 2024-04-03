package com.study.business.sc.course.domain.req;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 上课请求参数
 * @author ：zhangbaoyu
 * @date ：Created in 2020/6/8 10:44
 */
@Data
public class ReqBusinessClaBegin implements Serializable {

    private Long courseTimeId;

    private Long[] studentIds;

    /**
     * 实际上课时间
     */
    private String startTime;

    /**
     * 实际结束时间
     */
    private String endTime;

    /**
     * 其他说明
     */
    private String memo;

    /**
     * 消耗课时数量
     */
    private BigDecimal payHourCount;

    /**
     * 上课时课程 参数校验
     * @return
     */
    public boolean checkParamForHour(){
        if(null == courseTimeId || null == studentIds || studentIds.length == 0){
            return false;
        }
        if(StringUtils.isAnyEmpty(startTime,endTime)){
            return false;
        }
        if(null == payHourCount || "0".equals(payHourCount.toString())){
            return false;
        }
        return true;
    }

    /**
     * 上周期课程 参数校验
     * @return
     */
    public boolean checkParamForDate(){
        if(null == courseTimeId || null == studentIds || studentIds.length == 0){
            return false;
        }
        if(StringUtils.isAnyEmpty(startTime,endTime)){
            return false;
        }
        return true;
    }

}
