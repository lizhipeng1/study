package com.study.base.business.sys.admin.domain.req;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-02-06 11:45
 */
@Data
public class ReqUpdateUserTenant implements Serializable {

    private String userId;

    private String[] tenantIds;

}
