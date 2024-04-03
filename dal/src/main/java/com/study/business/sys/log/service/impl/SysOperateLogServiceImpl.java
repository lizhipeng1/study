package com.study.business.sys.log.service.impl;

import com.study.business.sys.log.repo.model.SysOperateLog;
import com.study.business.sys.log.repo.mapper.SysOperateLogMapper;
import com.study.business.sys.log.service.ISysOperateLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2021-04-20
 */
@Service
public class SysOperateLogServiceImpl extends ServiceImpl<SysOperateLogMapper, SysOperateLog> implements ISysOperateLogService {

    @Override
    @Async
    public void asyncSaveLog(SysOperateLog sysOperateLog) {
        this.save(sysOperateLog);
    }
}
