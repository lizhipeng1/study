package com.study.core.business.sys.admin.service;

import cn.xluobo.business.sc.course.repo.mapper.ScCourseClaMapper;
import cn.xluobo.business.sc.course.repo.mapper.ScCourseMapper;
import cn.xluobo.business.sc.student.repo.mapper.ScStudentMapper;
import cn.xluobo.business.sys.admin.domain.req.ReqBusinessAddTenant;
import cn.xluobo.business.sys.admin.domain.req.ReqSearchSysTenant;
import cn.xluobo.business.sys.admin.enums.RoleEnum;
import cn.xluobo.business.sys.admin.repo.model.*;
import cn.xluobo.business.sys.staff.repo.mapper.SysStaffMapper;
import cn.xluobo.business.sys.staff.repo.model.SysStaff;
import cn.xluobo.business.sys.staff.service.ISysStaffService;
import cn.xluobo.config.exception.BusinessException;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.config.tenant.TenantContextHolder;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.api.ApiResEnums;
import cn.xluobo.core.page.RespPage;
import cn.xluobo.core.utils.DateUtil;
import cn.xluobo.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessSysTenantService {

    @Autowired
    private ISysTenantService sysTenantService;
    @Autowired
    private ISysStaffService staffService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysStaffMapper staffMapper;
    @Autowired
    private ScCourseMapper courseMapper;
    @Autowired
    private ScCourseClaMapper claMapper;
    @Autowired
    private ScStudentMapper studentMapper;
    @Autowired
    private BusinessSysUserService businessSysUserService;
    @Autowired
    private ISysUserDeptService deptService;
    @Autowired
    private ISysUserTenantService userTenantService;
    @Autowired
    private ISysUserRoleService userRoleService;
    @Autowired
    private ISysRoleService roleService;

    /**
     * 查询树
     *
     * @param reqSearchSysTenant
     * @return
     */
    public APIResponse searchList(ReqSearchSysTenant reqSearchSysTenant) {
        QueryWrapper qw = new QueryWrapper();
        if (StringUtils.isNotEmpty(reqSearchSysTenant.getTenantName())) {
            qw.like("tenant_name", reqSearchSysTenant.getTenantName());
        }
        if (StringUtils.isNotEmpty(reqSearchSysTenant.getContactName())) {
            qw.like("contact_name", reqSearchSysTenant.getContactName());
        }
        if (StringUtils.isNotEmpty(reqSearchSysTenant.getInUse())) {
            qw.eq("in_use", reqSearchSysTenant.getInUse());
        }
        if (StringUtils.isNoneEmpty(reqSearchSysTenant.getBeginTime(), reqSearchSysTenant.getEndTime())) {
            qw.between("end_time", reqSearchSysTenant.getBeginTime() + " 00:00:00", reqSearchSysTenant.getEndTime() + " 23:59:59");
        }
        // 不展示超级管理租户
        qw.ne("super_tenant", "1");

        qw.orderByDesc("in_use", "create_time");
        RespPage<SysTenant> page = new RespPage(reqSearchSysTenant.getPageNum(), reqSearchSysTenant.getPageSize());
        RespPage<SysTenant> listPage = sysTenantService.page(page, qw);
        return APIResponse.toAPIResponse(listPage);
    }

    /**
     * 前端select
     *
     * @return
     */
    public APIResponse treeSelect() {
        QueryWrapper qw = new QueryWrapper();
        qw.select("tenant_id", "tenant_name");
        qw.ne("super_tenant", 1);
        qw.orderByDesc("create_time");
        List<SysTenant> list = sysTenantService.list(qw);
        return APIResponse.toAPIResponse(list);
    }

    /**
     * 详情
     *
     * @param tenantId
     * @return
     */
    public APIResponse detailById(Long tenantId) {
        if (null == tenantId) {
            return APIResponse.toAPIResponse(null);
        }
        SysTenant detailInfo = sysTenantService.getById(tenantId);
        List<String> useDateList = Arrays.asList(DateUtil.format(detailInfo.getBeginTime(), DateUtil.DATE_PATTERN.YYYY_MM_DD), DateUtil.format(detailInfo.getEndTime(), DateUtil.DATE_PATTERN.YYYY_MM_DD));
        String[] useDateRange = useDateList.toArray(new String[useDateList.size()]);
        detailInfo.setUseDateRange(useDateRange);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param reqBusinessAddTenant
     * @return
     */
    public APIResponse addTenant(ReqBusinessAddTenant reqBusinessAddTenant) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();

        SysTenant sysTenant = new SysTenant();
        BeanUtils.copyProperties(reqBusinessAddTenant, sysTenant);

        sysTenant.setCreateUser(loginUser.getUserId());
        sysTenant.setBeginTime(new DateTime(sysTenant.getBeginTime()).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate());
        sysTenant.setEndTime(new DateTime(sysTenant.getEndTime()).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate());
        sysTenantService.save(sysTenant);

        try {
            TenantContextHolder.setTenant(sysTenant.getTenantId());
            // 保存用户
            if (StringUtils.isAnyEmpty(reqBusinessAddTenant.getUsername(), reqBusinessAddTenant.getPassword())) {
                return APIResponse.toExceptionResponse(("请求参数错误,请完善后重试"));
            } else if (!reqBusinessAddTenant.getPassword().equals(reqBusinessAddTenant.getCheckPass())) {
                return APIResponse.toExceptionResponse(("新旧密码输入不一致,请稍后重试"));
            }
            // 校验账号是否已被注册
            boolean usernameUnique = userService.checkUsernameUnique(reqBusinessAddTenant.getUsername());
            if (!usernameUnique) {
                return APIResponse.toExceptionResponse(("用户名已被注册,请修改后重新提交"));
            }
            SysUser sysUser = reqBusinessAddTenant.transferToSysUser();
            businessSysUserService.addSysUser(sysUser);

            //  保存员工信息
            SysStaff sysStaff = reqBusinessAddTenant.transferToSysStaff();
            sysStaff.setUserId(sysUser.getUserId());
            sysStaff.setCreateUser(loginUser.getUserId());
            staffService.save(sysStaff);

            // 用户校区
            SysUserDept sysUserDept = new SysUserDept();
            sysUserDept.setUserId(sysUser.getUserId());
            sysUserDept.setDeptId(-1L);
            sysUserDept.setTenantId(sysTenant.getTenantId());
            deptService.save(sysUserDept);

            // 用户租户
            SysUserTenant sysUserTenant = new SysUserTenant();
            sysUserTenant.setTenantId(sysTenant.getTenantId());
            sysUserTenant.setUserId(sysUser.getUserId());
            userTenantService.save(sysUserTenant);

            // 用户角色
            List<SysUserRole> userRoleList = Lists.newArrayList();
            SysRole schoolRole = roleService.selectByRoleCode(RoleEnum.SCHOOL.getRoleCode());
            SysRole schoolManagerRole = roleService.selectByRoleCode(RoleEnum.SCHOOL_MANAGER.getRoleCode());
            SysRole teachingManagerRole = roleService.selectByRoleCode(RoleEnum.TEACHING_MANAGER.getRoleCode());
            SysRole teacherRole = roleService.selectByRoleCode(RoleEnum.TEACHER.getRoleCode());
            if (null == schoolRole) {
                throw new BusinessException("无法获取学校角色");
            } if (null == schoolManagerRole) {
                throw new BusinessException("无法获取校长角色");
            }
            if (null == teachingManagerRole) {
                throw new BusinessException("无法获取教务管理角色");
            }
            if (null == teacherRole) {
                throw new BusinessException("无法获取教师角色");
            }
            SysUserRole schoolUserRole = new SysUserRole();
            schoolUserRole.setUserId(sysUser.getUserId());
            schoolUserRole.setRoleId(schoolRole.getRoleId());
            schoolUserRole.setTenantId(sysTenant.getTenantId());
            userRoleList.add(schoolUserRole);

            SysUserRole schoolManagerUserRole = new SysUserRole();
            schoolManagerUserRole.setUserId(sysUser.getUserId());
            schoolManagerUserRole.setRoleId(schoolManagerRole.getRoleId());
            schoolManagerUserRole.setTenantId(sysTenant.getTenantId());
            userRoleList.add(schoolManagerUserRole);

            SysUserRole teachingManagerUserRole = new SysUserRole();
            teachingManagerUserRole.setUserId(sysUser.getUserId());
            teachingManagerUserRole.setRoleId(teachingManagerRole.getRoleId());
            teachingManagerUserRole.setTenantId(sysTenant.getTenantId());
            userRoleList.add(teachingManagerUserRole);

            SysUserRole teacherUserRole = new SysUserRole();
            teacherUserRole.setUserId(sysUser.getUserId());
            teacherUserRole.setRoleId(teacherRole.getRoleId());
            teacherUserRole.setTenantId(sysTenant.getTenantId());
            userRoleList.add(teacherUserRole);

            userRoleService.saveBatch(userRoleList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            TenantContextHolder.remove();
        }
        return APIResponse.toOkResponse();
    }

    /**
     * 更新
     *
     * @param sysTenant
     * @return
     */
    public APIResponse updateTenant(SysTenant sysTenant) {
        if (StringUtils.isEmpty(sysTenant.getTenantId())) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        SysTenant dbTenant = sysTenantService.getById(sysTenant.getTenantId());
        if(null != dbTenant && dbTenant.getSuperTenant()) {
            return APIResponse.toAPIResponse("此租户信息不允许变更");
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        sysTenant.setBeginTime(new DateTime(sysTenant.getBeginTime()).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate());
        sysTenant.setEndTime(new DateTime(sysTenant.getEndTime()).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate());
        sysTenant.setLastUpdateUser(loginUser.getUserId());
        sysTenant.setLastUpdateTime(new Date());
        boolean updateTenant = sysTenantService.updateById(sysTenant);
        if (updateTenant) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param tenantIds
     * @return
     */
    public APIResponse deleteById(String[] tenantIds) {
        if (null == tenantIds || tenantIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }

        for (String tenantId : tenantIds) {

            SysTenant dbTenant = sysTenantService.getById(tenantId);
            if(null != dbTenant && dbTenant.getSuperTenant()) {
                return APIResponse.toAPIResponse(dbTenant.getTenantName() + ",此租户信息不允许删除");
            }

            // 是否有学生
            Integer tenantStudentCount = studentMapper.selectTenantStudentCount(tenantId);
            if (tenantStudentCount > 0) {
                return APIResponse.toExceptionResponse("租户下有学员,无法删除");
            }
            // 是否有课
            Integer tenantCourseCount = courseMapper.selectTenantCourseCount(tenantId);
            if (tenantCourseCount > 0) {
                return APIResponse.toExceptionResponse("租户下有课程,无法删除");
            }
            // 是否有班
            Integer tenantClaCount = claMapper.selectTenantClaCount(tenantId);
            if (tenantClaCount > 0) {
                return APIResponse.toExceptionResponse("租户下有班级,无法删除");
            }
        }

        boolean deleteTenant = sysTenantService.removeByIds(Arrays.asList(tenantIds));
        if (deleteTenant) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 用户租户列表 select
     *
     * @return
     */
    public APIResponse userTenantSelectLimitSelf(String userId) {
        String limitUserId = LoginUserUtil.getLoginUserId();
        boolean superTenantUserHasRole = userService.checkSuperTenantUserHasRole(limitUserId, RoleEnum.TENANT_MANAGER.getRoleCode());
        if (superTenantUserHasRole) {
            List<SysTenant> sysTenants = sysTenantService.selectUserTenantList(userId, null);
            return APIResponse.toAPIResponse(sysTenants);
        } else {
            List<SysTenant> sysTenants = sysTenantService.selectUserTenantList(userId, limitUserId);
            return APIResponse.toAPIResponse(sysTenants);
        }


    }

    /**
     * 用户租户列表 select
     *
     * @return
     */
    public APIResponse userTenantSelect(String userId) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        List<SysTenant> sysTenants = sysTenantService.selectUserTenantList(userId, null);
        return APIResponse.toAPIResponse(sysTenants);
    }

    /**
     * 当前登录人 租户列表 select
     *
     * @return
     */
    public APIResponse loginUserTenantSelect() {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        List<SysTenant> sysTenants = sysTenantService.selectUserTenantList(loginUser.getUserId(), null);
        sysTenants.stream().forEach(item -> {
            if (loginUser.getNowTenantId().equals(item.getTenantId())) {
                item.setNowTenant(true);
            }
        });
        return APIResponse.toAPIResponse(sysTenants);
    }

    /**
     * 当前租户信息
     * @return
     */
    public SysTenant nowTenantInfo() {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        String nowTenantId = loginUser.getNowTenantId();
        QueryWrapper<SysTenant> qw = new QueryWrapper<>();
        qw.select("tenant_name","contact_name","contact_phone","contact_address").eq("tenant_id", nowTenantId);
        return sysTenantService.getOne(qw);
    }
}
