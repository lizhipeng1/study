package com.study.base.business.sys.staff.service;

import cn.xluobo.business.sys.staff.domain.req.ReqSearchStaff;
import cn.xluobo.business.sys.staff.domain.resp.RespStaffInfo;
import cn.xluobo.business.sys.staff.repo.model.SysStaff;
import cn.xluobo.core.page.RespPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 教师信息 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-03-17 11:27:37
 */
public interface ISysStaffService extends IService<SysStaff> {

    List<RespStaffInfo> searchStaffList(ReqSearchStaff reqSearchStaff, RespPage page);

    /**
     * 教师列表
     * @param teacherName
     * @return
     */
    List<SysStaff> teacherList(String teacherName);

    /**
     * 员工列表
     * @param staffName
     * @return
     */
    List<SysStaff> staffList(String staffName);

    /**
     * 获取关联用户id
     * @param staffIds
     * @return
     */
    List<String> getUserIds(Long[] staffIds);

    // 根据用户获取
    Long getStaffIdByUserId(String userId);
}
