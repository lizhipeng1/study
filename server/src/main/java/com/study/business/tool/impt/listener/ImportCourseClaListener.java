package com.study.business.tool.impt.listener;

import com.study.business.sc.course.repo.enums.ClaTimeRepeatTypeEnums;
import com.study.business.sc.course.repo.enums.ClaTimeRuleTypeEnums;
import com.study.business.sc.course.repo.model.ScClaTimeRule;
import com.study.business.sc.course.repo.model.ScCourseCla;
import com.study.business.sc.course.service.BusinessClaTimeRuleService;
import com.study.business.sc.course.service.IScCourseClaService;
import com.study.business.tool.impt.domain.ImportCourseCla;
import com.study.core.api.APIBaseResponse;
import com.study.core.utils.DateUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
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
public class ImportCourseClaListener extends AnalysisEventListener<ImportCourseCla> {

    private IScCourseClaService courseClaService;

    private BusinessClaTimeRuleService businessClaTimeRuleService;

    /**
     * 是否需要保存
     */
    private Boolean needSave;

    private Map<String, Long> courseMap;
    private Map<String, String> campusMap;
    private Map<String, Long> teacherMap;
    private Map<String, String> recruitStatusMap;
    private Map<String, String> claTimeRepeatTypeMap;
    private Map<String, Long> roomMap;
    private Map<String, String> weekDayMap;

    // 登录用户id
    private String loginUserId = "";

    // 导入id
    private Long importId;

    // 需保存的saveCourseClaList
    private List<ImportCourseCla> saveCourseClaList = Lists.newArrayList();

    // 校验失败的列表
    private List<ImportCourseCla> failCourseClaList = Lists.newArrayList();

    /**
     * 最多读取多少行
     */
    int maxRecord = 100;

    /**
     * 当前已读取
     */
    int hadReadRecord = 0;

    public ImportCourseClaListener(IScCourseClaService courseClaService, BusinessClaTimeRuleService businessClaTimeRuleService) {
        this.courseClaService = courseClaService;
        this.businessClaTimeRuleService = businessClaTimeRuleService;
    }

