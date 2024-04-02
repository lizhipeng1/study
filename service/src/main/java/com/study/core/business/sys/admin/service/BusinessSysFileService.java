package com.study.core.business.sys.admin.service;

import cn.xluobo.business.sys.admin.domain.req.ReqUploadFile;
import cn.xluobo.business.sys.admin.repo.model.SysFile;
import cn.xluobo.config.properties.UploadConfigProperties;
import cn.xluobo.core.api.APIResponse;
import cn.xluobo.core.api.ApiResEnums;
import cn.xluobo.core.utils.MimeTypeUtils;
import cn.xluobo.utils.LoginUserUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 文件上传
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020-03-01 09:46
 */
@Service
public class BusinessSysFileService {

    @Autowired
    private UploadConfigProperties uploadConfigProperties;

    @Autowired
    private ISysFileService sysFileService;

    /**
     * 上传图片
     *
     * @param reqUploadFile
     * @return
     */
    public APIResponse uploadImg(ReqUploadFile reqUploadFile) throws IOException {
        MultipartFile file = reqUploadFile.getFile();
        String fileType = reqUploadFile.getFileType();
        String fileIndex = reqUploadFile.getFileIndex();
        if (StringUtils.isAnyEmpty(fileType)) {
            return APIResponse.toExceptionResponse("请求参数错误");
        }

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String path = uploadConfigProperties.getFilePath();
        //上传文件夹
        path = path + "/" + fileType;


        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        if (!ServletFileUpload.isMultipartContent(request)) {
            return APIResponse.toExceptionResponse(ApiResEnums.PARAM_FAIL);
        }


        String originalFileName = file.getOriginalFilename();
        if (originalFileName.length() > 180) {
            originalFileName = originalFileName.substring(originalFileName.length() - 180);
        }
        if("blob".equals(originalFileName)){
            String sysUserId = LoginUserUtil.getLoginUserId();
            originalFileName = sysUserId + originalFileName;
        }

        //文件名
        String fileName = DateTime.now().toString("yyyyMMddHHmmss") + originalFileName;
        //后缀
        String suffix = FilenameUtils.getExtension(originalFileName);
        if (StringUtils.isEmpty(suffix)) {
            suffix = MimeTypeUtils.getExtension(file.getContentType());
            fileName = fileName + "." + suffix;
        }

        File uploadFileDir = new File(path);
        if (!uploadFileDir.exists()) {
            uploadFileDir.mkdir();
        }

        File uploadFile = new File(uploadFileDir, fileName);
        FileUtils.copyInputStreamToFile(file.getInputStream(), uploadFile);

        SysFile sysFile = new SysFile();
        sysFile.setFileIndex(fileIndex);
        sysFile.setFileName(fileName);
        sysFile.setFileSize(String.valueOf(file.getSize()));
        sysFile.setFileSuffix(suffix);
        sysFile.setFileType(fileType);
        sysFile.setFilePType("-1");
        sysFile.setPrePath("/static/upload/" + fileType + "/");

        sysFileService.save(sysFile);
        return APIResponse.toAPIResponse(sysFile);
    }

}
