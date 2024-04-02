package com.study.base.business.sc.course.domain.req.course;

import cn.xluobo.core.api.APIBaseResponse;
import lombok.Data;

/**
 * 修改课程
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/7/8 20:34
 */
@Data
public class ReqChangeScCourse extends ReqAddScCourse{

    private Long courseId;

    public APIBaseResponse checkParam() {
        APIBaseResponse checkParam = super.checkParam();
        if(!checkParam.isSuccess()){
            return checkParam;
        }
        if(null == courseId){
            return APIBaseResponse.fail("请求参数错误,请稍后重试");
        }
        return APIBaseResponse.success();
    }

}
