package com.study.core.business.tool.impt.service.strategy.impl;

import cn.xluobo.business.sc.base.domain.req.ReqRoomSelect;
import cn.xluobo.business.sc.base.domain.resp.RespRoomSelect;
import cn.xluobo.business.sc.base.service.IScRoomService;
import cn.xluobo.business.sc.course.repo.model.ScCourse;
import cn.xluobo.business.sc.course.service.BusinessClaTimeRuleService;
import cn.xluobo.business.sc.course.service.IScCourseClaService;
import cn.xluobo.business.sc.course.service.IScCourseService;
import cn.xluobo.business.sys.admin.domain.resp.RespTreeSelect;
import cn.xluobo.business.sys.admin.repo.model.SysDictData;
import cn.xluobo.business.sys.admin.service.BusinessSysDeptService;
import cn.xluobo.business.sys.admin.service.BusinessSysDictDataService;
import cn.xluobo.business.sys.staff.repo.model.SysStaff;
import cn.xluobo.business.sys.staff.service.ISysStaffService;
import cn.xluobo.business.tool.export.handler.bean.SelectValidationData;
import cn.xluobo.business.tool.impt.domain.ImportCourseCla;
import cn.xluobo.business.tool.impt.listener.ImportCourseClaListener;
import cn.xluobo.business.tool.impt.service.strategy.AbstractImportStrategy;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.config.properties.UploadConfigProperties;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.utils.LoginUserUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/10 16:45
 */
@Service("import_course_cla")
@Slf4j
public class ImportCourseClaImpl extends AbstractImportStrategy {

    @Autowired
    private IScCourseService courseService;
    @Autowired
    private IScCourseClaService claService;
    @Autowired
    private BusinessSysDeptService deptService;
    @Autowired
    private ISysStaffService staffService;
    @Autowired
    private BusinessSysDictDataService dictDataService;
    @Autowired
    private BusinessClaTimeRuleService businessClaTimeRuleService;
    @Autowired
    private IScRoomService roomService;
    @Autowired
    private UploadConfigProperties uploadConfigProperties;

    private static final String FILE_TYPE = "import_course_cla";

