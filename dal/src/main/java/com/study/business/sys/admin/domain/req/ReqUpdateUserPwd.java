package com.study.business.sys.admin.domain.req;

import lombok.Data;

import java.io.Serializable;

/**
 * 修改当前用户密码
 * @author ：zhangbaoyu
 * @date ：Created in 2020-02-19 22:14
 */
@Data
public class ReqUpdateUserPwd implements Serializable {

    private String oldPassword;

    private String newPassword;

    private String confirmPassword;

}
