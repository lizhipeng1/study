package com.study.base.business.sc.student.domain.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 学生基本信息
 * </p>
 *
 * @author zhangby
 * @since 2020-04-27 07:13:40
 */
@Data
public class RespSearchStudent implements Serializable {



    private Long studentId;

    private Long schoolId;

    private String schoolName;

    private String studentName;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDay;

    private Integer age;

    private String sex;

    private String phone;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date inTime;

    // 联系人信息
    private String contactInfo;
}