    @Override
    public void invoke(ImportCourseCla data, AnalysisContext context) {
        APIBaseResponse checkParam = checkParam(data);
        if (!checkParam.isSuccess()) {
            data.setFailMsg(checkParam.getRespMsg());
            failCourseClaList.add(data);
            return;
        }
        saveCourseClaList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (needSave) {
            saveCourseCla();
        }
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
     * 保存班级信息
     */
    public void saveCourseCla() {
        for (ImportCourseCla courseCla : saveCourseClaList) {

            Long courseId = courseMap.get(courseCla.getCourseName());
            String deptId = campusMap.get(courseCla.getDeptName());
            Long teacherId = teacherMap.get(courseCla.getTeacherName());
            String recruitStatus = recruitStatusMap.get(courseCla.getRecruitStatus());

            // 保存课程信息
            ScCourseCla cla = new ScCourseCla();
            cla.setCourseId(courseId);
            cla.setDepartId(Long.valueOf(deptId));
            cla.setStaffId(teacherId);
            cla.setClaName(courseCla.getClaName());
            cla.setCapacity(Integer.parseInt(courseCla.getCapacity()));
            cla.setRecruitStatus(recruitStatus);
            cla.setEveryStuLoseHour(new BigDecimal(courseCla.getEveryStuLoseHour()));
            cla.setOpenDate(courseCla.getOpenDate());
            cla.setCloseDate(courseCla.getCloseDate());
            cla.setMemo(courseCla.getMemo());
            cla.setCreateUser(loginUserId);
            courseClaService.save(cla);

            // 规则排课
            if (StringUtils.isNoneEmpty(courseCla.getBeginDate(), courseCla.getEndDate())) {
                Long roomId = null;
                if (StringUtils.isNotEmpty(courseCla.getRoomName())) {
                    roomId = teacherMap.get(courseCla.getRoomName());
                }
                String repeatType = claTimeRepeatTypeMap.get(courseCla.getRepeatType());
                // 保存规则排课信息 并生成排课信息
                ScClaTimeRule claTimeRule = new ScClaTimeRule();
                claTimeRule.setClaId(cla.getClaId());
                claTimeRule.setRuleType(ClaTimeRuleTypeEnums.REPEAT_RULE.getRuleType());
                claTimeRule.setBeginDate(courseCla.getBeginDate());
                claTimeRule.setEndDate(courseCla.getEndDate());
                claTimeRule.setRepeatType(repeatType);

                if (ClaTimeRepeatTypeEnums.EVERY_WEEK.getRepeatType().equals(repeatType)
                        || ClaTimeRepeatTypeEnums.EVERY_SECOND_WEEK.getRepeatType().equals(repeatType)) {
                    List<String> weekDayKeyList = Lists.newArrayList();
                    String weekDay = courseCla.getWeekDay();
                    String[] weekDayArray = weekDay.split(",");
                    for (String item : weekDayArray) {
                        String weekDayKey = weekDayMap.get(item);
                        weekDayKeyList.add(weekDayKey);
                    }
                    claTimeRule.setWeekDay(String.join(",", weekDayKeyList));
                }

                claTimeRule.setFilterHoliday("过滤".equals(courseCla.getFilterHoliday()));
                claTimeRule.setStartTime(courseCla.getStartTime());
                claTimeRule.setEndTime(courseCla.getEndTime());
                claTimeRule.setTeacherId(teacherId);
                claTimeRule.setRoomId(roomId);
                businessClaTimeRuleService.addClaTimeRule(claTimeRule);

            }

        }
    }

    /**
     * 校验导入参数
     *
     * @param data
     * @return
     */
    public APIBaseResponse checkParam(ImportCourseCla data) {
        if (StringUtils.isAnyEmpty(data.getClaName(), data.getCourseName(),
                data.getDeptName(), data.getTeacherName(), data.getOpenDate())) {
            return APIBaseResponse.fail("请填写班级名称、课程名称、校区、任课教师、开班日期");
        } else if (StringUtils.isEmpty(data.getEveryStuLoseHour())) {
            return APIBaseResponse.fail("请填写每次上课学员扣除课时数");
        } else if (data.getClaName().length() > 30) {
            return APIBaseResponse.fail("班级名称过长,最长30!");
        } else if (StringUtils.isNotEmpty(data.getMemo()) && data.getMemo().length() > 200) {
            return APIBaseResponse.fail("备注过长,最长200!");
        }
        try {
            new BigDecimal(data.getEveryStuLoseHour());
        }catch (NumberFormatException numberFormatException) {
            return APIBaseResponse.fail("每次上课学员扣除课时数 需填写两位小数");
        }
        if(StringUtils.isNotEmpty(data.getCapacity())){
            try {
                Integer.parseInt(data.getCapacity());
            }catch (NumberFormatException numberFormatException) {
                return APIBaseResponse.fail("满班人数需为正整数");
            }
        }
        Long courseId = courseMap.get(data.getCourseName());
        String deptId = campusMap.get(data.getDeptName());
        Long teacherId = teacherMap.get(data.getTeacherName());
        String recruitStatus = recruitStatusMap.get(data.getRecruitStatus());

        if (null == courseId || null == teacherId) {
            return APIBaseResponse.fail("根据课程名称、任课教师名称无法获取对应信息,请核对填写信息或重新下载导入模板进行导入");
        } else if (StringUtils.isEmpty(deptId)) {
            return APIBaseResponse.fail("根据校区名称无法获取对应信息,请核对填写信息或重新下载导入模板进行导入");
        } else if (StringUtils.isEmpty(recruitStatus)) {
            return APIBaseResponse.fail("根据招生状态无法获取对应信息,请核对填写信息或重新下载导入模板进行导入");
        }

        if (StringUtils.isNoneEmpty(data.getBeginDate(), data.getEndDate())) {

            try {
                DateUtil.yyyMMddDayBegin(data.getBeginDate());
                DateUtil.yyyMMddDayBegin(data.getEndDate());
            }catch (Exception e) {
                return APIBaseResponse.fail("请按照正确格式填写排课开始日期、排课结束日期");
            }

            if (StringUtils.isAnyEmpty(data.getRepeatType(), data.getStartTime(), data.getEndTime())) {
                return APIBaseResponse.fail("导入排课时,请填写重复方式、上课时间、下课时间。如无需导入排课信息，请删除填写的排课开始日期、排课结束日期");
            } else if (!"过滤".equals(data.getFilterHoliday()) && !"不过滤".equals(data.getFilterHoliday())) {
                return APIBaseResponse.fail("导入排课时,是否过滤节假日只可填写过滤/不过滤");
            }
            String repeatType = claTimeRepeatTypeMap.get(data.getRepeatType());
            if (StringUtils.isEmpty(repeatType)) {
                return APIBaseResponse.fail("根据重复方式无法获取对应信息,请核对填写信息或重新下载导入模板进行导入");
            } else if (!ClaTimeRepeatTypeEnums.EVERY_SECOND_DAY.getRepeatType().equals(data.getRepeatType())) {
                if (StringUtils.isEmpty(data.getWeekDay())) {
                    return APIBaseResponse.fail("导入排课时,每周重复、隔周重复时需填写上课星期");
                }
                String weekDay = data.getWeekDay();
                String[] weekDayArray = weekDay.split(",");
                for (String item : weekDayArray) {
                    if (StringUtils.isEmpty(weekDayMap.get(item))) {
                        return APIBaseResponse.fail("请重新填写上课星期'" + item + "',使用英文,号分隔");
                    }
                }
            }
            if (StringUtils.isNotEmpty(data.getRoomName())) {
                Long roomId = roomMap.get(data.getRoomName());
                if (null == roomId) {
                    return APIBaseResponse.fail("根据教室名称无法获取对应信息,请核对填写信息或重新下载导入模板进行导入");
                }
            }
        }


        return APIBaseResponse.success();
    }

    public int getSuccessRecord() {
        return this.saveCourseClaList.size();
    }

    public int getFailRecord() {
        return this.failCourseClaList.size();
    }
}
