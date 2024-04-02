package com.study.base.business.sys.admin.service;

import cn.xluobo.business.sys.admin.domain.req.ReqSearchSysUser;
import cn.xluobo.business.sys.admin.repo.model.SysUser;
import cn.xluobo.core.page.RespPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
public interface ISysUserService extends IService<SysUser> {

    UserDetails loadUserDetailByUserName(String userName);

    List<SysUser> selectUserList(ReqSearchSysUser reqSearchSysUser, RespPage page);

    /**
     * 校验用户名是否唯一
     * @param username
     * @return
     */
    boolean checkUsernameUnique(String username);

    /**
     * 获取绑定用户
     * @param wechatUserId wechat_user_info.wechat_user_id
     * @return
     */
    SysUser getByBindByWechatUserId(Long wechatUserId);

    /**
     * 根据用户名 获取用户信息
     * @param username
     * @return
     */
    SysUser getByUserName(String username);

    /**
     * 校验用户是否拥有某角色
     * @param roleCode
     * @return
     */
    boolean checkSuperTenantUserHasRole(String userId, String roleCode);

    /**
     * 校验用户是否拥有 指定的任意一个角色
     * @param userId
     * @param tenantId
     * @param roleCodes
     * @return
     */
    boolean checkUserHasAnyRole(String userId, String tenantId, String[] roleCodes);

}
