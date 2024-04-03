package com.study.business.sys.admin.domain.req;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-02-06 11:45
 */
@Data
public class ReqUpdateUserRole implements Serializable {

    private String userId;

    private String tenantId;

    /**
     * 角色Id
     */
    @TableField(exist = false)
    private Long[] roleIds;
}
