package com.study.business.sc.course.service;

import com.study.business.sc.course.domain.export.ExpCourse;
import com.study.business.sc.course.domain.req.ReqBusinessOrderCourseDetail;
import com.study.business.sc.course.domain.req.ReqSearchScCourse;
import com.study.business.sc.course.domain.req.ReqSelect;
import com.study.business.sc.course.domain.req.course.ReqAddScCourse;
import com.study.business.sc.course.domain.req.course.ReqAddScCourseChargeItem;
import com.study.business.sc.course.domain.req.course.ReqChangeScCourse;
import com.study.business.sc.course.domain.resp.RespBusinessChooseCourseCharge;
import com.study.business.sc.course.domain.resp.RespBusinessChooseCourseInfo;
import com.study.business.sc.course.domain.resp.course.RespScCourseDetail;
import com.study.business.sc.course.domain.resp.course.RespSearchCourse;
import com.study.business.sc.course.repo.mapper.ScCourseMapper;
import com.study.business.sc.course.repo.model.ScCourse;
import com.study.business.sc.course.repo.model.ScCourseCharge;
import com.study.business.sc.course.repo.model.ScCourseCla;
import com.study.business.sc.course.repo.model.ScCourseType;
import com.study.business.sc.student.repo.model.ScStudentCourse;
import com.study.business.sc.student.service.IScStudentCourseService;
import com.study.business.sys.admin.domain.resp.RespTreeSelect;
import com.study.business.sys.admin.repo.model.SysDept;
import com.study.business.sys.admin.service.BusinessSysDeptService;
import com.study.business.sys.admin.service.BusinessSysDictDataService;
import com.study.business.sys.admin.service.ISysDeptService;
import com.study.business.tool.export.strategy.CourseExportMergeStrategy;
import com.study.config.login.LoginUser;
import com.study.config.properties.UploadConfigProperties;
import com.study.core.api.APIBaseResponse;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.core.page.RespPage;
import com.study.utils.LoginUserUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessScCourseService {

    @Autowired
    private IScCourseService scCourseService;
    @Autowired
    private IScCourseClaService courseClaService;
    @Autowired
    private ScCourseMapper courseMapper;
    @Autowired
    private IScCourseChargeService courseChargeService;
    @Autowired
    private BusinessSysDeptService deptService;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private UploadConfigProperties uploadConfigProperties;
    @Autowired
    private BusinessSysDictDataService dictDataService;
    @Autowired
    private IScStudentCourseService studentCourseService;
    @Autowired
    private IScCourseTypeService courseTypeService;

    /**
     * 查询
     *
     * @param reqSearchScCourse
     * @return
     */
    public APIResponse searchList(ReqSearchScCourse reqSearchScCourse) {
        RespPage<RespSearchCourse> searchCourseRespPage = scCourseService.searchCourse(reqSearchScCourse);
        return APIResponse.toAPIResponse(searchCourseRespPage);
    }

    /**
     * 查询课程列表
     * 含有学生报读状态
     *
     * @param reqSearchScCourse
     * @return
     */
    public APIResponse selectCourseListWithStudentCourse(ReqSearchScCourse reqSearchScCourse) {
        RespPage respPage = new RespPage(reqSearchScCourse.getPageNum(), reqSearchScCourse.getPageSize());
        List<RespSearchCourse> courseList = courseMapper.selectCourseListWithStudentCourse(reqSearchScCourse, respPage);
        respPage.setRows(courseList);
        return APIResponse.toAPIResponse(respPage);
    }

    /**
     * 前端select
     *
     * @return
     */
    public APIResponse select(ReqSelect reqSelect) {
        QueryWrapper qw = new QueryWrapper();
        qw.select("course_name", "course_id");

        if (StringUtils.isNotEmpty(reqSelect.getSearch())) {
            qw.like("course_name", reqSelect.getSearch());
        }

        qw.orderByDesc("create_time");
        List<ScCourse> list = scCourseService.list(qw);
        return APIResponse.toAPIResponse(list);
    }

    /**
     * 详情
     *
     * @param courseId
     * @return
     */
    public APIResponse detailById(Long courseId) {
        if (null == courseId) {
            return APIResponse.toAPIResponse(ApiResEnums.PARAM_FAIL);
        }

        // 课程详情
        ScCourse detailInfo = scCourseService.getById(courseId);


        RespScCourseDetail respScCourseDetail = new RespScCourseDetail();
        respScCourseDetail.setCourseId(detailInfo.getCourseId());
        respScCourseDetail.setCourseName(detailInfo.getCourseName());
        respScCourseDetail.setCourseTypeId(detailInfo.getCourseTypeId());
        respScCourseDetail.setTeachingMode(detailInfo.getTeachingMode());
        respScCourseDetail.setCourseIntro(detailInfo.getCourseIntro());
        respScCourseDetail.setCourseCampus("part");
        respScCourseDetail.setPartCampus(new String[]{});

        if(null != detailInfo.getCourseTypeId()) {
            ScCourseType courseType = courseTypeService.getById(detailInfo.getCourseTypeId());
            respScCourseDetail.setCourseTypeName(courseType.getCourseType());
        }



        List<ReqAddScCourseChargeItem> feeModeHourList = Lists.newArrayList();
        List<ReqAddScCourseChargeItem> feeModeDateList = Lists.newArrayList();
        List<ReqAddScCourseChargeItem> feeModeCycleList = Lists.newArrayList();

        // 上课校区
        List<String> partCampusList = Lists.newArrayList();

        // 所有校区id 对应名称 map
        Map<String, String> campusMap = Maps.newHashMap();
        List<RespTreeSelect> campusList = deptService.campusList();
        for (RespTreeSelect respTreeSelect : campusList) {
            campusMap.put(respTreeSelect.getId(), respTreeSelect.getLabel());
        }
        campusMap.put("-1", "全部校区");

        // 课程收费配置信息
        QueryWrapper<ScCourseCharge> qw = new QueryWrapper<>();
        qw.eq("course_id", detailInfo.getCourseId());
        List<ScCourseCharge> courseChargeList = courseChargeService.list(qw);
        for (ScCourseCharge courseCharge : courseChargeList) {
            ReqAddScCourseChargeItem chargeItem = new ReqAddScCourseChargeItem();
            Long departId = courseCharge.getDepartId();
            if (!partCampusList.contains(departId.toString()) && departId.compareTo(-1L) != 0) {
                partCampusList.add(departId.toString());
            }
            String chargeType = courseCharge.getChargeType();
            chargeItem.setChargeId(courseCharge.getChargeId());
            chargeItem.setCampusId(courseCharge.getDepartId());
            chargeItem.setCampusName(campusMap.get(courseCharge.getDepartId().toString()));
            if ("hour".equals(chargeType)) {
                chargeItem.setCnt(courseCharge.getCount());
                chargeItem.setTotalFee(courseCharge.getTotalFee());
                feeModeHourList.add(chargeItem);
            } else if ("date".equals(chargeType)) {
                chargeItem.setCnt(courseCharge.getCount());
                chargeItem.setTotalFee(courseCharge.getTotalFee());
                chargeItem.setDateType(courseCharge.getDateUnit());
                feeModeDateList.add(chargeItem);
            } else if ("cycle".equals(chargeType)) {
                chargeItem.setCnt(courseCharge.getCount());
                chargeItem.setTotalFee(courseCharge.getTotalFee());
                feeModeCycleList.add(chargeItem);
            }
        }

        // 上课校区  所有或部分校区
        if (partCampusList.size() == 0) {
            respScCourseDetail.setCourseCampus("all");
        } else {
            respScCourseDetail.setCourseCampus("part");
            respScCourseDetail.setPartCampus(partCampusList.toArray(new String[partCampusList.size()]));
        }

        respScCourseDetail.setFeeModeHourList(feeModeHourList);
        respScCourseDetail.setFeeModeDateList(feeModeDateList);
        respScCourseDetail.setFeeModeCycleList(feeModeCycleList);
        if (!feeModeHourList.isEmpty()) {
            respScCourseDetail.setFeeModeHour(true);
        }
        if (!feeModeDateList.isEmpty()) {
            respScCourseDetail.setFeeModeDate(true);
        }
        if (!feeModeCycleList.isEmpty()) {
            respScCourseDetail.setFeeModeCycle(true);
        }

        return APIResponse.toAPIResponse(respScCourseDetail);
    }

    /**
     * 添加课程
     *
     * @param reqAddScCourse
     * @return
     */
    public APIResponse addScCourse(ReqAddScCourse reqAddScCourse) {
        APIBaseResponse checkParam = reqAddScCourse.checkParam();
        if (!checkParam.isSuccess()) {
            return APIResponse.toExceptionResponse(checkParam.getRespMsg());
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();

        // course 表保存
        ScCourse scCourse = new ScCourse();
        scCourse.setCourseName(reqAddScCourse.getCourseName());
        scCourse.setCourseTypeId(reqAddScCourse.getCourseTypeId());
        scCourse.setTeachingMode(reqAddScCourse.getTeachingMode());
        scCourse.setCourseIntro(reqAddScCourse.getCourseIntro());
        scCourse.setCreateUser(loginUser.getUserId());
        scCourse.setLastUpdateUser(loginUser.getUserId());
        boolean addScCourse = scCourseService.save(scCourse);

        // course_charge保存

        // 按课时收费
        if (reqAddScCourse.isFeeModeHour()) {
            List<ReqAddScCourseChargeItem> feeModeHourList = reqAddScCourse.getFeeModeHourList();
            feeModeHourList.forEach(item -> {
                ScCourseCharge courseCharge = new ScCourseCharge();
                courseCharge.setCourseId(scCourse.getCourseId());
                courseCharge.setDepartId(item.getCampusId());
                courseCharge.setChargeType("hour");
                courseCharge.setCount(item.getCnt());
                courseCharge.setTotalFee(item.getTotalFee());
                courseChargeService.save(courseCharge);
            });
        }

        // 按时间收费
        if (reqAddScCourse.isFeeModeDate()) {
            List<ReqAddScCourseChargeItem> feeModeDateList = reqAddScCourse.getFeeModeDateList();
            feeModeDateList.forEach(item -> {
                ScCourseCharge courseCharge = new ScCourseCharge();
                courseCharge.setCourseId(scCourse.getCourseId());
                courseCharge.setDepartId(item.getCampusId());
                courseCharge.setChargeType("date");
                courseCharge.setCount(item.getCnt());
                courseCharge.setTotalFee(item.getTotalFee());
                courseCharge.setDateUnit(item.getDateType());
                courseChargeService.save(courseCharge);
            });
        }

        // 按期收费
        if (reqAddScCourse.isFeeModeCycle()) {
            List<ReqAddScCourseChargeItem> feeModeCycleList = reqAddScCourse.getFeeModeCycleList();
            feeModeCycleList.forEach(item -> {
                ScCourseCharge courseCharge = new ScCourseCharge();
                courseCharge.setCourseId(scCourse.getCourseId());
                courseCharge.setDepartId(item.getCampusId());
                courseCharge.setChargeType("cycle");
                courseCharge.setCount(item.getCnt());
                courseCharge.setTotalFee(item.getTotalFee());
                courseChargeService.save(courseCharge);
            });
        }

        return APIResponse.toOkResponse();
    }

    /**
     * 更新
     *
     * @param reqChangeScCourse
     * @return
     */
    public APIResponse updateScCourse(ReqChangeScCourse reqChangeScCourse) {
        APIBaseResponse checkParam = reqChangeScCourse.checkParam();
        if (!checkParam.isSuccess()) {
            return APIResponse.toExceptionResponse(checkParam.getRespMsg());
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();

        // 更新课程信息
        ScCourse updateCourse = new ScCourse();
        updateCourse.setCourseId(reqChangeScCourse.getCourseId());
        updateCourse.setCourseName(reqChangeScCourse.getCourseName());
        updateCourse.setCourseTypeId(reqChangeScCourse.getCourseTypeId());
        updateCourse.setTeachingMode(reqChangeScCourse.getTeachingMode());
        updateCourse.setCourseIntro(reqChangeScCourse.getCourseIntro());
        updateCourse.setLastUpdateUser(loginUser.getUserId());
        updateCourse.setLastUpdateTime(new Date());
        boolean updateScCourse = scCourseService.updateById(updateCourse);

        // course_charge 删除
        UpdateWrapper uw = new UpdateWrapper();
        uw.eq("course_id", updateCourse.getCourseId());
        courseChargeService.remove(uw);

        // course_charge 新增

        // 按课时收费
        if (reqChangeScCourse.isFeeModeHour()) {
            List<ReqAddScCourseChargeItem> feeModeHourList = reqChangeScCourse.getFeeModeHourList();
            feeModeHourList.forEach(item -> {
                ScCourseCharge courseCharge = new ScCourseCharge();
                courseCharge.setCourseId(updateCourse.getCourseId());
                courseCharge.setDepartId(item.getCampusId());
                courseCharge.setChargeType("hour");
                courseCharge.setCount(item.getCnt());
                courseCharge.setTotalFee(item.getTotalFee());
                courseChargeService.save(courseCharge);
            });
        }

        // 按时间收费
        if (reqChangeScCourse.isFeeModeDate()) {
            List<ReqAddScCourseChargeItem> feeModeDateList = reqChangeScCourse.getFeeModeDateList();
            feeModeDateList.forEach(item -> {
                ScCourseCharge courseCharge = new ScCourseCharge();
                courseCharge.setCourseId(updateCourse.getCourseId());
                courseCharge.setDepartId(item.getCampusId());
                courseCharge.setChargeType("date");
                courseCharge.setCount(item.getCnt());
                courseCharge.setTotalFee(item.getTotalFee());
                courseCharge.setDateUnit(item.getDateType());
                courseChargeService.save(courseCharge);
            });
        }

        // 按期收费
        if (reqChangeScCourse.isFeeModeCycle()) {
            List<ReqAddScCourseChargeItem> feeModeCycleList = reqChangeScCourse.getFeeModeCycleList();
            feeModeCycleList.forEach(item -> {
                ScCourseCharge courseCharge = new ScCourseCharge();
                courseCharge.setCourseId(updateCourse.getCourseId());
                courseCharge.setDepartId(item.getCampusId());
                courseCharge.setChargeType("cycle");
                courseCharge.setCount(item.getCnt());
                courseCharge.setTotalFee(item.getTotalFee());
                courseChargeService.save(courseCharge);
            });
        }

        return APIResponse.toOkResponse();
    }

    /**
     * 删除
     *
     * @param courseIds
     * @return
     */
    public APIResponse deleteById(Long[] courseIds) {
        if (null == courseIds || courseIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }

        // 如课程对应在用班级，不允许删除
        QueryWrapper<ScCourseCla> qw = new QueryWrapper<>();
        qw.in("course_id", courseIds);
        int courseClaCount = courseClaService.count(qw);
        if (courseClaCount != 0) {
            return APIResponse.toExceptionResponse("该课程下存在在用班级,无法删除课程");
        }

        // 有报读记录 不允许删除
        QueryWrapper<ScStudentCourse> qwSc = new QueryWrapper<>();
        qwSc.in("course_id", courseIds);
        int studentCourseCount = studentCourseService.count(qwSc);
        if (studentCourseCount != 0) {
            return APIResponse.toExceptionResponse("该课程已报读,无法删除课程");
        }

        boolean deleteScCourse = scCourseService.removeByIds(Arrays.asList(courseIds));
        if (deleteScCourse) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 变更是否开售
     *
     * @param scCourse
     * @return
     */
    public APIResponse changeCourseSale(ScCourse scCourse) {
        if (null == scCourse.getCourseId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        if (StringUtils.isEmpty(scCourse.getSale())) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        UpdateWrapper uw = new UpdateWrapper();
        uw.set("sale", scCourse.getSale());
        uw.eq("course_id", scCourse.getCourseId());
        boolean updateScCourse = scCourseService.update(uw);
        if (updateScCourse) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 导出课程
     *
     * @param reqSearchScCourse
     * @return
     */
    public APIResponse exportCourse(ReqSearchScCourse reqSearchScCourse) {
        try {
            List<ExpCourse> courseList = courseMapper.selectCourseForExport(reqSearchScCourse);
            String fileName = "exportCourse_" + System.currentTimeMillis() + UUID.randomUUID().toString();
            String fullPath = uploadConfigProperties.getTempSaveExportPath() + "/" + fileName + ".xlsx";
            CourseExportMergeStrategy<ExpCourse> mergeStrategy = new CourseExportMergeStrategy<ExpCourse>(courseList, 0, 6);
            EasyExcel.write(fullPath, ExpCourse.class)
                    .registerWriteHandler(mergeStrategy)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet("课程列表").doWrite(courseList);
            return APIResponse.toAPIResponse(fileName);
        } catch (Exception e) {
            return APIResponse.toExceptionResponse("下载文件失败" + e.getMessage());
        }
    }

    /**
     * 报名-课程详情
     *
     * @param orderCourseDetail
     * @return
     */
    public APIResponse orderCourseDetail(ReqBusinessOrderCourseDetail orderCourseDetail) {
        Long[] courseIds = orderCourseDetail.getCourseIds();
        Long deptId = orderCourseDetail.getDeptId();
        Long studentId = orderCourseDetail.getStudentId();

        if (null == courseIds || courseIds.length == 0) {
            return APIResponse.toExceptionResponse("请选择课程");
        }

        if (null == deptId) {
            return APIResponse.toExceptionResponse("请选择校区");
        }

        // 校验学生是否可报读 课程
        APIBaseResponse studentCanSignUpCourse = studentCourseService.checkStudentCanSignUpCourse(studentId, courseIds, deptId);
        if (!studentCanSignUpCourse.isSuccess()) {
            return APIResponse.toExceptionResponse(studentCanSignUpCourse.getRespMsg());
        }

        // 学生已报名课程收费模式
        Map<Long, String> studentCourseChargeTypeMap = Maps.newHashMap();
        if (null != studentId) {
            QueryWrapper qw = new QueryWrapper();
            qw.select("course_id", "charge_type");
            qw.eq("student_id", studentId);
            List<ScStudentCourse> studentCourseList = studentCourseService.list(qw);
            studentCourseChargeTypeMap = studentCourseList.stream().collect(Collectors.toMap(ScStudentCourse::getCourseId, ScStudentCourse::getChargeType));
        }

        // 部门信息
        SysDept sysDept = sysDeptService.getById(deptId);

        List<RespBusinessChooseCourseInfo> courseInfoList = Lists.newArrayList();
        for (Long courseId : courseIds) {
            ScCourse scCourse = scCourseService.getById(courseId);
            if (null == scCourse) {
                continue;
            }
            RespBusinessChooseCourseInfo chooseCourseInfo = RespBusinessChooseCourseInfo.builder()
                    .courseId(scCourse.getCourseId())
                    .courseName(scCourse.getCourseName())
                    .deptId(sysDept.getDeptId())
                    .deptName(sysDept.getDeptName())
                    .teachingMode(scCourse.getTeachingMode())
                    .build();

            // 已报读的 收费方式
            String studentCourseChargeType = studentCourseChargeTypeMap.get(courseId);

            // 转换对象
            List<RespBusinessChooseCourseCharge> chooseCourseChargeList = courseChargeService.courseChargeList(courseId, studentCourseChargeType);

            chooseCourseInfo.setCourseChargeList(chooseCourseChargeList);
            // 是否为续报
            chooseCourseInfo.setContinueCourse(StringUtils.isNotEmpty(studentCourseChargeType));
            courseInfoList.add(chooseCourseInfo);
        }
        return APIResponse.toAPIResponse(courseInfoList);
    }

    /**
     * 学生是否可报名 课程
     *
     * @param orderCourseDetail
     * @return
     */
    public APIResponse studentCanSignUpCourse(ReqBusinessOrderCourseDetail orderCourseDetail) {
        Long[] courseIds = orderCourseDetail.getCourseIds();
        Long studentId = orderCourseDetail.getStudentId();
        if (null == studentId) {
            return APIResponse.toExceptionResponse("studentId 不能为空");
        }
        if (null == courseIds || courseIds.length == 0) {
            return APIResponse.toOkResponse();
        }
        APIBaseResponse studentCanSignUpCourse = studentCourseService.checkStudentCanSignUpCourse(studentId, courseIds, null);
        if (!studentCanSignUpCourse.isSuccess()) {
            return APIResponse.toExceptionResponse(studentCanSignUpCourse.getRespMsg());
        }

        return APIResponse.toOkResponse();
    }
}
