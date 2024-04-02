package com.study.core.business.tool.impt.service.strategy.impl;

import cn.xluobo.business.sc.base.domain.req.ReqSchoolSelect;
import cn.xluobo.business.sc.base.domain.resp.RespSchoolSelect;
import cn.xluobo.business.sc.base.service.IScSchoolService;
import cn.xluobo.business.sc.course.domain.export.ExpCourse;
import cn.xluobo.business.sc.course.domain.req.ReqCourseClaSelect;
import cn.xluobo.business.sc.course.domain.req.ReqSearchScCourse;
import cn.xluobo.business.sc.course.domain.resp.RespCourseClaSelectInfo;
import cn.xluobo.business.sc.course.enums.CourseChargeTypeEnum;
import cn.xluobo.business.sc.course.repo.mapper.ScCourseClaMapper;
import cn.xluobo.business.sc.course.repo.mapper.ScCourseMapper;
import cn.xluobo.business.sys.admin.domain.resp.RespTreeSelect;
import cn.xluobo.business.sys.admin.repo.model.SysDictData;
import cn.xluobo.business.sys.admin.service.BusinessSysDeptService;
import cn.xluobo.business.sys.admin.service.BusinessSysDictDataService;
import cn.xluobo.business.sys.receipt.repo.model.SysReceiptAccount;
import cn.xluobo.business.sys.receipt.service.ISysReceiptAccountService;
import cn.xluobo.business.sys.staff.repo.model.SysStaff;
import cn.xluobo.business.sys.staff.service.ISysStaffService;
import cn.xluobo.business.tool.export.handler.bean.SelectValidationData;
import cn.xluobo.business.tool.impt.domain.ImportStudentOrder;
import cn.xluobo.business.tool.impt.listener.ImportStudentOrderListener;
import cn.xluobo.business.tool.impt.service.strategy.AbstractImportStrategy;
import cn.xluobo.config.login.LoginUser;
import cn.xluobo.config.properties.UploadConfigProperties;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.utils.LoginUserUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 学生导入
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/10/12 16:24
 */
@Service("import_student_order")
@Slf4j
public class ImportStudentOrderImpl extends AbstractImportStrategy {

    @Autowired
    private BusinessSysDictDataService dictDataService;
    @Autowired
    private IScSchoolService schoolService;
    @Autowired
    private BusinessSysDeptService deptService;
    @Autowired
    private ScCourseMapper courseMapper;
    @Autowired
    private ScCourseClaMapper claMapper;
    @Autowired
    private ISysReceiptAccountService receiptAccountService;
    @Autowired
    private ISysStaffService staffService;
    @Autowired
    private UploadConfigProperties uploadConfigProperties;

    private static final String FILE_TYPE = "import_student_order";