    @Override
    public List<SelectValidationData> selectValidateConfig() {
        // 课程
        QueryWrapper<ScCourse> qwCourse = new QueryWrapper<>();
        qwCourse.select("course_name");
        qwCourse.eq("sale", "1");
        qwCourse.eq("delete_flag", "0");
        List<ScCourse> courseList = courseService.list(qwCourse);
        String[] courseNameStringArray = courseList.stream().map(ScCourse::getCourseName).toArray(String[]::new);

        // 全部校区
        List<RespTreeSelect> campusList = deptService.campusList();
        String[] campusNameStringArray = campusList.stream().map(RespTreeSelect::getLabel).toArray(String[]::new);

        // 教师列表
        List<SysStaff> teacherList = staffService.teacherList("");
        String[] teacherNameStringArray = teacherList.stream().map(SysStaff::getStaffName).toArray(String[]::new);

        // 招生状态
        List<SysDictData> recruitStatusList = dictDataService.dictTypeDataList("recruit_status");
        String[] chargeTypeStringArray = recruitStatusList.stream().map(SysDictData::getDictLabel).toArray(String[]::new);

        // 排课重复方式
        List<SysDictData> claTimeRepeatTypeList = dictDataService.dictTypeDataList("cla_time_repeat_type");
        String[] claTimeRepeatTypeStringArray = claTimeRepeatTypeList.stream().map(SysDictData::getDictLabel).toArray(String[]::new);

        // 所有教室
        List<RespRoomSelect> roomList = roomService.selectRoomSelect(new ReqRoomSelect());
        String[] roomNameStringArray = roomList.stream().map(RespRoomSelect::getRoomName).toArray(String[]::new);

        return Lists.newArrayList(
                SelectValidationData.builder().firstCol(1).lastCol(1).firstRow(1).lastRow(106).selectDataArray(courseNameStringArray).build(),
                SelectValidationData.builder().firstCol(2).lastCol(2).firstRow(1).lastRow(106).selectDataArray(campusNameStringArray).build(),
                SelectValidationData.builder().firstCol(3).lastCol(3).firstRow(1).lastRow(106).selectDataArray(teacherNameStringArray).build(),
                SelectValidationData.builder().firstCol(5).lastCol(5).firstRow(1).lastRow(106).selectDataArray(chargeTypeStringArray).build(),
                SelectValidationData.builder().firstCol(12).lastCol(12).firstRow(1).lastRow(106).selectDataArray(claTimeRepeatTypeStringArray).build(),
                SelectValidationData.builder().firstCol(14).lastCol(14).firstRow(1).lastRow(106).selectDataArray(new String[]{"过滤", "不过滤"}).build(),
                SelectValidationData.builder().firstCol(17).lastCol(17).firstRow(1).lastRow(106).selectDataArray(roomNameStringArray).build()
        );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public APIResponse importDataByFileId(Long importId, String fileId) throws IOException {

        String path = uploadConfigProperties.getTempSaveImportPath();
        path = path + "/" + FILE_TYPE;
        File file = new File(path, fileId);
        if (!file.exists()) {
            return APIResponse.toExceptionResponse("无法获取文件,请重试");
        }

        // 课程
        QueryWrapper<ScCourse> qwCourse = new QueryWrapper<>();
        qwCourse.select("course_name", "course_id");
        qwCourse.eq("sale", "1");
        qwCourse.eq("delete_flag", "0");
        List<ScCourse> courseList = courseService.list(qwCourse);
        // 全部校区
        List<RespTreeSelect> campusList = deptService.campusList();
        // 教师列表
        List<SysStaff> teacherList = staffService.teacherList("");
        // 招生状态
        List<SysDictData> recruitStatusList = dictDataService.dictTypeDataList("recruit_status");
        // 排课重复方式
        List<SysDictData> claTimeRepeatTypeList = dictDataService.dictTypeDataList("cla_time_repeat_type");
        // 所有教室
        List<RespRoomSelect> roomList = roomService.selectRoomSelect(new ReqRoomSelect());
        // 上课星期
        List<SysDictData> weekDayList = dictDataService.dictTypeDataList("week_day");


        Map<String, Long> courseMap = Maps.newHashMap();
        Map<String, String> campusMap = Maps.newHashMap();
        Map<String, Long> teacherMap = Maps.newHashMap();
        Map<String, String> recruitStatusMap = Maps.newHashMap();
        Map<String, String> claTimeRepeatTypeMap = Maps.newHashMap();
        Map<String, Long> roomMap = Maps.newHashMap();
        Map<String, String> weekDayMap = Maps.newHashMap();

        courseList.forEach(item -> {
            courseMap.put(item.getCourseName(), item.getCourseId());
        });
        campusList.forEach(item -> {
            campusMap.put(item.getLabel(), item.getId());
        });
        teacherList.forEach(item -> {
            teacherMap.put(item.getStaffName(), item.getStaffId());
        });
        recruitStatusList.forEach(item -> {
            recruitStatusMap.put(item.getDictLabel(), item.getDictValue());
        });
        claTimeRepeatTypeList.forEach(item -> {
            claTimeRepeatTypeMap.put(item.getDictLabel(), item.getDictValue());
        });
        roomList.forEach(item -> {
            roomMap.put(item.getRoomName(), item.getRoomId());
        });
        weekDayList.forEach(item -> {
            weekDayMap.put(item.getDictLabel(), item.getDictValue());
        });

        LoginUser loginUser = LoginUserUtil.getLoginUser();

        ImportCourseClaListener importListener = new ImportCourseClaListener(claService, businessClaTimeRuleService);
        importListener.setNeedSave(true);
        importListener.setCourseMap(courseMap);
        importListener.setCampusMap(campusMap);
        importListener.setTeacherMap(teacherMap);
        importListener.setRecruitStatusMap(recruitStatusMap);
        importListener.setClaTimeRepeatTypeMap(claTimeRepeatTypeMap);
        importListener.setRoomMap(roomMap);
        importListener.setWeekDayMap(weekDayMap);
        importListener.setLoginUserId(loginUser.getUserId());
        importListener.setImportId(importId);

        InputStream is = new FileInputStream(file);
        EasyExcel.read(is, ImportCourseCla.class, importListener).sheet().autoTrim(true).headRowNumber(5).doRead();

        int successRecord = importListener.getSuccessRecord();
        int failRecord = importListener.getFailRecord();
        file.delete();
        return APIResponse.toAPIResponse("导入成功" + successRecord + "条,导入失败:" + failRecord);
    }

    @Override
    public APIResponse checkData(MultipartFile multipartFile) throws IOException {
        long fileSize = multipartFile.getSize();
        if (fileSize > 1024 * 1024 * 2) {
            return APIResponse.toExceptionResponse("导入文件需小于2M");
        }
        // 课程
        QueryWrapper<ScCourse> qwCourse = new QueryWrapper<>();
        qwCourse.select("course_name", "course_id");
        qwCourse.eq("sale", "1");
        qwCourse.eq("delete_flag", "0");
        List<ScCourse> courseList = courseService.list(qwCourse);
        // 全部校区
        List<RespTreeSelect> campusList = deptService.campusList();
        // 教师列表
        List<SysStaff> teacherList = staffService.teacherList("");
        // 招生状态
        List<SysDictData> recruitStatusList = dictDataService.dictTypeDataList("recruit_status");
        // 排课重复方式
        List<SysDictData> claTimeRepeatTypeList = dictDataService.dictTypeDataList("cla_time_repeat_type");
        // 所有教室
        List<RespRoomSelect> roomList = roomService.selectRoomSelect(new ReqRoomSelect());
        // 上课星期
        List<SysDictData> weekDayList = dictDataService.dictTypeDataList("week_day");


        Map<String, Long> courseMap = Maps.newHashMap();
        Map<String, String> campusMap = Maps.newHashMap();
        Map<String, Long> teacherMap = Maps.newHashMap();
        Map<String, String> recruitStatusMap = Maps.newHashMap();
        Map<String, String> claTimeRepeatTypeMap = Maps.newHashMap();
        Map<String, Long> roomMap = Maps.newHashMap();
        Map<String, String> weekDayMap = Maps.newHashMap();

        courseList.forEach(item -> {
            courseMap.put(item.getCourseName(), item.getCourseId());
        });
        campusList.forEach(item -> {
            campusMap.put(item.getLabel(), item.getId());
        });
        teacherList.forEach(item -> {
            teacherMap.put(item.getStaffName(), item.getStaffId());
        });
        recruitStatusList.forEach(item -> {
            recruitStatusMap.put(item.getDictLabel(), item.getDictValue());
        });
        claTimeRepeatTypeList.forEach(item -> {
            claTimeRepeatTypeMap.put(item.getDictLabel(), item.getDictValue());
        });
        roomList.forEach(item -> {
            roomMap.put(item.getRoomName(), item.getRoomId());
        });
        weekDayList.forEach(item -> {
            weekDayMap.put(item.getDictLabel(), item.getDictValue());
        });

        LoginUser loginUser = LoginUserUtil.getLoginUser();

        ImportCourseClaListener importListener = new ImportCourseClaListener(claService, businessClaTimeRuleService);
        importListener.setNeedSave(false);
        importListener.setCourseMap(courseMap);
        importListener.setCampusMap(campusMap);
        importListener.setTeacherMap(teacherMap);
        importListener.setRecruitStatusMap(recruitStatusMap);
        importListener.setClaTimeRepeatTypeMap(claTimeRepeatTypeMap);
        importListener.setRoomMap(roomMap);
        importListener.setWeekDayMap(weekDayMap);
        importListener.setLoginUserId(loginUser.getUserId());

        // 保存文件
        String fileId = IdWorker.getIdStr();
        String fileName = DateTime.now().toString("yyyyMMddHHmmss") + "_" + fileId;
        String originalFileName = multipartFile.getOriginalFilename();
        String suffix = FilenameUtils.getExtension(originalFileName);
        if (StringUtils.isNotEmpty(suffix)) {
            fileName = fileName + "." + suffix;
        }

        String path = uploadConfigProperties.getTempSaveImportPath();
        //上传文件夹
        path = path + "/" + FILE_TYPE;
        File uploadFileDir = new File(path);
        if (!uploadFileDir.exists()) {
            uploadFileDir.mkdir();
        }
        File uploadFile = new File(uploadFileDir, fileName);
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), uploadFile);

        InputStream is = new FileInputStream(uploadFile);
        EasyExcel.read(is, ImportCourseCla.class, importListener).sheet().autoTrim(true).headRowNumber(5).doRead();

        List<ImportCourseCla> saveCourseClaList = importListener.getSaveCourseClaList();
        List<ImportCourseCla> failCourseClaList = importListener.getFailCourseClaList();
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("successList", saveCourseClaList);
        resultMap.put("failList", failCourseClaList);
        resultMap.put("fileId", fileName);

        return APIResponse.toAPIResponse(resultMap);
    }
}
