package com.study.base.business.sc.student.repo.mapper;

import cn.xluobo.business.sc.student.domain.req.ReqSearchScStudent;
import cn.xluobo.business.sc.student.domain.req.ReqStudentSelect;
import cn.xluobo.business.sc.student.domain.resp.RespSearchStudent;
import cn.xluobo.business.sc.student.repo.model.ScStudent;
import cn.xluobo.core.page.ReqDeptCondition;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 学生基本信息 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-04-27 07:13:40
 */
public interface ScStudentMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<ScStudent> {

    List<RespSearchStudent> selectForSearchTable(@Param("reqSearchScStudent")ReqSearchScStudent reqSearchScStudent, @Param("page")Page page);

    List<RespSearchStudent> selectForSelect(@Param("studentSelect") ReqStudentSelect studentSelect, @Param("page") Page page);

    /**
     * 全部学生
     * @param reqSearchScStudent
     * @return
     */
    List<RespSearchStudent> selectAllStudent(ReqSearchScStudent reqSearchScStudent);

    /**
     * 学员数量
     * @param reqDeptCondition
     * @return
     */
    Integer selectStudentCount(ReqDeptCondition reqDeptCondition);

    /**
     * 租户下学生数量
     * @param tenantId
     * @return
     */
    @SqlParser(filter = true)
    Integer selectTenantStudentCount(String tenantId);
}
