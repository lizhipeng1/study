package com.study.base.business.sc.order.domain.req;

import cn.xluobo.business.sc.order.repo.model.ScOrder;
import cn.xluobo.core.page.ReqPageBase;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 17:30
 */
@Data
public class ReqSearchScOrder extends ReqPageBase implements Serializable {

    // 学员
    private Long studentId;

    // 经办日期
    private String[] handleDate;

    private String handleDateBegin;
    private String handleDateEnd;

    // 是否欠费
    private Boolean arrears;

    // 校区
    private Long deptId;

    // 课程
    private Long courseId;

    // 销售员工
    private Long saleStaffId;

    // 订单类型
    private String orderType;

    // 订单状态
    private String orderStatus;

    // 经办人
    private Long createUser;

    //  经办校区
    private Long handleDeptId;

    // 销售来源
    private String saleSourceTag;

    public void setHandleDate(String[] handleDate) {
        this.handleDate = handleDate;
        if (null != handleDate && handleDate.length == 2 && StringUtils.isAnyEmpty(handleDateBegin, handleDateEnd)) {
            this.handleDateBegin = handleDate[0];
            this.handleDateEnd = handleDate[1];
        }
    }

}
