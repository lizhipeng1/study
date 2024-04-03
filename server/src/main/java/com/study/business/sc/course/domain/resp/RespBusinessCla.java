package com.study.business.sc.course.domain.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 班级
 * </p>
 *
 * @author zhangby
 * @since 2020-06-21 07:13:40
 */
@Data
public class RespBusinessCla implements Serializable {

    private Long claId;

    private String claName;

    private String chargeName;

    public RespBusinessCla(Long claId, String claName, String chargeName) {
        this.claId = claId;
        this.claName = claName;
        this.chargeName = chargeName;
    }

    public RespBusinessCla() {
    }
}
