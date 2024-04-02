package com.study.base.business.sys.log.service;

import cn.xluobo.business.sys.log.repo.model.SysOperateLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author zhangby
 * @since 2021-04-20
 */
public interface ISysOperateLogService extends IService<SysOperateLog> {

    /**
     * 异步保存日志
     * @param sysOperateLog
     */
    void asyncSaveLog(SysOperateLog sysOperateLog);

}
