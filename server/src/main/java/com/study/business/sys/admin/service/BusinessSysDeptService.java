package com.study.business.sys.admin.service;

import com.study.business.sc.student.repo.model.ScStudentCourse;
import com.study.business.sc.student.service.IScStudentCourseService;
import com.study.business.sys.admin.domain.req.ReqSearchSysDept;
import com.study.business.sys.admin.domain.resp.RespTreeSelect;
import com.study.business.sys.admin.repo.model.SysDept;
import com.study.business.sys.admin.repo.model.SysUserDept;
import com.study.config.login.LoginUser;
import com.study.core.api.APIResponse;
import com.study.core.api.ApiResEnums;
import com.study.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessSysDeptService {

    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private ISysUserDeptService userDeptService;
    @Autowired
    private IScStudentCourseService studentCourseService;

    /**
     * 查询树
     *
     * @param reqSearchSysDept
     * @return
     */
    public APIResponse searchList(ReqSearchSysDept reqSearchSysDept) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("delete_flag", "0");
        if (StringUtils.isNotEmpty(reqSearchSysDept.getDeptName())) {
            qw.like("dept_name", reqSearchSysDept.getDeptName());
        }
        if (StringUtils.isNotEmpty(reqSearchSysDept.getInUse())) {
            qw.eq("in_use", reqSearchSysDept.getInUse());
        }
        qw.orderByAsc("sort");
        List<SysDept> list = sysDeptService.list(qw);
        SysDept sysDept = new SysDept();
        sysDept.setDeptId(-1L);
        sysDept.converterTree(list);
        List<SysDept> respPage = sysDept.getChildren();
        return APIResponse.toAPIResponse(respPage);
    }

    /**
     * 前端select
     *
     * @return
     */
    public APIResponse treeSelect() {
        SysDept sysDept = new SysDept();
        sysDept.setInUse("1");
        sysDept.setDeleteFlag("0");
        QueryWrapper<SysDept> qw = new QueryWrapper(sysDept);
        qw.orderByAsc("sort");
        List<SysDept> list = sysDeptService.list(qw);
        SysDept tree = new SysDept();
        tree.setDeptId(-1L);
        tree.converterTree(list);
        List<SysDept> children = tree.getChildren();
        List<RespTreeSelect> respTreeSelects = children.stream().map(RespTreeSelect::new).collect(Collectors.toList());
        return APIResponse.toAPIResponse(respTreeSelects);
    }

    /**
     * 详情
     *
     * @param deptId
     * @return
     */
    public APIResponse detailById(Long deptId) {
        if (null == deptId) {
            return APIResponse.toAPIResponse(null);
        }
        SysDept detailInfo = sysDeptService.getById(deptId);
        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param sysDept
     * @return
     */
    public APIResponse addDept(SysDept sysDept) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        sysDept.setCreateUser(loginUser.getUserId());
        SysDept parentDept = sysDeptService.getById(sysDept.getParentId());
        if (null != parentDept && "0".equals(parentDept.getInUse())) {
            return APIResponse.toExceptionResponse("上级部门已停用,不允许新增");
        }
        if (null != parentDept) {
            sysDept.setAncestors(parentDept.getAncestors() + "," + sysDept.getParentId());
        } else {
            sysDept.setAncestors(String.valueOf(sysDept.getParentId()));
        }

        boolean addDept = sysDeptService.save(sysDept);
        if (addDept) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 更新
     *
     * @param sysDept
     * @return
     */
    public APIResponse updateDept(SysDept sysDept) {
        if (null == sysDept.getDeptId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        if (sysDept.getParentId().compareTo(sysDept.getDeptId()) == 0) {
            return APIResponse.toExceptionResponse("上级部门不能是自己");
        }
        //修改子元素 关系
        SysDept newParentDept = sysDeptService.getById(sysDept.getParentId());
        SysDept oldDept = sysDeptService.getById(sysDept.getDeptId());
        if (null != newParentDept && null != oldDept) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            sysDept.setAncestors(newAncestors);
            sysDeptService.updateDeptChildren(sysDept.getDeptId(), newAncestors, oldAncestors);
        }

        LoginUser loginUser = LoginUserUtil.getLoginUser();
        sysDept.setLastUpdateUser(loginUser.getUserId());
        sysDept.setLastUpdateTime(new Date());
        boolean updateDept = sysDeptService.updateById(sysDept);
        if (updateDept) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 删除
     *
     * @param deptIds
     * @return
     */
    public APIResponse deleteById(Long[] deptIds) {
        if (null == deptIds || deptIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }

        for (Long deptId : deptIds) {
            if (sysDeptService.hadChild(deptId)) {
                return APIResponse.toExceptionResponse("存在子部门不能删除");
            }
            if (sysDeptService.hadUser(deptId)) {
                return APIResponse.toExceptionResponse("存在用户不能删除");
            }
        }

        // 如果已报读 不允许删除
        QueryWrapper<ScStudentCourse> qwSc = new QueryWrapper<>();
        qwSc.in("dept_id", deptIds);
        int studentCourseCount = studentCourseService.count(qwSc);
        if (studentCourseCount != 0) {
            return APIResponse.toExceptionResponse("该校区已有报读学员,无法删除");
        }

        boolean deleteDept = sysDeptService.removeByIds(Arrays.asList(deptIds));
        if (deleteDept) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }

    /**
     * 前端select
     *
     * @return
     */
    public List<RespTreeSelect> campusList() {
        SysDept sysDept = new SysDept();
        sysDept.setInUse("1");
        sysDept.setDeleteFlag("0");
        sysDept.setDeptType("2");
        QueryWrapper<SysDept> qw = new QueryWrapper(sysDept);
        qw.orderByAsc("sort");
        List<SysDept> list = sysDeptService.list(qw);
        List<RespTreeSelect> respTreeSelects = list.stream().map(RespTreeSelect::new).collect(Collectors.toList());
        return respTreeSelects;
    }

    /**
     * 前端select
     *
     * @return
     */
    public List<RespTreeSelect> campusListLimitByUser() {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        List<SysDept> deptList = sysDeptService.selectUserCampusList(loginUser.getUserId());
        List<RespTreeSelect> respTreeSelects = deptList.stream().map(RespTreeSelect::new).collect(Collectors.toList());
        return respTreeSelects;
    }

    /**
     * 校区选择
     * 如果拥有全部校区权限，返回全部校区 部分校区
     * 否二 返回 部分校区
     *
     * @return
     */
    public List<RespTreeSelect> campusSelect() {

        List<RespTreeSelect> list = Lists.newArrayList();

        list.add(new RespTreeSelect("all","全部校区",null));
        list.add(new RespTreeSelect("part","部分校区",null));

        return list;
    }

    /**
     * 校区选择
     * 如果拥有全部校区权限，返回全部校区 部分校区
     * 否二 返回 部分校区
     *
     * @return
     */
    public List<RespTreeSelect> campusSelectLimitByUser() {
        LoginUser loginUser = LoginUserUtil.getLoginUser();

        List<RespTreeSelect> list = Lists.newArrayList();

        QueryWrapper qw = new QueryWrapper();
        qw.eq("user_id",loginUser.getUserId());
        qw.eq("dept_id","-1");
        SysUserDept one = userDeptService.getOne(qw);
        if(null != one){
            list.add(new RespTreeSelect("all","全部校区",null));
        }
        list.add(new RespTreeSelect("part","部分校区",null));

        return list;
    }
}
