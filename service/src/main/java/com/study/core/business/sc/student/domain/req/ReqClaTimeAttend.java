package com.study.core.business.sc.student.domain.req;

import cn.xluobo.core.api.APIResponse;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 记上课请求参数
 * 修改上课记录请求参数
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/1 19:44
 */
@Data
public class ReqClaTimeAttend {

    // 上课方式 排课记上课rule、自定义上课custom
    private String attendType;

    // 排课编号
    private Long courseTimeId;

    private Long claId;

    private Long teacherId;

    private Long roomId;

    private String claDate;

    private String startTime;

    private String endTime;

    private String classTheme;

    private String memo;

    List<ReqClaTimeAttendItem> studentAttendList;

    /**
     * 参数校验
     * @return
     */
    public APIResponse checkParam() {
        if(null == studentAttendList || studentAttendList.size() ==0) {
            return APIResponse.toExceptionResponse("排课记上课,请选择上课学生");
        }
        for (ReqClaTimeAttendItem attendItem : studentAttendList) {
            if(StringUtils.isAnyEmpty(attendItem.getAttendStatus())) {
                return APIResponse.toExceptionResponse("请选择学员到课状态");
            } else if(null == attendItem.getStudentCourseId()) {
                return APIResponse.toExceptionResponse("请选择学员");
            } else if(null == attendItem.getStuLoseHour()) {
                return APIResponse.toExceptionResponse("请选择扣减课时");
            }
        }
        if("rule".equals(attendType)) {
            // 排课记上课
            if(null == courseTimeId) {
                return APIResponse.toExceptionResponse("排课记上课,未选择上课日期!");
            } else if(StringUtils.isAnyEmpty(startTime,endTime)) {
                return APIResponse.toExceptionResponse("排课记上课,未选择上下课时间!");
            }

        } else if ("custom".equals(attendType)) {
            // 自定义记上课
            if(StringUtils.isAnyEmpty(claDate,startTime,endTime)) {
                return APIResponse.toExceptionResponse("自定义记上课,未选择上课日期,上下课时间!");
            } else if( null == teacherId) {
                return APIResponse.toExceptionResponse("自定义记上课,请选择上课教师!");
            }
        }
        return APIResponse.toOkResponse();
    }

    /**
     * 已上课修改
     * 参数校验
     * @return
     */
    public APIResponse checkParamForUpdateHadClaTime() {
        if(null == studentAttendList || studentAttendList.size() ==0) {
            return APIResponse.toExceptionResponse("排课记上课,请选择上课学生");
        }
        for (ReqClaTimeAttendItem attendItem : studentAttendList) {
            if(StringUtils.isAnyEmpty(attendItem.getAttendStatus())) {
                return APIResponse.toExceptionResponse("请选择学员到课状态");
            } else if(null == attendItem.getStudentCourseId()) {
                return APIResponse.toExceptionResponse("请选择学员");
            } else if(null == attendItem.getStuLoseHour()) {
                return APIResponse.toExceptionResponse("请选择扣减课时");
            }
        }

        // 自定义记上课
        if(StringUtils.isAnyEmpty(claDate,startTime,endTime)) {
            return APIResponse.toExceptionResponse("自定义记上课,未选择上课日期,上下课时间!");
        } else if( null == teacherId) {
            return APIResponse.toExceptionResponse("自定义记上课,请选择上课教师!");
        }
        return APIResponse.toOkResponse();
    }

}
