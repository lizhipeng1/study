package com.study.business.sys.log.service;

import com.study.business.sys.log.repo.model.SysUserLoginLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 登录日志 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-11-13
 */
public interface ISysUserLoginLogService extends IService<SysUserLoginLog> {

    /**
     * 保存登录日志
     * @param request
     * @param authentication
     */
    void saveLoginLog(HttpServletRequest request, Authentication authentication);

}
