package com.study.business.tool.impt.service.strategy.impl;

import com.study.business.sc.course.repo.model.ScCourseType;
import com.study.business.sc.course.service.IScCourseChargeService;
import com.study.business.sc.course.service.IScCourseService;
import com.study.business.sc.course.service.IScCourseTypeService;
import com.study.business.sys.admin.domain.resp.RespTreeSelect;
import com.study.business.sys.admin.repo.model.SysDictData;
import com.study.business.sys.admin.service.BusinessSysDeptService;
import com.study.business.sys.admin.service.BusinessSysDictDataService;
import com.study.business.tool.export.handler.bean.SelectValidationData;
import com.study.business.tool.impt.domain.ImportCourse;
import com.study.business.tool.impt.listener.ImportCourseListener;
import com.study.business.tool.impt.service.strategy.AbstractImportStrategy;
import com.study.config.login.LoginUser;
import com.study.core.api.APIResponse;
import com.study.utils.LoginUserUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 导入课程select限制配置
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/2 18:56
 */
@Service("import_course")
@Slf4j
public class ImportCourseImpl extends AbstractImportStrategy {

    @Autowired
    private BusinessSysDeptService deptService;
    @Autowired
    private IScCourseTypeService scCourseTypeService;
    @Autowired
    private BusinessSysDictDataService dictDataService;
    @Autowired
    private IScCourseService courseService;
    @Autowired
    private IScCourseChargeService courseChargeService;

    @Override
    public List<SelectValidationData> selectValidateConfig() {
        // 全部校区
        List<RespTreeSelect> campusList = deptService.campusList();
        List<String> campusNameList = Lists.newArrayList("全部校区");
        List<String> allDbCampusList = campusList.stream().map(RespTreeSelect::getLabel).collect(Collectors.toList());
        campusNameList.addAll(allDbCampusList);
        String[] campusNameStringArray = campusNameList.toArray(new String[allDbCampusList.size()]);

        // 课程类型
        QueryWrapper<ScCourseType> qw = new QueryWrapper<>();
        qw.eq("in_use", "1");
        qw.orderByDesc("create_time");
        List<ScCourseType> courseTypeList = scCourseTypeService.list(qw);
        String[] courseTypeStringArray = courseTypeList.stream().map(ScCourseType::getCourseType).toArray(String[]::new);

        // 收费模式
        List<SysDictData> chargeTypeList = dictDataService.dictTypeDataList("charge_type");
        String[] chargeTypeStringArray = chargeTypeList.stream().map(SysDictData::getDictLabel).toArray(String[]::new);

        // 时间段
        List<SysDictData> dateUnitList = dictDataService.dictTypeDataList("date_unit");
        String[] dateUnitListStringArray = dateUnitList.stream().map(SysDictData::getDictLabel).toArray(String[]::new);


        return Lists.newArrayList(
                SelectValidationData.builder().firstCol(1).lastCol(1).firstRow(1).lastRow(206).selectDataArray(courseTypeStringArray).build(),
                SelectValidationData.builder().firstCol(2).lastCol(2).firstRow(1).lastRow(206).selectDataArray(new String[]{"班课", "一对一"}).build(),
                SelectValidationData.builder().firstCol(4).lastCol(4).firstRow(1).lastRow(206).selectDataArray(new String[]{"开售", "不开售"}).build(),
                SelectValidationData.builder().firstCol(5).lastCol(5).firstRow(1).lastRow(206).selectDataArray(campusNameStringArray).build(),
                SelectValidationData.builder().firstCol(6).lastCol(6).firstRow(1).lastRow(206).selectDataArray(chargeTypeStringArray).build(),
                SelectValidationData.builder().firstCol(9).lastCol(9).firstRow(1).lastRow(206).selectDataArray(dateUnitListStringArray).build()
        );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public APIResponse importData(Long importId, MultipartFile multipartFile) throws IOException {
        long fileSize = multipartFile.getSize();
        if (fileSize > 1024 * 1024 * 2) {
            return APIResponse.toExceptionResponse("导入文件需小于2M");
        }

        LoginUser loginUser = LoginUserUtil.getLoginUser();


        // 课程类型
        QueryWrapper qw = new QueryWrapper();
        qw.eq("in_use", "1");
        qw.orderByDesc("create_time");
        List<ScCourseType> courseTypeList = scCourseTypeService.list(qw);
        // 全部校区
        List<RespTreeSelect> campusList = deptService.campusList();
        campusList.add(RespTreeSelect.builder().id("-1").label("全部校区").build());
        // 收费模式
        List<SysDictData> chargeTypeList = dictDataService.dictTypeDataList("charge_type");
        // 时间段
        List<SysDictData> dateUnitList = dictDataService.dictTypeDataList("date_unit");

        // 课程类型key value key为名称 value为ID
        Map<String, Long> courseTypeMap = Maps.newHashMap();
        for (ScCourseType scCourseType : courseTypeList) {
            courseTypeMap.put(scCourseType.getCourseType(), scCourseType.getCourseTypeId());
        }
        // 校区key value
        Map<String, Long> campusMap = Maps.newHashMap();
        for (RespTreeSelect respTreeSelect : campusList) {
            campusMap.put(respTreeSelect.getLabel(), Long.valueOf(respTreeSelect.getId()));
        }
        // 收费模式
        Map<String, String> chargeTypeMap = Maps.newHashMap();
        for (SysDictData sysDictData : chargeTypeList) {
            chargeTypeMap.put(sysDictData.getDictLabel(), sysDictData.getDictValue());
        }
        // 时间段
        Map<String, String> dateUnitMap = Maps.newHashMap();
        for (SysDictData sysDictData : dateUnitList) {
            dateUnitMap.put(sysDictData.getDictLabel(), sysDictData.getDictValue());
        }

        Map<String, String> saleMap = Maps.newHashMap();
        saleMap.put("开售", "1");
        saleMap.put("不开售", "2");

        Map<String, String> teachingModeMap = Maps.newHashMap();
        teachingModeMap.put("班课", "1");
        teachingModeMap.put("一对一", "2");


        ImportCourseListener importCourseListener = new ImportCourseListener(courseService, courseChargeService);
        importCourseListener.setCourseTypeMap(courseTypeMap);
        importCourseListener.setCampusMap(campusMap);
        importCourseListener.setChargeTypeMap(chargeTypeMap);
        importCourseListener.setDateUnitMap(dateUnitMap);
        importCourseListener.setSaleMap(saleMap);
        importCourseListener.setTeachingModeMap(teachingModeMap);
        importCourseListener.setLoginUserId(loginUser.getUserId());
        importCourseListener.setImportId(importId);
        EasyExcel.read(multipartFile.getInputStream(), ImportCourse.class, importCourseListener).sheet().autoTrim(true).headRowNumber(7).doRead();

        int successRecord = importCourseListener.getSuccessRecord();
        int failRecord = importCourseListener.getFailRecord();
        return APIResponse.toAPIResponse("导入成功" + successRecord + "条,导入失败:" + failRecord);
    }
}