    @Override
    public List<SelectValidationData> selectValidateConfig() {
        // 联系人关系
        List<SysDictData> contactRelationList = dictDataService.dictTypeDataList("contact_relation");
        String[] contactRelationArray = contactRelationList.stream().map(SysDictData::getDictLabel).toArray(String[]::new);

        // 性别
        List<SysDictData> sexList = dictDataService.dictTypeDataList("sex");
        String[] sexArray = sexList.stream().map(SysDictData::getDictLabel).toArray(String[]::new);

        // 学校
        List<RespSchoolSelect> schoolList = schoolService.selectSchoolSelect(new ReqSchoolSelect());
        String[] schoolArray = schoolList.stream().map(RespSchoolSelect::getSchoolName).toArray(String[]::new);

        // 订单类型
        List<SysDictData> orderDetailTagList = dictDataService.dictTypeDataList("order_detail_tag");
        String[] orderDetailTagArray = orderDetailTagList.stream().map(SysDictData::getDictLabel).toArray(String[]::new);

        // 报读校区
        List<RespTreeSelect> campusList = deptService.campusList();
        String[] campusNameArray = campusList.stream().map(RespTreeSelect::getLabel).toArray(String[]::new);

        // 报读课程 课程-收费方式(校区)
        ReqSearchScCourse reqSearchScCourse = new ReqSearchScCourse();
        reqSearchScCourse.setSale("1");
        List<ExpCourse> courseList = courseMapper.selectCourseForExport(reqSearchScCourse);

        String[] courseNameArray = courseList.stream().map(item -> {
            String courseName = item.getCourseName() + "(" + item.getDepartName() + ")-" + item.getChargeTypeName();
            if (CourseChargeTypeEnum.DATE.getChargeType().equals(item.getChargeType())) {
                courseName = courseName + "(" + item.getTotalFee().toString() + item.getDateUnitName() + ")";
            } else {
                courseName = courseName + "(" + item.getCount().toString() + "课时," + item.getTotalFee().toString() + "元)";
            }
            return courseName;
        }).toArray(String[]::new);

        // 报读班级 班级(教师)-课程
        List<RespCourseClaSelectInfo> claList = claMapper.selectForSelect(new ReqCourseClaSelect());
        String[] claNameArray = claList.stream().map(item -> item.getDeptName() + "-" + item.getClaName() + "(" + item.getStaffName() + ")" + "-" + item.getCourseName()).toArray(String[]::new);

        // 收款账户
        List<SysReceiptAccount> receiptAccountList = receiptAccountService.select();
        String[] receiptAccountNameArray = receiptAccountList.stream().map(SysReceiptAccount::getAccountName).toArray(String[]::new);

        // 销售员工
        List<SysStaff> staffList = staffService.staffList(null);
        String[] staffNameArray = staffList.stream().map(SysStaff::getStaffName).toArray(String[]::new);

        return Lists.newArrayList(
                SelectValidationData.builder().firstCol(1).lastCol(1).firstRow(2).lastRow(206).selectDataArray(contactRelationArray).build(),
                SelectValidationData.builder().firstCol(3).lastCol(3).firstRow(2).lastRow(206).selectDataArray(sexArray).build(),
                SelectValidationData.builder().firstCol(4).lastCol(4).firstRow(2).lastRow(206).selectDataArray(schoolArray).build(),
                SelectValidationData.builder().firstCol(6).lastCol(6).firstRow(2).lastRow(206).selectDataArray(orderDetailTagArray).build(),
                SelectValidationData.builder().firstCol(7).lastCol(7).firstRow(2).lastRow(206).selectDataArray(campusNameArray).build(),
                SelectValidationData.builder().firstCol(8).lastCol(8).firstRow(2).lastRow(206).selectDataArray(courseNameArray).build(),
                SelectValidationData.builder().firstCol(9).lastCol(9).firstRow(2).lastRow(206).selectDataArray(claNameArray).build(),
                SelectValidationData.builder().firstCol(16).lastCol(16).firstRow(2).lastRow(206).selectDataArray(campusNameArray).build(),
                SelectValidationData.builder().firstCol(19).lastCol(19).firstRow(2).lastRow(206).selectDataArray(receiptAccountNameArray).build(),
                SelectValidationData.builder().firstCol(20).lastCol(20).firstRow(2).lastRow(206).selectDataArray(staffNameArray).build()
        );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public APIResponse importDataByFileId(Long importId, String fileId) throws IOException {
        String path = uploadConfigProperties.getTempSaveImportPath();
        path = path + "/" + FILE_TYPE;
        File file = new File(path, fileId);
        if (!file.exists()) {
            return APIResponse.toExceptionResponse("无法获取文件,请重试");
        }


        LoginUser loginUser = LoginUserUtil.getLoginUser();
        ImportStudentOrderListener importListener = new ImportStudentOrderListener();
        importListener.setNeedSave(true);
        importListener.setLoginUserId(loginUser.getUserId());
        importListener.setLoginUser(loginUser);
        importListener.setImportId(importId);

        InputStream is = new FileInputStream(file);
        EasyExcel.read(is, ImportStudentOrder.class, importListener).sheet().autoTrim(true).headRowNumber(5).doRead();

        int successRecord = importListener.getSuccessRecordSize();
        int failRecord = importListener.getFailRecordSize();
        file.delete();
        return APIResponse.toAPIResponse("导入成功" + successRecord + "条,导入失败:" + failRecord);
    }

    @Override
    public APIResponse checkData(MultipartFile multipartFile) throws IOException {
        long fileSize = multipartFile.getSize();
        if (fileSize > 1024 * 1024 * 4) {
            return APIResponse.toExceptionResponse("导入文件需小于4M");
        }

        LoginUser loginUser = LoginUserUtil.getLoginUser();

        ImportStudentOrderListener importListener = new ImportStudentOrderListener();
        importListener.setNeedSave(false);
        importListener.setLoginUserId(loginUser.getUserId());

        // 保存文件
        String fileId = IdWorker.getIdStr();
        String fileName = DateTime.now().toString("yyyyMMddHHmmss") + "_" + fileId;
        String originalFileName = multipartFile.getOriginalFilename();
        String suffix = FilenameUtils.getExtension(originalFileName);
        if (StringUtils.isNotEmpty(suffix)) {
            fileName = fileName + "." + suffix;
        }

        String path = uploadConfigProperties.getTempSaveImportPath();
        //上传文件夹
        path = path + "/" + FILE_TYPE;
        File uploadFileDir = new File(path);
        if (!uploadFileDir.exists()) {
            uploadFileDir.mkdir();
        }
        File uploadFile = new File(uploadFileDir, fileName);
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), uploadFile);

        InputStream is = new FileInputStream(uploadFile);
        EasyExcel.read(is, ImportStudentOrder.class, importListener).sheet().autoTrim(true).headRowNumber(5).doRead();

        List<ImportStudentOrder> saveStudentOrderList = importListener.getSuccessRecordList();
        List<ImportStudentOrder> failStudentList = importListener.getFailRecordList();
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("successList", saveStudentOrderList);
        resultMap.put("failList", failStudentList);
        resultMap.put("fileId", fileName);

        return APIResponse.toAPIResponse(resultMap);
    }
}
