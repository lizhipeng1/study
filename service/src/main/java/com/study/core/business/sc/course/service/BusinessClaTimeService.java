package com.study.core.business.sc.course.service;

import cn.xluobo.business.sc.base.repo.model.ScRoom;
import cn.xluobo.business.sc.base.service.IScRoomService;
import cn.xluobo.business.sc.course.domain.req.time.ReqSearchClaTime;
import cn.xluobo.business.sc.course.domain.resp.time.*;
import cn.xluobo.business.sc.course.repo.enums.ClaTimeStatusEnums;
import cn.xluobo.business.sc.course.repo.mapper.ScClaTimeMapper;
import cn.xluobo.business.sc.course.repo.model.ScClaTime;
import cn.xluobo.business.sc.course.repo.model.ScClaTimeAttend;
import cn.xluobo.business.sc.course.repo.model.ScCourse;
import cn.xluobo.business.sc.course.repo.model.ScCourseCla;
import cn.xluobo.business.sc.student.domain.req.ReqClaTimeAttend;
import cn.xluobo.business.sc.student.service.BusinessScStudentCourseService;
import cn.xluobo.business.sc.student.service.IScStudentCourseService;
import cn.xluobo.business.sys.admin.repo.model.SysDept;
import cn.xluobo.business.sys.admin.service.ISysDeptService;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.api.ApiResEnums;
import cn.xluobo.core.constants.SysConstant;
import cn.xluobo.core.page.RespPage;
import cn.xluobo.core.utils.DateUtil;
import cn.xluobo.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/16 17:05
 */
@Service
@Transactional
public class BusinessClaTimeService {

    @Autowired
    private IScClaTimeService claTimeService;
    @Autowired
    private IScClaTimeRuleService claTimeRuleService;
    @Autowired
    private ScClaTimeMapper claTimeMapper;
    @Autowired
    private IScRoomService roomService;
    @Autowired
    private IScCourseClaService claService;
    @Autowired
    private IScCourseService courseService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private IScClaTimeAttendService claTimeAttendService;
    @Autowired
    private IScStudentCourseService studentCourseService;
    @Autowired
    private BusinessScStudentCourseService businessScStudentCourseService;

