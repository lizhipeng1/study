package com.study.business.sc.course.service.impl;

import com.study.business.sc.course.domain.resp.RespBusinessChooseCourseCharge;
import com.study.business.sc.course.enums.CourseChargeTypeEnum;
import com.study.business.sc.course.repo.mapper.ScCourseChargeMapper;
import com.study.business.sc.course.repo.model.ScCourseCharge;
import com.study.business.sc.course.service.IScCourseChargeService;
import com.study.business.sys.admin.repo.model.SysDictData;
import com.study.business.sys.admin.service.ISysDictDataService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程收费模式 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-07-08
 */
@Service
public class ScCourseChargeServiceImpl extends ServiceImpl<ScCourseChargeMapper, ScCourseCharge> implements IScCourseChargeService {

    @Autowired
    private ISysDictDataService sysDictDataService;

    @Override
    public List<RespBusinessChooseCourseCharge> transferCourseChargeList(List<ScCourseCharge> courseChargeList) {
        List<SysDictData> dateUnitList = sysDictDataService.dictTypeDataList("date_unit");
        Map<String, String> dictUnitMap = dateUnitList.stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictLabel));

        List<SysDictData> chargeTypeList = sysDictDataService.dictTypeDataList("charge_type");
        Map<String, String> chargeTypeMap = chargeTypeList.stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictLabel));

        return courseChargeList.stream().map(item -> {
            String label = "";
            String chargeType = item.getChargeType();
            String chargeTypeName = chargeTypeMap.get(chargeType);
            if (CourseChargeTypeEnum.DATE.getChargeType().equals(chargeType)) {
                // 按时间 100元/月
                label = "(" + chargeTypeName + ")" + item.getTotalFee().toString() + dictUnitMap.get(item.getDateUnit());
            } else if (CourseChargeTypeEnum.HOUR.getChargeType().equals(chargeType)) {
                // 按课时 10课时:100元
                label = "(" + chargeTypeName + ")" + item.getCount().toString() + "课时 " + item.getTotalFee().toString() + "元";
            } else if (CourseChargeTypeEnum.CYCLE.getChargeType().equals(chargeType)) {
                // 按期 100元/月
                label = "(" + chargeTypeName + ")" + item.getCount().toString() + "课时/期 " + item.getTotalFee().toString() + "元";
            }
            return RespBusinessChooseCourseCharge.builder()
                    .chargeId(item.getChargeId())
                    .totalFee(item.getTotalFee())
                    .label(label)
                    .chargeType(item.getChargeType())
                    .count(item.getCount())
                    .dateUnit(item.getDateUnit())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<RespBusinessChooseCourseCharge> courseChargeList(Long courseId, String chargeType) {
        QueryWrapper<ScCourseCharge> qw = new QueryWrapper<>();
        qw.eq("course_id", courseId);
        if (StringUtils.isNotEmpty(chargeType)) {
            qw.eq("charge_type", chargeType);
        }
        List<ScCourseCharge> courseChargeList = this.list(qw);
        return transferCourseChargeList(courseChargeList);
    }
}
