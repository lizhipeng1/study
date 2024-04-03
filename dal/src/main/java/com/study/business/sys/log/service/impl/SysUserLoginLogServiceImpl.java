package com.study.business.sys.log.service.impl;

import com.study.business.sys.log.repo.model.SysUserLoginLog;
import com.study.business.sys.log.repo.mapper.SysUserLoginLogMapper;
import com.study.business.sys.log.service.ISysUserLoginLogService;
import com.study.config.login.LoginUser;
import com.study.core.utils.ip.IpUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 登录日志 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-11-13
 */
@Service
@Slf4j
public class SysUserLoginLogServiceImpl extends ServiceImpl<SysUserLoginLogMapper, SysUserLoginLog> implements ISysUserLoginLogService {

    @Override
    public void saveLoginLog(HttpServletRequest request, Authentication authentication) {
        try {
            String ipAddress = IpUtils.getIpAddr(request);
            //获取浏览器信息
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            OperatingSystem operatingSystem = userAgent.getOperatingSystem();
            String operatingSystemName = operatingSystem.getName();
            Browser browser = userAgent.getBrowser();

            StringBuffer info = new StringBuffer(operatingSystemName);

            if (null == browser) {
                log.error("userAgent.getBrowser() is null");
            } else {
                info.append(",").append(browser.getName());
                //获取浏览器版本号
                Version version = browser.getVersion(request.getHeader("User-Agent"));
                if (null == version) {
                    log.error("browser.getVersion(request.getHeader(\"User-Agent\")) is null");
                } else {
                    info.append("/").append(version.getVersion());
                }
            }

            log.info("login success ipAddress={},infoStr={}", ipAddress, info.toString());

            Object principal = authentication.getPrincipal();
            if(principal instanceof LoginUser) {
                LoginUser loginUser = (LoginUser)principal;
                SysUserLoginLog loginLog = new SysUserLoginLog();
                loginLog.setUserId(loginUser.getUserId());
                loginLog.setUsername(loginUser.getUsername());
                loginLog.setName(loginUser.getName());
                loginLog.setLoginIp(ipAddress);
                loginLog.setBrowserInfo(info.toString());
                this.save(loginLog);
            }
        } catch (Exception e) {
            log.error("saveLoginLog error",e);
        }
    }
}
