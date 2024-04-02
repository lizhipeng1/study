package com.study.base.business.sys.staff.repo.mapper;

import cn.xluobo.business.sys.staff.domain.req.ReqSearchStaff;
import cn.xluobo.business.sys.staff.domain.resp.RespStaffInfo;
import cn.xluobo.business.sys.staff.repo.model.SysStaff;
import cn.xluobo.core.page.RespPage;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 教师信息 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2020-03-17 11:27:37
 */
public interface SysStaffMapper extends BaseMapper<SysStaff> {

    /**
     * 查询员工列表
     * @param reqSearchStaff
     * @param page
     * @return
     */
    List<RespStaffInfo> selectStaffList(@Param("reqSearchStaff") ReqSearchStaff reqSearchStaff, @Param("page") RespPage page);

    /**
     * 租户下员工数量
     * @param tenantId
     * @return
     */
    @SqlParser(filter = true)
    Integer selectTenantStaffCount(String tenantId);
}
