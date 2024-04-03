package com.study.business.sc.log.repo.mapper;

import com.study.business.sc.log.domain.req.ReqSearchStuCourseLog;
import com.study.business.sc.log.repo.model.ScStudentCourseLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程缴费扣费记录 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-12-17
 */
public interface ScStudentCourseLogMapper extends BaseMapper<ScStudentCourseLog> {

    List<ScStudentCourseLog> selectForSearchTable(@Param("reqSearchStuCourseLog") ReqSearchStuCourseLog reqSearchStuCourseLog, @Param("page") Page page);
}
