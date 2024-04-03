package com.study.business.sc.student.service;

import com.study.business.sc.student.repo.model.ScStudentContact;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 联系人 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-27
 */
public interface IScStudentContactService extends IService<ScStudentContact> {

    /**
     * 获取学生练习方式
     * @param studentId
     * @return
     */
    List<ScStudentContact> getStudentContactList(Long studentId);
}
