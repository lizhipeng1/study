package com.study.core.business.sc.log.service;

import cn.xluobo.business.sc.log.domain.req.ReqSearchStuCourseLog;
import cn.xluobo.business.sc.log.repo.mapper.ScStudentCourseLogMapper;
import cn.xluobo.business.sc.log.repo.model.ScStudentCourseLog;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.page.RespPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/12/21 14:46
 */
@Service
@Transactional
@Slf4j
public class BusinessScStudentCourseLogService {

    @Autowired
    private ScStudentCourseLogMapper studentCourseLogMapper;

    /**
     * 查询
     *
     * @param reqSearchStuCourseLog
     * @return
     */
    public APIResponse searchList(ReqSearchStuCourseLog reqSearchStuCourseLog) {
        RespPage<ScStudentCourseLog> page = new RespPage<>(reqSearchStuCourseLog.getPageNum(), reqSearchStuCourseLog.getPageSize());
        List<ScStudentCourseLog> orderList = studentCourseLogMapper.selectForSearchTable(reqSearchStuCourseLog, page);
        page.setRows(orderList);
        return APIResponse.toAPIResponse(page);
    }
}