    /**
     * 按周获取课表
     *
     * @param reqSearchClaTime
     * @return
     */
    public APIResponse searchListForCalendar(ReqSearchClaTime reqSearchClaTime) {
        String beginDate = reqSearchClaTime.getBeginDate();
        String endDate = reqSearchClaTime.getEndDate();
        Long deptId = reqSearchClaTime.getDeptId();

        if (StringUtils.isAnyEmpty(beginDate, endDate)) {
            return APIResponse.toOkResponse();
        }

        DateTime cycleBegin = DateUtil.yyyMMddDayBegin(beginDate);
        DateTime cycleEnd = DateUtil.yyyMMddDayEnd(endDate);

        if (1 != cycleBegin.getDayOfWeek() || 7 != cycleEnd.getDayOfWeek()) {
            return APIResponse.toOkResponse();
        }

        // 日历数据  格式为：时间->星期->课程
        Map<Integer, Map<Integer, List<RespClaTimeCalendar>>> claTimeCalendarMap = Maps.newHashMap();
        SysConstant.CLA_TIME_MAP.forEach((claTimeKey, claTimeValue) -> {
            Map<Integer, List<RespClaTimeCalendar>> weekDayMap = Maps.newHashMap();
            SysConstant.WEEK_DAY_MAP.forEach((weekDayKey, weekDay) -> {
                List<RespClaTimeCalendar> claTimeArrayList = Lists.newArrayList();
                weekDayMap.put(weekDayKey, claTimeArrayList);
            });
            claTimeCalendarMap.put(claTimeKey, weekDayMap);
        });

        // 获取排课信息
        List<RespClaTimeCalendar> respClaTimeList = claTimeMapper.selectListForCalendar(reqSearchClaTime);

        // 将排课信息 入到 claTimeCalendarMap
        respClaTimeList.forEach(item -> {
            Integer weekDay = item.getWeekDay();
            Integer startHour = item.getStartHour();

            if (claTimeCalendarMap.containsKey(startHour)) {
                claTimeCalendarMap.get(startHour).get(weekDay).add(item);
            } else if (claTimeCalendarMap.containsKey(startHour - 1)) {
                // 每两个小时 一个上课时段，所以-1
                claTimeCalendarMap.get(startHour - 1).get(weekDay).add(item);
            }
        });


        RespBusinessClaTimeCalendar timeCalendar = new RespBusinessClaTimeCalendar();
        // 每行数据
        List<ClaTimeContainer> claTimeContainerList = Lists.newArrayList();
        // 星期排课数量
        Map<Integer, Integer> columnTitleTimeCountMap = Maps.newHashMap();

        // 每行数据
        claTimeCalendarMap.forEach((claTimeKey, claTimeMap) -> {
            ClaTimeContainer claTimeContainer = new ClaTimeContainer();
            claTimeContainer.setTime(SysConstant.CLA_TIME_MAP.get(claTimeKey));

            Map<Integer, List<ClaTimeCalendarItem>> claTimeWeekDayMap = Maps.newHashMap();
            claTimeMap.forEach((weekDayKey, list) -> {

                // 排课数量
                if (columnTitleTimeCountMap.containsKey(weekDayKey)) {
                    columnTitleTimeCountMap.put(weekDayKey, columnTitleTimeCountMap.get(weekDayKey) + list.size());
                } else {
                    columnTitleTimeCountMap.put(weekDayKey, list.size());
                }

                List<ClaTimeCalendarItem> timeItemList = list.stream().map(item -> {
                    ClaTimeCalendarItem claTimeItem = new ClaTimeCalendarItem();
                    claTimeItem.transfer(item);
                    return claTimeItem;
                }).collect(Collectors.toList());
                claTimeWeekDayMap.put(weekDayKey, timeItemList);
            });
            claTimeContainer.setClaTimeWeekDayMap(claTimeWeekDayMap);
            claTimeContainerList.add(claTimeContainer);
        });
        Collections.sort(claTimeContainerList, (o1, o2) -> {
            int a = Integer.parseInt(o1.getTime().substring(0, 2));
            int b = Integer.parseInt(o2.getTime().substring(0, 2));
            if (a > b) {
                return 1;
            } else if (a < b) {
                return -1;
            } else {
                return 0;
            }
        });
        timeCalendar.setClaTimeContainer(claTimeContainerList);


        // 标题
        List<ClaTimeColumnTitle> columnTitles = Lists.newArrayList();
        while (cycleBegin.isBefore(cycleEnd)) {
            int dayOfWeek = cycleBegin.getDayOfWeek();

            ClaTimeColumnTitle claTimeColumnTitle = new ClaTimeColumnTitle();
            claTimeColumnTitle.setWeekName(SysConstant.WEEK_DAY_MAP.get(dayOfWeek));
            claTimeColumnTitle.setDay(cycleBegin.toString("MM-dd"));
            claTimeColumnTitle.setCount(columnTitleTimeCountMap.get(dayOfWeek));

            columnTitles.add(claTimeColumnTitle);
            cycleBegin = cycleBegin.plusDays(1);
        }
        timeCalendar.setColumnTitles(columnTitles);

        return APIResponse.toAPIResponse(timeCalendar);
    }

    /**
     * 获取最近几天的排课
     *
     * @param reqSearchClaTime
     * @return
     */
    public List<RespClaTimeCalendar> searchRecentDayTimeList(ReqSearchClaTime reqSearchClaTime) {
        return claTimeMapper.selectListForCalendar(reqSearchClaTime);
    }

    /**
     * 获取上课记录
     *
     * @param searchClaTime
     * @return
     */
    public RespPage<RespClaTime> selectListForAttend(ReqSearchClaTime searchClaTime) {
        RespPage<RespClaTime> page = new RespPage<>(searchClaTime.getPageNum(), searchClaTime.getPageSize());
        List<RespClaTime> claTimeList = claTimeMapper.selectListForAttend(searchClaTime, page);
        page.setRows(claTimeList);
        return page;
    }

