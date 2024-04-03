package com.study.business.sc.course.repo.mapper;

import com.study.business.sc.course.domain.req.ReqCourseClaSelect;
import com.study.business.sc.course.domain.req.ReqSearchScCourseCla;
import com.study.business.sc.course.domain.req.cla.ReqClaCount;
import com.study.business.sc.course.domain.resp.RespCourseClaInfo;
import com.study.business.sc.course.domain.resp.RespCourseClaSelectInfo;
import com.study.business.sc.course.repo.model.ScCourseCla;
import com.study.core.page.RespPage;
import com.baomidou.mybatisplus.annotation.SqlParser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程班级信息 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-03-17 01:11:06
 */
public interface ScCourseClaMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<ScCourseCla> {

    /**
     * 班级列表
     *
     * @param reqSearchScCourseCla
     * @param page
     * @return
     */
    List<RespCourseClaInfo> selectClaList(@Param("reqSearchScCourseCla") ReqSearchScCourseCla reqSearchScCourseCla, @Param("page")RespPage page);

    /**
     * 班级select
     * @param courseClaSelect
     * @return
     */
    List<RespCourseClaSelectInfo> selectForSelect(ReqCourseClaSelect courseClaSelect);

    /**
     * 班级数量
     * @param reqClaCount
     * @return
     */
    Integer selectClaCount(ReqClaCount reqClaCount);

    /**
     * 班级数量
     * @param tenantId
     * @return
     */
    @SqlParser(filter = true)
    Integer selectTenantClaCount(String tenantId);

    /**
     * 班级在读学员数量
     * @param claId
     * @return
     */
    Integer selectStudentCnt(Long claId);
}
