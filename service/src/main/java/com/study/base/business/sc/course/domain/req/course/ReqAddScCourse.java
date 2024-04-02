package com.study.base.business.sc.course.domain.req.course;

import cn.xluobo.core.api.APIBaseResponse;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 录入课程
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/7/8 20:34
 */
@Data
public class ReqAddScCourse {

    /**
     * 课程名
     */
    private String courseName;

    /**
     * 课程类型
     */
    private Long courseTypeId;

    /**
     * 教学模式
     */
    private String teachingMode;

    /**
     * 课程简介
     */
    private String courseIntro;

    /**
     * 上课校区 所有 或 部分 all part
     */
    private String courseCampus;

    /**
     * 上课校区
     */
    private String[] partCampus;

    /**
     * 收费模式
     */
    private boolean feeModeHour;

    /**
     * 收费模式
     */
    private boolean feeModeDate;

    /**
     * 收费模式
     */
    private boolean feeModeCycle;

    private List<ReqAddScCourseChargeItem> feeModeHourList;

    private List<ReqAddScCourseChargeItem> feeModeDateList;

    private List<ReqAddScCourseChargeItem> feeModeCycleList;

    /**
     * 参数校验
     *
     * @return
     */
    public APIBaseResponse checkParam() {
        if (StringUtils.isEmpty(courseName)) {
            return APIBaseResponse.fail("请输入课程名称");
        } else if (StringUtils.isEmpty(teachingMode)) {
            return APIBaseResponse.fail("请选择教学模式");
        }

        if (StringUtils.isEmpty(courseCampus)) {
            return APIBaseResponse.fail("请选择上课校区");
        }
        if ("part".equals(courseCampus) && null == partCampus) {
            return APIBaseResponse.fail("请选择上课校区");
        }
        if ("part".equals(courseCampus) && partCampus.length == 0) {
            return APIBaseResponse.fail("请选择上课校区");
        }

        if (!feeModeHour && !feeModeDate && !feeModeCycle) {
            return APIBaseResponse.fail("请配置收费模式");
        }

        if (feeModeHour && (null == feeModeHourList || feeModeHourList.isEmpty())) {
            return APIBaseResponse.fail("请配置课时收费模式");
        }
        if (feeModeDate && (null == feeModeDateList || feeModeDateList.isEmpty())) {
            return APIBaseResponse.fail("请配置时间收费模式");
        }
        if (feeModeCycle && (null == feeModeCycleList || feeModeCycleList.isEmpty())) {
            return APIBaseResponse.fail("请配置周期收费模式");
        }

        BigDecimal zero = BigDecimal.ZERO;
        if (feeModeHour) {
            for (ReqAddScCourseChargeItem item : feeModeHourList) {
                BigDecimal cnt = item.getCnt();
                BigDecimal totalFee = item.getTotalFee();
                String campusName = item.getCampusName();
                if(null == cnt || cnt.compareTo(zero)==0){
                    return APIBaseResponse.fail("请填写"+campusName+"按课时收费 数量");
                }
                if(null == totalFee || totalFee.compareTo(zero)==0){
                    return APIBaseResponse.fail("请填写"+campusName+"按课时收费 金额");
                }
            }
        }

        if (feeModeDate) {
            for (ReqAddScCourseChargeItem item : feeModeDateList) {
                BigDecimal cnt = item.getCnt();
                BigDecimal totalFee = item.getTotalFee();
                String dateType = item.getDateType();
                String campusName = item.getCampusName();
                if(null == cnt || cnt.compareTo(zero)==0){
                    return APIBaseResponse.fail("请填写"+campusName+"按时间收费 数量");
                }
                if(null == totalFee || totalFee.compareTo(zero)==0){
                    return APIBaseResponse.fail("请填写"+campusName+"按时间收费 金额");
                }
                if(StringUtils.isEmpty(dateType)){
                    return APIBaseResponse.fail("请填写"+campusName+"按时间收费 时间段");
                }
            }
        }

        if(feeModeCycle) {
            for (ReqAddScCourseChargeItem item : feeModeCycleList) {
                BigDecimal cnt = item.getCnt();
                BigDecimal totalFee = item.getTotalFee();
                String campusName = item.getCampusName();
                if(null == cnt || cnt.compareTo(zero)==0){
                    return APIBaseResponse.fail("请填写"+campusName+"按期收费 数量");
                }
                if(null == totalFee || totalFee.compareTo(zero)==0){
                    return APIBaseResponse.fail("请填写"+campusName+"按期收费 金额");
                }
            }
        }

        return APIBaseResponse.success();
    }
}
