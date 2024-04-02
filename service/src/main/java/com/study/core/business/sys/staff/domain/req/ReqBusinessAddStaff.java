package com.study.core.business.sys.staff.domain.req;

import cn.xluobo.business.sys.staff.repo.model.SysStaff;
import cn.xluobo.core.api.APIBaseResponse;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 新增员工
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/18 08:44
 */
@Data
public class ReqBusinessAddStaff extends SysStaff {

    /**
     * 是否为登录用户
     */
    private boolean loginUser;

    /**
     * 是否允许登录
     */
    private String locked;

    /**
     * 角色Id
     */
    private Long[] roleIds;

    private String username;

    private String password;

    private String checkPass;

    /**
     * 校区权限 所有 或 部分 all part
     */
    private String belongCampus;

    /**
     * 具体校区
     */
    private String[] partCampus;

    public APIBaseResponse checkParam() {
        if (StringUtils.isAnyEmpty(getStaffName(), getPhone(), getSex())) {
            return APIBaseResponse.fail("请求参数错误,请完善后重试");
        }
        if (loginUser) {
            if (StringUtils.isEmpty(belongCampus)) {
                return APIBaseResponse.fail("请选择所属校区");
            }
            if ("part".equals(belongCampus) && null == partCampus) {
                return APIBaseResponse.fail("请选择所属校区");
            }
            if ("part".equals(belongCampus) && partCampus.length == 0) {
                return APIBaseResponse.fail("请选择所属校区");
            }
        }
        return APIBaseResponse.success();
    }
}
