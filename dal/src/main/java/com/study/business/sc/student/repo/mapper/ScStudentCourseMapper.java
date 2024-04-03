package com.study.business.sc.student.repo.mapper;

import com.study.business.sc.student.domain.req.ReqSearchStuCourseSignUp;
import com.study.business.sc.student.domain.req.ReqSearchStudentCourse;
import com.study.business.sc.student.domain.req.ReqSearchStudentCourseCla;
import com.study.business.sc.student.domain.resp.RespStuCourseSignUpStudent;
import com.study.business.sc.student.domain.resp.RespCourseClaStudent;
import com.study.business.sc.student.domain.resp.RespStudentCourse;
import com.study.business.sc.student.repo.model.ScStudentCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 学生课程 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-08-21
 */
public interface ScStudentCourseMapper extends BaseMapper<ScStudentCourse> {

    /**
     * 学生在读课程名称
     * @param studentId
     * @return
     */
    List<String> selectStudentCourseNameList(Long studentId);

    /**
     * 学生报读校区
     * @param studentId
     * @return
     */
    List<String> selectStudentDeptNameList(Long studentId);

    /**
     * 课程、班级 学生列表
     *
     * @param reqSearchStudentCourseCla
     * @param page
     * @return
     */
    List<RespCourseClaStudent> selectStudentList(@Param("reqSearchStudentCourseCla") ReqSearchStudentCourseCla reqSearchStudentCourseCla, @Param("page")Page page);

    /**
     * 应到人数
     * @param claId
     * @return
     */
    int selectClaNeedAttendCnt(Long claId);

    /**
     * 学生报读课程列表
     * @param searchStudentCourse
     * @return
     */
    List<RespStudentCourse> selectStudentCourseList(ReqSearchStudentCourse searchStudentCourse);

    /**
     * 当报读课程总课时、总费用 为0时，删除报读
     * @param studentCourseId
     * @return
     */
    int deleteWhenTotalHourZeroForInvalid(Long studentCourseId);

    /**
     * 当报读课程总天数、总费用 为0时，删除报读
     * @param studentCourseId
     * @return
     */
    int deleteWhenTotalDayZeroForInvalid(Long studentCourseId);

    /**
     * 在读学生 剩余天数 小于 minBalanceDay 的数量
     * 未生效天数 + 生效剩余天数 < minBalanceDay
     * @param minBalanceDay 最小剩余天数
     * @return
     */
    Integer selectWillExpireDateCount(Integer minBalanceDay);

    /**
     * 在读学生 剩余课时 小于 minBalanceHour 的数量
     * 剩余课时 - 过期课时 < minBalanceHour
     * @param minBalanceHour 最小剩余课时数
     * @return
     */
    Integer selectWillExpireHourCount(Integer minBalanceHour);

    /**
     * 学生报读列表
     *
     * @param reqSearchStuCourseSignUp
     * @param page
     * @return
     */
    List<RespStuCourseSignUpStudent> selectStudentSignUpList(@Param("reqSearchStuCourseSignUp") ReqSearchStuCourseSignUp reqSearchStuCourseSignUp, @Param("page")Page page);
}
