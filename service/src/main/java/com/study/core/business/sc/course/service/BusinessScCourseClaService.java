package com.study.core.business.sc.course.service;

import cn.xluobo.business.sc.course.domain.req.ReqAddScCourseCla;
import cn.xluobo.business.sc.course.domain.req.ReqSearchScCourseCla;
import cn.xluobo.business.sc.course.domain.resp.RespBusinessChooseCourseCharge;
import cn.xluobo.business.sc.course.domain.resp.RespCourseClaInfo;
import cn.xluobo.business.sc.course.domain.resp.cla.RespClaAllDetailInfo;
import cn.xluobo.business.sc.course.repo.mapper.ScCourseClaMapper;
import cn.xluobo.business.sc.course.repo.model.ScClaTime;
import cn.xluobo.business.sc.course.repo.model.ScCourse;
import cn.xluobo.business.sc.course.repo.model.ScCourseCla;
import cn.xluobo.business.sc.student.repo.mapper.ScStudentMapper;
import cn.xluobo.business.sc.student.repo.model.ScStudentCourse;
import cn.xluobo.business.sc.student.service.IScStudentCourseService;
import cn.xluobo.business.sc.student.service.IScStudentService;
import cn.xluobo.business.sys.admin.repo.model.SysDept;
import cn.xluobo.business.sys.admin.service.ISysDeptService;
import cn.xluobo.business.sys.staff.repo.model.SysStaff;
import cn.xluobo.business.sys.staff.service.ISysStaffService;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.core.api.APIBaseResponse;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.api.ApiResEnums;
import cn.xluobo.core.page.RespPage;
import cn.xluobo.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessScCourseClaService {

    @Autowired
    private IScCourseClaService scCourseClaService;
    @Autowired
    private IScCourseService courseService;
    @Autowired
    private IScCourseChargeService courseChargeService;
    @Autowired
    private ISysStaffService teacherService;
    @Autowired
    private ScCourseClaMapper scCourseClaMapper;
    @Autowired
    private ScStudentMapper studentMapper;
    @Autowired
    private IScStudentService studentService;
    @Autowired
    private IScStudentCourseService studentCourseService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private IScClaTimeRuleService claTimeRuleService;
    @Autowired
    private IScClaTimeService claTimeService;

    /**
     * 查询
     *
     * @param reqSearchScCourseCla
     * @return
     */
    public APIResponse searchList(ReqSearchScCourseCla reqSearchScCourseCla) {
        RespPage<RespCourseClaInfo> page = new RespPage(reqSearchScCourseCla.getPageNum(), reqSearchScCourseCla.getPageSize());
        List<RespCourseClaInfo> claList = scCourseClaMapper.selectClaList(reqSearchScCourseCla, page);
        page.setRows(claList);
        return APIResponse.toAPIResponse(page);
    }

    /**
     * 详情
     *
     * @param claId
     * @return
     */
    public APIResponse detailById(Long claId) {
        if (null == claId) {
            return APIResponse.toAPIResponse(null);
        }
        Map<String, Object> resultMap = Maps.newHashMap();
        // 班级信息
        ScCourseCla detailInfo = scCourseClaService.getById(claId);
        if (null == detailInfo) {
            return APIResponse.toAPIResponse(null);
        }
        // 课程信息
        ScCourse scCourse = courseService.getById(detailInfo.getCourseId());
        // 是否允许变更课程，有报名不允许变更课程
        APIBaseResponse canChangeCourse = scCourseClaService.canChangeCourse(claId);
        resultMap.put("canChangeCourse", canChangeCourse.isSuccess());

        resultMap.put("claInfo", detailInfo);
        resultMap.put("claCourseInfo", scCourse);

        return APIResponse.toAPIResponse(resultMap);
    }

    /**
     * 班级详情
     *
     * @param claId
     * @return
     */
    public RespClaAllDetailInfo allDetailInfoById(Long claId) {
        if (null == claId) {
            return null;
        }
        // 班级信息
        ScCourseCla courseCla = scCourseClaService.getById(claId);
        if (null == courseCla) {
            return null;
        }

        // 班级当前学员数量
        Integer studentCnt = scCourseClaService.selectStudentCnt(claId);
        courseCla.setStudentCnt(studentCnt);

        // 课程信息
        ScCourse scCourse = courseService.getById(courseCla.getCourseId());


        // 课程收费信息
        List<RespBusinessChooseCourseCharge> courseChargeList = courseChargeService.courseChargeList(scCourse.getCourseId(), null);

        // 校区
        if (null != courseCla.getDepartId()) {
            SysDept sysDept = deptService.getById(courseCla.getDepartId());
            courseCla.setDeptName(sysDept.getDeptName());
        }

        // 教师
        if (null != courseCla.getStaffId()) {
            SysStaff staff = teacherService.getById(courseCla.getStaffId());
            courseCla.setTeacherName(staff.getStaffName());
        }

        // 上课时间
        List<String> claTimeInfo = claTimeRuleService.selectClaTimeInfo(claId);

        return RespClaAllDetailInfo.builder()
                .courseCla(courseCla)
                .course(scCourse)
                .courseChargeList(courseChargeList)
                .claTimeList(claTimeInfo)
                .build();
    }

    /**
     * 添加
     *
     * @param scCourseCla
     * @return
     */
    public APIResponse addScCourseCla(ReqAddScCourseCla scCourseCla) {

        LoginUser loginUser = LoginUserUtil.getLoginUser();

        // 课程编号是否存在
        ScCourse course = courseService.getById(scCourseCla.getCourseId());
        if (null == course) {
            return APIResponse.toExceptionResponse("课程不存在,请重新选择后提交。");
        }

        // 教师是否存在
        SysStaff teacher = teacherService.getById(scCourseCla.getStaffId());
        if (null == teacher) {
            return APIResponse.toExceptionResponse("教师不存在,请重新选择后提交。");
        }

        // 新建班级
        ScCourseCla cla = scCourseCla.getScCourseCla(loginUser);
        APIBaseResponse checkParam = cla.checkParam();
        if (!checkParam.isSuccess()) {
            return APIResponse.toExceptionResponse(checkParam.getRespMsg());
        }
        boolean addScCourseCla = scCourseClaService.save(cla);
        if (addScCourseCla) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param scCourseCla
     * @return
     */
    public APIResponse updateScCourseCla(ScCourseCla scCourseCla) {
        if (null == scCourseCla.getClaId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }

        ScCourseCla dbClaInfo = scCourseClaService.getById(scCourseCla.getClaId());
        Long dbCourseId = dbClaInfo.getCourseId();

        // 课程编号是否存在
        ScCourse course = courseService.getById(scCourseCla.getCourseId());
        if (null == course) {
            return APIResponse.toExceptionResponse("课程不存在,请重新选择后提交。");
        }

        // 教师是否存在
        SysStaff teacher = teacherService.getById(scCourseCla.getStaffId());
        if (null == teacher) {
            return APIResponse.toExceptionResponse("教师不存在,请重新选择后提交。");
        }

        // 修改课程，校验是否允许修改所属课程
        if (!dbCourseId.equals(scCourseCla.getCourseId())) {
            APIBaseResponse canChangeCourse = scCourseClaService.canChangeCourse(scCourseCla.getClaId());
            if (!canChangeCourse.isSuccess()) {
                return APIResponse.toExceptionResponse(canChangeCourse.getRespMsg());
            }
        }

        LoginUser loginUser = LoginUserUtil.getLoginUser();
        scCourseCla.setLastUpdateUser(loginUser.getUserId());
        scCourseCla.setLastUpdateTime(new Date());
        boolean updateScCourseCla = scCourseClaService.updateById(scCourseCla);
        if (updateScCourseCla) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param claIds
     * @return
     */
    public APIResponse deleteById(Long[] claIds) {
        if (null == claIds || claIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }

        // 有学员无法 删除
        QueryWrapper<ScStudentCourse> qwSc = new QueryWrapper<>();
        qwSc.in("cla_id", claIds);
        int studentCourseCount = studentCourseService.count(qwSc);
        if (studentCourseCount != 0) {
            return APIResponse.toExceptionResponse("该班级内有学员,无法删除班级");
        }

        // 已排课不允许删除
        QueryWrapper<ScClaTime> qwSct = new QueryWrapper<>();
        qwSct.in("cla_id", claIds);
        int claTimeCount = claTimeService.count(qwSct);
        if (claTimeCount != 0) {
            return APIResponse.toExceptionResponse("该班级已排课,无法删除班级");
        }

        boolean deleteScCourseCla = scCourseClaService.removeByIds(Arrays.asList(claIds));

        if (deleteScCourseCla) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }
}
