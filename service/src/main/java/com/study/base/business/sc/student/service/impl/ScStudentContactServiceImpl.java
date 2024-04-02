package com.study.base.business.sc.student.service.impl;

import cn.xluobo.business.sc.student.repo.mapper.ScStudentContactMapper;
import cn.xluobo.business.sc.student.repo.model.ScStudentContact;
import cn.xluobo.business.sc.student.service.IScStudentContactService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 联系人 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-27
 */
@Service
public class ScStudentContactServiceImpl extends ServiceImpl<ScStudentContactMapper, ScStudentContact> implements IScStudentContactService {

    @Override
    public List<ScStudentContact> getStudentContactList(Long studentId) {
        QueryWrapper<ScStudentContact> qw = new QueryWrapper<>();
        qw.eq("student_id", studentId);
        qw.orderByAsc("create_time");
        List<ScStudentContact> contacts = this.list(qw);
        return contacts;
    }
}
