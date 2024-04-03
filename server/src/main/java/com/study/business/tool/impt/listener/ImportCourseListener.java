package com.study.business.tool.impt.listener;

import com.study.business.sc.course.enums.CourseChargeTypeEnum;
import com.study.business.sc.course.repo.model.ScCourse;
import com.study.business.sc.course.repo.model.ScCourseCharge;
import com.study.business.sc.course.service.IScCourseChargeService;
import com.study.business.sc.course.service.IScCourseService;
import com.study.business.tool.impt.domain.ImportCourse;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/3 22:02
 */
@Slf4j
@Data
public class ImportCourseListener extends AnalysisEventListener<ImportCourse> {

    private IScCourseService courseService;

    private IScCourseChargeService courseChargeService;

    // 课程类型key value
    private Map<String, Long> courseTypeMap;
    // 校区key value
    private Map<String, Long> campusMap;
    // 收费模式
    private Map<String, String> chargeTypeMap;
    // 时间段
    private Map<String, String> dateUnitMap;
    // 是否开售
    private Map<String, String> saleMap;
    // 教学木事
    private Map<String, String> teachingModeMap;

    // 登录用户id
    private String loginUserId = "";

    // 导入id
    private Long importId;

    // 需保存的saveCourseList
    private List<ImportCourse> saveCourseList = Lists.newArrayList();

    /**
     * 最多读取多少行
     */
    int maxRecord = 200;

    /**
     * 当前已读取
     */
    int hadReadRecord = 0;

    private int successRecord = 0;
    private int failRecord = 0;

    public ImportCourseListener(IScCourseService courseService, IScCourseChargeService courseChargeService) {
        this.courseService = courseService;
        this.courseChargeService = courseChargeService;
    }

    @Override
    public void invoke(ImportCourse data, AnalysisContext context) {
        boolean checkParam = checkParam(data);
        if (!checkParam) {
            failRecord++;
            return;
        }
        successRecord++;

        saveCourseList.add(data);
        log.info(JSON.toJSONString(data));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveCourse();
        log.info("doAfterAllAnalysed");
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        if (hadReadRecord++ < maxRecord) {
            return true;
        }
        return false;
    }

    /**
     * 保存课程信息
     */
    public void saveCourse() {
        // 缓存以保存的课程
        Map<String, Long> hadSaveCache = Maps.newHashMap();

        for (ImportCourse importCourse : saveCourseList) {
            if (!hadSaveCache.containsKey(importCourse.getCourseName())) {
                // 保存课程信息
                ScCourse course = new ScCourse();
                course.setCourseName(importCourse.getCourseName());
                Long courseTypeId = courseTypeMap.get(importCourse.getCourseTypeName());
                course.setCourseTypeId(courseTypeId);
                String teachingMode = teachingModeMap.get(importCourse.getTeachingMode());
                course.setTeachingMode(teachingMode);
                course.setCourseIntro(importCourse.getCourseIntro());
                String sale = saleMap.get(importCourse.getSale());
                course.setSale(sale);
                course.setCreateUser(loginUserId);
                course.setImportId(importId);
                courseService.save(course);
                hadSaveCache.put(importCourse.getCourseName(), course.getCourseId());
            }
            // 获取以保存课程ID
            Long courseId = hadSaveCache.get(importCourse.getCourseName());
            
            // 保存收费
            String chargeTypeName = importCourse.getChargeType();
            String chargeType = chargeTypeMap.get(chargeTypeName);

            if (chargeType.equals(CourseChargeTypeEnum.HOUR.getChargeType())) {
                // 按课时收费
                ScCourseCharge courseCharge = new ScCourseCharge();
                courseCharge.setCourseId(courseId);
                Long departId = campusMap.get(importCourse.getDepartName());
                courseCharge.setDepartId(departId);
                courseCharge.setChargeType("hour");
                courseCharge.setCount(importCourse.getCount());
                courseCharge.setTotalFee(importCourse.getTotalFee());
                courseChargeService.save(courseCharge);
            } else if (chargeType.equals(CourseChargeTypeEnum.DATE.getChargeType())) {
                // 按时间收费
                ScCourseCharge courseCharge = new ScCourseCharge();
                courseCharge.setCourseId(courseId);
                Long departId = campusMap.get(importCourse.getDepartName());
                courseCharge.setDepartId(departId);
                courseCharge.setChargeType("date");
                courseCharge.setCount(new BigDecimal(1));
                courseCharge.setTotalFee(importCourse.getTotalFee());
                String dateUnit = dateUnitMap.get(importCourse.getDateUnit());
                courseCharge.setDateUnit(dateUnit);
                courseChargeService.save(courseCharge);
            } else if (chargeType.equals(CourseChargeTypeEnum.CYCLE.getChargeType())) {
                // 按周期收费
                ScCourseCharge courseCharge = new ScCourseCharge();
                courseCharge.setCourseId(courseId);
                Long departId = campusMap.get(importCourse.getDepartName());
                courseCharge.setDepartId(departId);
                courseCharge.setChargeType("cycle");
                courseCharge.setCount(importCourse.getCount());
                courseCharge.setTotalFee(importCourse.getTotalFee());
                courseChargeService.save(courseCharge);
            }
        }
    }

    /**
     * 校验导入参数
     *
     * @param data
     * @return
     */
    public boolean checkParam(ImportCourse data) {
        if (StringUtils.isAnyEmpty(data.getCourseName(), data.getTeachingMode(), data.getSale())) {
            return false;
        } else if (StringUtils.isAnyEmpty(data.getDepartName(), data.getChargeType())) {
            return false;
        } else if (null == data.getTotalFee()) {
            return false;
        }

        String teachingMode = teachingModeMap.get(data.getTeachingMode());
        String sale = saleMap.get(data.getSale());
        Long departId = campusMap.get(data.getDepartName());
        String dateUnit = dateUnitMap.get(data.getDateUnit());
        String chargeType = chargeTypeMap.get(data.getChargeType());
        if (null == departId) {
            return false;
        }
        if(StringUtils.isNotEmpty(data.getCourseTypeName())) {
            Long courseTypeId = courseTypeMap.get(data.getCourseTypeName());
            if(null == courseTypeId) {
                return false;
            }
        }
        if (StringUtils.isAnyEmpty(teachingMode, sale, chargeType)) {
            return false;
        }
        if (StringUtils.isEmpty(dateUnit) && CourseChargeTypeEnum.DATE.getChargeType().equals(chargeType)) {
            return false;
        }
        if (null == data.getCount() && !CourseChargeTypeEnum.DATE.getChargeType().equals(chargeType)) {
            return false;
        }
        return true;
    }
}
