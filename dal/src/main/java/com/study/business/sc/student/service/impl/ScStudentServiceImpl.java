package com.study.business.sc.student.service.impl;

import com.study.business.sc.student.domain.req.ReqStudentSelect;
import com.study.business.sc.student.domain.resp.RespSearchStudent;
import com.study.business.sc.student.repo.model.ScStudent;
import com.study.business.sc.student.repo.mapper.ScStudentMapper;
import com.study.business.sc.student.service.IScStudentService;
import com.study.core.page.RespPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学生基本信息 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-04-27 07:13:40
 */
@Service
public class ScStudentServiceImpl extends ServiceImpl<ScStudentMapper, ScStudent> implements IScStudentService {

    @Override
    public List<RespSearchStudent> selectStudentSelect(ReqStudentSelect studentSelect, Page page) {
        return baseMapper.selectForSelect(studentSelect, page);
    }

    @Override
    public Long selectIdByName(String studentName) {
        QueryWrapper<ScStudent> qw = new QueryWrapper<>();
        qw.select("student_id");
        qw.eq("student_name", studentName);
        List<ScStudent> studentList = this.list(qw);
        if (null != studentList && studentList.size() > 0) {
            return studentList.get(0).getStudentId();
        }
        return null;
    }

    @Override
    public RespPage<ScStudent> selectStudentList(Integer max) {
        QueryWrapper<ScStudent> qw = new QueryWrapper<>();
        qw.select("student_id","student_name");
        qw.orderByDesc("create_time");
        RespPage<ScStudent> respPage = new RespPage<ScStudent>(1,max);
        return this.page(respPage,qw);
    }
}
