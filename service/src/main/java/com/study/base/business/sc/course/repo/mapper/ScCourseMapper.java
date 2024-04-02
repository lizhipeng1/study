package com.study.base.business.sc.course.repo.mapper;

import cn.xluobo.business.sc.course.domain.export.ExpCourse;
import cn.xluobo.business.sc.course.domain.req.ReqSearchScCourse;
import cn.xluobo.business.sc.course.domain.resp.course.RespSearchCourse;
import cn.xluobo.business.sc.course.repo.model.ScCourse;
import cn.xluobo.core.page.ReqDeptCondition;
import cn.xluobo.core.page.RespPage;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程信息 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-03-17 11:25:55
 */
public interface ScCourseMapper extends BaseMapper<ScCourse> {

    /**
     * 获取课程列表
     * @param reqSearchScCourse
     * @param page
     * @return
     */
    List<RespSearchCourse> selectCourseList(@Param("reqSearchScCourse") ReqSearchScCourse reqSearchScCourse, @Param("page") RespPage page);

    /**
     * 获取课程列表
     * 包含学生当前课程状态
     * @param reqSearchScCourse
     * @param page
     * @return
     */
    List<RespSearchCourse> selectCourseListWithStudentCourse(@Param("reqSearchScCourse") ReqSearchScCourse reqSearchScCourse, @Param("page") RespPage page);

    /**
     * 导出课程
     * @param reqSearchScCourse
     * @return
     */
    List<ExpCourse> selectCourseForExport(@Param("reqSearchScCourse") ReqSearchScCourse reqSearchScCourse);

    /**
     * 课程数量
     * @param reqDeptCondition
     * @return
     */
    Integer selectCourseCount(ReqDeptCondition reqDeptCondition);

    /**
     * 租户下课程数量
     * @param tenantId
     * @return
     */
    @SqlParser(filter = true)
    Integer selectTenantCourseCount(String tenantId);

}
