package com.study.core.business.sc.student.service;

import cn.xluobo.business.sc.base.repo.model.ScSchool;
import cn.xluobo.business.sc.base.service.IScSchoolService;
import cn.xluobo.business.sc.student.domain.req.ReqSearchScStudent;
import cn.xluobo.business.sc.student.domain.req.ReqStudentSelect;
import cn.xluobo.business.sc.student.domain.resp.RespSearchStudent;
import cn.xluobo.business.sc.student.repo.mapper.ScStudentMapper;
import cn.xluobo.business.sc.student.repo.model.ScStudent;
import cn.xluobo.business.sc.student.repo.model.ScStudentAccount;
import cn.xluobo.business.sc.student.repo.model.ScStudentContact;
import cn.xluobo.business.sc.student.repo.model.ScStudentCourse;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.api.ApiResEnums;
import cn.xluobo.core.page.RespPage;
import cn.xluobo.utils.LoginUserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:24
 */
@Service
@Transactional
public class BusinessScStudentService {

    @Autowired
    private IScStudentService scStudentService;

    @Autowired
    private IScStudentContactService contactService;

    @Autowired
    private ScStudentMapper studentMapper;
    @Autowired
    private IScSchoolService schoolService;
    @Autowired
    private IScStudentCourseService studentCourseService;
    @Autowired
    private IScStudentAccountService accountService;

    /**
     * 查询
     *
     * @param reqSearchScStudent
     * @return
     */
    public APIResponse searchList(ReqSearchScStudent reqSearchScStudent) {
        RespPage<RespSearchStudent> page = new RespPage(reqSearchScStudent.getPageNum(), reqSearchScStudent.getPageSize());
        List<RespSearchStudent> respSearchStudents = studentMapper.selectForSearchTable(reqSearchScStudent, page);
        page.setRows(respSearchStudents);
        return APIResponse.toAPIResponse(page);
    }

    /**
     * 前端select
     *
     * @return
     */
    public APIResponse select(ReqStudentSelect studentSelect) {
        RespPage<RespSearchStudent> page = new RespPage(studentSelect.getPageNum(), studentSelect.getPageSize());
        List<RespSearchStudent> list = scStudentService.selectStudentSelect(studentSelect, page);
        return APIResponse.toAPIResponse(list);
    }

    /**
     * 详情
     *
     * @param studentId
     * @return
     */
    public APIResponse detailById(Long studentId) {
        if (null == studentId) {
            return APIResponse.toAPIResponse(null);
        }
        ScStudent detailInfo = scStudentService.getById(studentId);

        // 联系人信息
        QueryWrapper<ScStudentContact> qw = new QueryWrapper<>();
        qw.eq("student_id", studentId);
        List<ScStudentContact> contactList = contactService.list(qw);
        detailInfo.setContactList(contactList);

        // 学校名称
        if (null != detailInfo.getSchoolId()) {
            ScSchool scSchool = schoolService.getById(detailInfo.getSchoolId());
            detailInfo.setSchoolName(scSchool.getSchoolName());
        }

        return APIResponse.toAPIResponse(detailInfo);
    }

    /**
     * 添加
     *
     * @param scStudent
     * @return
     */
    public APIResponse addScStudent(ScStudent scStudent) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();

        if (StringUtils.isNotEmpty(scStudent.getSchoolName())) {
            Long schoolId = schoolService.getSchoolId(scStudent.getSchoolName());
            scStudent.setSchoolId(schoolId);
        }

        // 保存学生信息
        List<ScStudentContact> contactList = scStudent.getContactList();
        if (null != contactList && contactList.size() > 0) {
            scStudent.setPhone(contactList.get(0).getContactPhone());
        }
        scStudent.setCreateUser(loginUser.getUserId());
        scStudentService.save(scStudent);

        // 删除联系人信息
        UpdateWrapper<ScStudentContact> uw = new UpdateWrapper<>();
        uw.eq("student_id", scStudent.getStudentId());
        contactService.remove(uw);

        // 保存联系人信息
        if (null != contactList && contactList.size() > 0) {
            contactList.forEach(item -> {
                item.setStudentId(scStudent.getStudentId());
                item.setCreateUser(loginUser.getUserId());
            });
            contactService.saveBatch(contactList);

            scStudent.setPhone(contactList.get(0).getContactPhone());
        }

        return APIResponse.toOkResponse();
    }

    /**
     * 更新
     *
     * @param scStudent
     * @return
     */
    public APIResponse updateScStudent(ScStudent scStudent) {
        if (null == scStudent.getStudentId()) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
        LoginUser loginUser = LoginUserUtil.getLoginUser();

        if (StringUtils.isNotEmpty(scStudent.getSchoolName())) {
            Long schoolId = schoolService.getSchoolId(scStudent.getSchoolName());
            scStudent.setSchoolId(schoolId);
        }

        // 删除联系人信息
        UpdateWrapper<ScStudentContact> uw = new UpdateWrapper<>();
        uw.eq("student_id", scStudent.getStudentId());
        contactService.remove(uw);

        // 保存联系人信息
        List<ScStudentContact> contactList = scStudent.getContactList();
        if (null != contactList && contactList.size() > 0) {
            contactList.forEach(item -> {
                item.setStudentId(scStudent.getStudentId());
                item.setCreateUser(loginUser.getUserId());
            });
            contactService.saveBatch(contactList);

            scStudent.setPhone(contactList.get(0).getContactPhone());
        }

        // 更新学生信息
        scStudent.setLastUpdateUser(loginUser.getUserId());
        scStudent.setLastUpdateTime(new Date());
        boolean updateScStudent = scStudentService.updateById(scStudent);

        return APIResponse.toOkResponse();
    }

    /**
     * 删除
     *
     * @param studentIds
     * @return
     */
    public APIResponse deleteById(Long[] studentIds) {
        if (null == studentIds || studentIds.length == 0) {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }

        // 如果已报读 不允许删除
        QueryWrapper<ScStudentCourse> qwSc = new QueryWrapper<>();
        qwSc.in("student_id", studentIds);
        int studentCourseCount = studentCourseService.count(qwSc);
        if (studentCourseCount != 0) {
            return APIResponse.toExceptionResponse("学生已报读课程,无法删除");
        }

        QueryWrapper<ScStudentAccount> qwSsa = new QueryWrapper<>();
        qwSsa.in("student_id", studentIds);
        List<ScStudentAccount> accountList = accountService.list(qwSsa);
        for (ScStudentAccount account : accountList) {
            if(account.getBalanceFee().compareTo(BigDecimal.ZERO) != 0) {
                return APIResponse.toExceptionResponse("学生账户尚有余额'"+account.getBalanceFee().toString()+"元',无法删除");
            }
        }

        boolean deleteScStudent = scStudentService.removeByIds(Arrays.asList(studentIds));
        if (deleteScStudent) {
            return APIResponse.toOkResponse();
        } else {
            return APIResponse.toExceptionResponse(ApiResEnums.FAIL_WAIT_A_MINUTE);
        }
    }
}