    /**
     * 添加
     *
     * @param claTime
     * @return
     */
    public APIResponse addClaTime(ScClaTime claTime) {
        if (!claTime.checkAddParam()) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }
        // 教室
        if (null != claTime.getRoomId()) {
            ScRoom room = roomService.getById(claTime.getRoomId());
            claTime.setRoomName(room.getRoomName());
        }

        ScCourseCla courseCla = claService.getById(claTime.getClaId());
        claTime.setPayHour(courseCla.getEveryStuLoseHour());
        claTime.setSource("3");
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        claTime.setCreateUser(loginUser.getUserId());
        claTime.setCreateTime(new Date());
        claTimeService.save(claTime);
        return APIResponse.toOkResponse();
    }

    /**
     * 详情
     *
     * @param courseTimeId
     * @return
     */
    public APIResponse detailById(Long courseTimeId) {
        if (null == courseTimeId) {
            return APIResponse.toAPIResponse(null);
        }
        ScClaTime claTime = claTimeService.getById(courseTimeId);
        Long claId = claTime.getClaId();
        if (null != claId) {
            ScCourseCla courseCla = claService.getById(claId);
            if (null != courseCla) {
                claTime.setDeptId(courseCla.getDepartId());
            }
        }

        return APIResponse.toAPIResponse(claTime);
    }

    /**
     * 更新
     *
     * @param claTime
     * @return
     */
    public APIResponse updateClaTime(ScClaTime claTime) {
        if (null == claTime.getCourseTimeId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }
        if (!claTime.checkUpdateParam()) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }

        ScCourseCla courseCla = claService.getById(claTime.getClaId());

        LoginUser loginUser = LoginUserUtil.getLoginUser();

        UpdateWrapper uw = new UpdateWrapper();
        uw.eq("course_time_id", claTime.getCourseTimeId());
        uw.eq("status", ClaTimeStatusEnums.WAIT_CLASS.getStatus());
        // 教室
        if (null != claTime.getRoomId()) {
            ScRoom room = roomService.getById(claTime.getRoomId());
            uw.set("room_name", room.getRoomName());
        }
        uw.set("cla_date", claTime.getClaDate());
        uw.set("start_time", claTime.getStartTime());
        uw.set("end_time", claTime.getEndTime());
        uw.set("teacher_id", claTime.getTeacherId());
        uw.set("room_id", claTime.getRoomId());
        uw.set("pay_hour", courseCla.getEveryStuLoseHour());
        uw.set("class_theme", claTime.getClassTheme());
        uw.set("last_update_user", loginUser.getUserId());
        uw.set("last_update_time", new Date());

        boolean update = claTimeService.update(uw);
        if(!update) {
            return APIResponse.toExceptionResponse("调课失败,请稍后重试");
        }

        return APIResponse.toOkResponse();
    }

    /**
     * 删除
     *
     * @param courseTimeId
     * @return
     */
    public APIResponse deleteById(Long courseTimeId) {
        if (null == courseTimeId) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        ScClaTime claTime = claTimeService.getById(courseTimeId);
        String status = claTime.getStatus();
        if ("2".equals(status)) {
            return APIResponse.toExceptionResponse("已上课,无法删除");
        }

        claTimeService.removeById(courseTimeId);

        return APIResponse.toOkResponse();
    }

    /**
     * 变更 已记上课 信息
     * @param reqClaTimeAttend
     * @return
     */
    public APIResponse changeHadClaTimeAttend(ReqClaTimeAttend reqClaTimeAttend) {
        APIResponse checkParam = reqClaTimeAttend.checkParamForUpdateHadClaTime();
        if (!checkParam.isSuccess()) {
            return checkParam;
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();

        Long courseTimeId = reqClaTimeAttend.getCourseTimeId();
        ScClaTime claTime = claTimeService.getById(courseTimeId);
        if (!ClaTimeStatusEnums.HAD_CLASS.getStatus().equals(claTime.getStatus())) {
            return APIResponse.toExceptionResponse("非已上课排课,无法变更!");
        }

        // 变更状态为待上课
        ScClaTime updateClaTime = new ScClaTime();
        updateClaTime.setCourseTimeId(courseTimeId);
        updateClaTime.setStatus(ClaTimeStatusEnums.WAIT_CLASS.getStatus());
        claTimeService.updateById(updateClaTime);

        Long claId = claTime.getClaId();
        ScCourseCla courseCla = claService.getById(claId);
        if(null == courseCla) {
            return APIResponse.toExceptionResponse("无法获取班级,无法删除");
        }
        Long courseId = courseCla.getCourseId();
        ScCourse scCourse = courseService.getById(courseId);
        if(null == scCourse) {
            return APIResponse.toExceptionResponse("无法获取课程,无法删除");
        }
        SysDept sysDept = deptService.getById(courseCla.getDepartId());
        if (null == sysDept) {
            return APIResponse.toExceptionResponse("无法获取校区信息,请稍后重试");
        }


        List<ScClaTimeAttend> dbAttendList = claTimeAttendService.getAttendList(courseTimeId);

        // 删除原上课记录
        for (ScClaTimeAttend claTimeAttend : dbAttendList) {
            // 学员日志 使用
            claTime.setCourseName(scCourse.getCourseName());
            claTime.setClaName(courseCla.getClaName());
            claTime.setDeptName(sysDept.getDeptName());
            // 恢复学员课时
            studentCourseService.recoverStudentCourseHour(claTimeAttend, claTime, loginUser);
        }

        // 删除上课记录明细 cla_time_attend
        UpdateWrapper<ScClaTimeAttend> uw = new UpdateWrapper<>();
        uw.eq("course_time_id", courseTimeId);
        claTimeAttendService.remove(uw);

        // 重新记上课
        return businessScStudentCourseService.claTimeAttend(reqClaTimeAttend);
    }

    /**
     * 删除已上课记录
     *
     * @param courseTimeId
     * @return
     */
    public APIResponse deleteHadClaTimeAttend(Long courseTimeId) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        ScClaTime claTime = claTimeService.getById(courseTimeId);
        if (null == claTime) {
            return APIResponse.toExceptionResponse("无法获取上课信息");
        }
        String status = claTime.getStatus();
        if (!"2".equals(status)) {
            return APIResponse.toExceptionResponse("暂未上课,无法删除");
        }

        Long claId = claTime.getClaId();
        ScCourseCla courseCla = claService.getById(claId);
        if(null == courseCla) {
            return APIResponse.toExceptionResponse("无法获取班级,无法删除");
        }
        Long courseId = courseCla.getCourseId();
        ScCourse scCourse = courseService.getById(courseId);
        if(null == scCourse) {
            return APIResponse.toExceptionResponse("无法获取课程,无法删除");
        }
        SysDept sysDept = deptService.getById(courseCla.getDepartId());
        if (null == sysDept) {
            return APIResponse.toExceptionResponse("无法获取校区信息,请稍后重试");
        }

        // 上课明细
        List<ScClaTimeAttend> claTimeAttendList = claTimeAttendService.getAttendList(courseTimeId);

        for (ScClaTimeAttend claTimeAttend : claTimeAttendList) {
            // 学员日志 使用
            claTime.setCourseName(scCourse.getCourseName());
            claTime.setClaName(courseCla.getClaName());
            claTime.setDeptName(sysDept.getDeptName());

            // 恢复学员课时
            studentCourseService.recoverStudentCourseHour(claTimeAttend, claTime, loginUser);
        }

        // 删除上课记录明细 cla_time_attend
        UpdateWrapper<ScClaTimeAttend> uw = new UpdateWrapper<>();
        uw.eq("course_time_id", courseTimeId);
        claTimeAttendService.remove(uw);

        // 删除上课记录 cla_time
        claTimeService.removeById(courseTimeId);

        return APIResponse.toOkResponse();
    }

}
