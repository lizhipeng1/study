package com.study.business.sys.staff.service;

import com.study.business.sc.course.domain.req.ReqSelect;
import com.study.business.sc.course.repo.model.ScClaTime;
import com.study.business.sc.course.repo.model.ScCourseCla;
import com.study.business.sc.course.service.IScClaTimeService;
import com.study.business.sc.course.service.IScCourseClaService;
import com.study.business.sys.admin.domain.req.ReqUpdateUserRole;
import com.study.business.sys.admin.repo.model.SysUser;
import com.study.business.sys.admin.repo.model.SysUserDept;
import com.study.business.sys.admin.repo.model.SysUserTenant;
import com.study.business.sys.admin.service.*;
import com.study.business.sys.staff.domain.req.ReqBusinessAddStaff;
import com.study.business.sys.admin.service.ISysRoleService;
import com.study.business.sys.admin.service.ISysUserDeptService;
import com.study.business.sys.admin.service.ISysUserService;
import com.study.business.sys.admin.service.ISysUserTenantService;
import com.study.business.sys.staff.domain.req.ReqSearchStaff;
import com.study.business.sys.staff.domain.resp.RespBusinessStaffDetail;
import com.study.business.sys.staff.domain.resp.RespStaffInfo;
import com.study.business.sys.staff.repo.model.SysStaff;
import com.study.config.login.LoginUser;
import com.study.core.api.APIBaseResponse;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.core.page.RespPage;
import com.study.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessSysStaffService {

    @Autowired
    private ISysStaffService sysStaffService;
    @Autowired
    private BusinessSysUserService businessSysUserService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private IScClaTimeService claTimeService;
    @Autowired
    private IScCourseClaService courseClaService;
    @Autowired
    private ISysUserDeptService userDeptService;
    @Autowired
    private ISysUserTenantService userTenantService;

    /**
     * 查询
     *
     * @param reqSearchSysStaff
     * @return
     */
    public APIResponse searchList(ReqSearchStaff reqSearchSysStaff) {
        RespPage<RespStaffInfo> page = new RespPage(reqSearchSysStaff.getPageNum(), reqSearchSysStaff.getPageSize());
        List<RespStaffInfo> staffList = sysStaffService.searchStaffList(reqSearchSysStaff, page);
        page.setRows(staffList);
        return APIResponse.toAPIResponse(page);
    }

    /**
     * 前端select
     *
     * @return
     */
    public APIResponse teacherSelect(ReqSelect reqSelect) {
        List<SysStaff> list = sysStaffService.teacherList(reqSelect.getSearch());
        return APIResponse.toAPIResponse(list);
    }


    /**
     * 详情
     *
     * @param teacherId
     * @return
     */
    public APIResponse detailById(Long teacherId) {
        if (null == teacherId) {
            return APIResponse.toAPIResponse(null);
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();

        RespBusinessStaffDetail respBusinessStaffDetail = new RespBusinessStaffDetail();

        // 员工信息
        SysStaff staffInfo = sysStaffService.getById(teacherId);
        respBusinessStaffDetail.setStaffInfo(staffInfo);

        // 用户名
        if (StringUtils.isNotEmpty(staffInfo.getUserId())) {
            QueryWrapper<SysUser> qw = new QueryWrapper<>();
            qw.select("username", "locked");
            qw.eq("user_id", staffInfo.getUserId());
            SysUser sysUser = userService.getOne(qw);
            if (null == sysUser) {
                staffInfo.setUserId(null);
            }
            respBusinessStaffDetail.setUserInfo(sysUser);

            // 员工所属校区
            QueryWrapper<SysUserDept> qwUserDept = new QueryWrapper<>();
            qwUserDept.eq("user_id", staffInfo.getUserId());
            qwUserDept.eq("tenant_id", loginUser.getNowTenantId());
            List<SysUserDept> userDeptList = userDeptService.list(qwUserDept);
            boolean allCampus = userDeptList.stream().anyMatch(item -> item.getDeptId() == -1L);
            if (allCampus) {
                respBusinessStaffDetail.setBelongCampus("all");
                respBusinessStaffDetail.setPartCampus(Lists.emptyList());
            } else {
                respBusinessStaffDetail.setBelongCampus("part");

                List<Long> deptIdList = userDeptList.stream().map(SysUserDept::getDeptId).collect(Collectors.toList());
                respBusinessStaffDetail.setPartCampus(deptIdList);
            }


            // 用户角色
            List<String> roleTreeIdList = sysRoleService.selectUserRoleTreeIdList(staffInfo.getUserId(), loginUser.getNowTenantId());
            respBusinessStaffDetail.setRoleTreeIdList(roleTreeIdList);
        } else {
            respBusinessStaffDetail.setUserInfo(null);
            respBusinessStaffDetail.setRoleTreeIdList(Lists.emptyList());
            respBusinessStaffDetail.setBelongCampus("all");
            respBusinessStaffDetail.setPartCampus(Lists.newArrayList());
        }

        return APIResponse.toAPIResponse(respBusinessStaffDetail);
    }

    /**
     * 添加员工
     *
     * @param staff
     * @return
     */
    public APIResponse addSysStaff(ReqBusinessAddStaff staff) {
        APIBaseResponse checkParam = staff.checkParam();
        if (!checkParam.isSuccess()) {
            return APIResponse.toExceptionResponse(checkParam.getRespMsg());
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        boolean isLoginUser = staff.isLoginUser();
        // 保存用户信息
        if (isLoginUser) {

            if (StringUtils.isAnyEmpty(staff.getUsername(), staff.getPassword())) {
                return APIResponse.toExceptionResponse(("请求参数错误,请完善后重试"));
            } else if (!staff.getPassword().equals(staff.getCheckPass())) {
                return APIResponse.toExceptionResponse(("新旧密码输入不一致,请稍后重试"));
            }
            // 校验账号是否已被注册
            boolean usernameUnique = userService.checkUsernameUnique(staff.getUsername());
            if (!usernameUnique) {
                return APIResponse.toExceptionResponse(("用户名已被注册,请修改后重新提交"));
            }

            // 添加用户
            SysUser sysUser = new SysUser();
            sysUser.setUsername(staff.getUsername());
            sysUser.setPassword(staff.getPassword());
            sysUser.setName(staff.getStaffName());
            sysUser.setPhone(staff.getPhone());
            sysUser.setEmailAddress(staff.getEmailAddress());
            sysUser.setDeptId(staff.getDeptId());
            sysUser.setLocked(staff.getLocked());
            businessSysUserService.addSysUser(sysUser);

            // 关联当前租户
            SysUserTenant sysUserTenant = new SysUserTenant();
            sysUserTenant.setUserId(sysUser.getUserId());
            sysUserTenant.setTenantId(loginUser.getNowTenantId());
            userTenantService.save(sysUserTenant);

            staff.setUserId(sysUser.getUserId());

            // 保存角色信息
            ReqUpdateUserRole reqUpdateUserRole = new ReqUpdateUserRole();
            reqUpdateUserRole.setUserId(sysUser.getUserId());
            reqUpdateUserRole.setTenantId(loginUser.getNowTenantId());
            reqUpdateUserRole.setRoleIds(staff.getRoleIds());
            businessSysUserService.changeUserRole(reqUpdateUserRole);

            // 所属校区
            String belongCampus = staff.getBelongCampus();
            if ("all".equals(belongCampus)) {
                SysUserDept sysUserDept = new SysUserDept();
                sysUserDept.setUserId(sysUser.getUserId());
                sysUserDept.setDeptId(-1L);
                sysUserDept.setTenantId(loginUser.getNowTenantId());
                userDeptService.save(sysUserDept);
            } else if ("part".equals(belongCampus)) {
                String[] partCampus = staff.getPartCampus();
                List<SysUserDept> userDeptList = Lists.newArrayList();
                for (String campus : partCampus) {
                    SysUserDept sysUserDept = new SysUserDept();
                    sysUserDept.setUserId(sysUser.getUserId());
                    sysUserDept.setDeptId(Long.valueOf(campus));
                    sysUserDept.setTenantId(loginUser.getNowTenantId());
                    userDeptList.add(sysUserDept);
                }
                if (userDeptList.size() > 0) {
                    userDeptService.saveBatch(userDeptList);
                }
            }
        }

        // 保存员工信息
        staff.setCreateUser(loginUser.getUserId());
        boolean addSysStaff = sysStaffService.save(staff);
        if (addSysStaff) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param staff
     * @return
     */
    public APIResponse updateSysStaff(ReqBusinessAddStaff staff) {
        if (null == staff.getStaffId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        APIBaseResponse checkParam = staff.checkParam();
        if (!checkParam.isSuccess()) {
            return APIResponse.toExceptionResponse(checkParam.getRespMsg());
        }

        LoginUser loginUser = LoginUserUtil.getLoginUser();
        // 可登陆信息
        boolean isLoginUser = staff.isLoginUser();

        SysStaff dbStaffInfo = sysStaffService.getById(staff.getStaffId());

        if (StringUtils.isEmpty(dbStaffInfo.getUserId()) && isLoginUser) {
            if (StringUtils.isAnyEmpty(staff.getUsername(), staff.getPassword())) {
                return APIResponse.toExceptionResponse(("请求参数错误,请完善后重试"));
            } else if (!staff.getPassword().equals(staff.getCheckPass())) {
                return APIResponse.toExceptionResponse(("新旧密码输入不一致,请稍后重试"));
            }
            // 校验账号是否已被注册
            boolean usernameUnique = userService.checkUsernameUnique(staff.getUsername());
            if (!usernameUnique) {
                return APIResponse.toExceptionResponse(("用户名已被注册,请修改后重新提交"));
            }
        }

        boolean addUser = false;
        if (StringUtils.isNotEmpty(dbStaffInfo.getUserId())) {
            addUser = true;
            // 如果有关联用户

            // 变更用户相关信息
            SysUser sysUser = new SysUser();
            sysUser.setUserId(dbStaffInfo.getUserId());
            sysUser.setName(staff.getStaffName());
            sysUser.setPhone(staff.getPhone());
            sysUser.setEmailAddress(staff.getEmailAddress());
            sysUser.setDeptId(staff.getDeptId());
            // 根据是否允许登录 变更锁定状态
            sysUser.setLocked(staff.getLocked());
            businessSysUserService.updateSysUser(sysUser);

            // 变更角色信息
            ReqUpdateUserRole reqUpdateUserRole = new ReqUpdateUserRole();
            reqUpdateUserRole.setUserId(sysUser.getUserId());
            reqUpdateUserRole.setTenantId(loginUser.getNowTenantId());
            reqUpdateUserRole.setRoleIds(staff.getRoleIds());
            businessSysUserService.changeUserRole(reqUpdateUserRole);

            // 不变更关联用户ID
            staff.setUserId(dbStaffInfo.getUserId());
        } else if (StringUtils.isEmpty(dbStaffInfo.getUserId()) && isLoginUser) {
            addUser = true;
            // 没有关联用户 并且允许登录
            SysUser sysUser = new SysUser();
            sysUser.setUsername(staff.getUsername());
            sysUser.setPassword(staff.getPassword());
            sysUser.setName(staff.getStaffName());
            sysUser.setPhone(staff.getPhone());
            sysUser.setEmailAddress(staff.getEmailAddress());
            sysUser.setDeptId(staff.getDeptId());
            businessSysUserService.addSysUser(sysUser);
            staff.setUserId(sysUser.getUserId());

            // 保存角色信息
            ReqUpdateUserRole reqUpdateUserRole = new ReqUpdateUserRole();
            reqUpdateUserRole.setUserId(sysUser.getUserId());
            reqUpdateUserRole.setTenantId(loginUser.getNowTenantId());
            reqUpdateUserRole.setRoleIds(staff.getRoleIds());
            businessSysUserService.changeUserRole(reqUpdateUserRole);
        }

        if (addUser) {
            // 删除原校区
            UpdateWrapper<SysUserDept> uw = new UpdateWrapper<>();
            uw.eq("user_id", staff.getUserId());
            userDeptService.remove(uw);
            // 所属校区
            String belongCampus = staff.getBelongCampus();
            if ("all".equals(belongCampus)) {
                SysUserDept sysUserDept = new SysUserDept();
                sysUserDept.setUserId(staff.getUserId());
                sysUserDept.setDeptId(-1L);
                sysUserDept.setTenantId(loginUser.getNowTenantId());
                userDeptService.save(sysUserDept);
            } else if ("part".equals(belongCampus)) {
                String[] partCampus = staff.getPartCampus();
                List<SysUserDept> userDeptList = Lists.newArrayList();
                for (String campus : partCampus) {
                    SysUserDept sysUserDept = new SysUserDept();
                    sysUserDept.setUserId(staff.getUserId());
                    sysUserDept.setDeptId(Long.valueOf(campus));
                    sysUserDept.setTenantId(loginUser.getNowTenantId());
                    userDeptList.add(sysUserDept);
                }
                if (userDeptList.size() > 0) {
                    userDeptService.saveBatch(userDeptList);
                }
            }
        }


        // 变更员工信息
        staff.setLastUpdateUser(loginUser.getUserId());
        staff.setLastUpdateTime(new Date());
        boolean updateSysStaff = sysStaffService.updateById(staff);
        if (updateSysStaff) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param teacherIds
     * @return
     */
    public APIResponse deleteById(Long[] teacherIds) {
        if (null == teacherIds || teacherIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }

        // 已排课不允许删除
        QueryWrapper<ScClaTime> qwSct = new QueryWrapper<>();
        qwSct.in("teacher_id", teacherIds);
        qwSct.eq("status", "1");
        int claTimeCount = claTimeService.count(qwSct);
        if (claTimeCount != 0) {
            return APIResponse.toExceptionResponse("员工有待上课程,无法删除");
        }

        // 是否为班级班主任
        QueryWrapper<ScCourseCla> qw = new QueryWrapper<>();
        qw.in("staff_id", teacherIds);
        int courseClaCount = courseClaService.count(qw);
        if (courseClaCount != 0) {
            return APIResponse.toExceptionResponse("员工当前为班级任课教师,无法删除");
        }


        // 用户禁用
        List<String> userIds = sysStaffService.getUserIds(teacherIds);
        if (null != userIds && userIds.size() > 0) {
            UpdateWrapper<SysUser> uw = new UpdateWrapper<>();
            uw.in("user_id", userIds);
            uw.set("enable", "0");
            userService.update(uw);
        }

        // 逻辑删除员工
        sysStaffService.removeByIds(Arrays.asList(teacherIds));
        return APIResponse.toOkResponse();
    }
}
