package com.study.base.business.sys.staff.service.impl;

import cn.xluobo.business.sys.staff.domain.req.ReqSearchStaff;
import cn.xluobo.business.sys.staff.domain.resp.RespStaffInfo;
import cn.xluobo.business.sys.staff.repo.mapper.SysStaffMapper;
import cn.xluobo.business.sys.staff.service.ISysStaffService;
import cn.xluobo.business.sys.staff.repo.model.SysStaff;
import cn.xluobo.core.page.RespPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 教师信息 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-03-17 11:27:37
 */
@Service
public class SysStaffServiceImpl extends ServiceImpl<SysStaffMapper, SysStaff> implements ISysStaffService {

    @Override
    public List<RespStaffInfo> searchStaffList(ReqSearchStaff reqSearchStaff, RespPage page) {
        return baseMapper.selectStaffList(reqSearchStaff, page);
    }

    @Override
    public List<SysStaff> teacherList(String teacherName) {
        QueryWrapper<SysStaff> qw = new QueryWrapper<>();
        qw.select("staff_name", "staff_id");
        qw.eq("teacher", "1");
        if (StringUtils.isNotEmpty(teacherName)) {
            qw.like("staff_name", teacherName);
        }
        qw.orderByAsc("staff_name");
        return this.list(qw);
    }

    @Override
    public List<SysStaff> staffList(String staffName) {
        QueryWrapper<SysStaff> qw = new QueryWrapper<>();
        qw.select("staff_name", "staff_id");
        if (StringUtils.isNotEmpty(staffName)) {
            qw.like("staff_name", staffName);
        }
        qw.orderByAsc("staff_name");
        return this.list(qw);
    }

    @Override
    public List<String> getUserIds(Long[] staffIds) {
        QueryWrapper<SysStaff> qw = new QueryWrapper<>();
        qw.select("user_id");
        qw.in("staff_id", staffIds);
        qw.isNotNull("user_id");
        List<SysStaff> staffList = this.list(qw);
        List<String> userIds = staffList.stream().map(SysStaff::getUserId).collect(Collectors.toList());
        return userIds;
    }

    @Override
    public Long getStaffIdByUserId(String userId) {
        QueryWrapper<SysStaff> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        SysStaff sysStaff = this.getOne(qw);
        if (null != sysStaff) {
            return sysStaff.getStaffId();
        }
        return null;
    }
}
