package com.study.business.sc.log.service;

import com.study.business.sc.log.domain.req.ReqSearchStuCourseLog;
import com.study.business.sc.log.repo.mapper.ScStudentCourseLogMapper;
import com.study.business.sc.log.repo.model.ScStudentCourseLog;
import com.study.core.api.APIResponse;
import com.study.core.page.RespPage;
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
