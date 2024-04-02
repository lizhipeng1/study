package com.study.base.business.sc.student.service;

import cn.xluobo.business.sc.student.domain.req.ReqStudentSelect;
import cn.xluobo.business.sc.student.domain.resp.RespSearchStudent;
import cn.xluobo.business.sc.student.repo.model.ScStudent;
import cn.xluobo.core.page.ReqDeptCondition;
import cn.xluobo.core.page.RespPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 学生基本信息 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-04-27 07:13:40
 */
public interface IScStudentService extends com.baomidou.mybatisplus.extension.service.IService<ScStudent> {

    List<RespSearchStudent> selectStudentSelect(ReqStudentSelect studentSelect, Page page);

    Long selectIdByName(String studentName);

    /**
     * 学生列表
     * @param max 最大数量
     * @return 学生姓名、学生ID
     */
    RespPage<ScStudent> selectStudentList(Integer max);

}
