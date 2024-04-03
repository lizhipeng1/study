package com.study.business.sc.course.domain.req;

import com.study.business.sc.course.repo.model.ScCourseCla;
import com.study.config.login.LoginUser;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 新增课程
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020-03-23 13:53
 */
@Data
public class ReqAddScCourseCla {

    private Long courseId;

    private Long departId;

    private Long staffId;

    private String claName;

    private String claColor;

    private Integer capacity;

    private String recruitStatus;

    private BigDecimal everyStuLoseHour;

    private String openDate;

    private String closeDate;

    private String memo;

    /**
     * 获取班级实体
     *
     * @param loginUser
     * @return
     */
    public ScCourseCla getScCourseCla(LoginUser loginUser) {
        ScCourseCla cla = new ScCourseCla();
        cla.setCourseId(courseId);
        cla.setDepartId(departId);
        cla.setStaffId(staffId);
        cla.setClaName(claName);
        cla.setClaColor(claColor);
        cla.setCapacity(capacity);
        cla.setRecruitStatus(recruitStatus);
        cla.setEveryStuLoseHour(everyStuLoseHour);
        cla.setEveryTeaGetHour(everyStuLoseHour);
        cla.setOpenDate(openDate);
        cla.setCloseDate(closeDate);
        cla.setMemo(memo);
        cla.setCreateUser(loginUser.getUserId());
        return cla;
    }
}
