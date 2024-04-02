package com.study.core.business.sc.order.domain.req;

import cn.xluobo.core.api.APIResponse;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 新办
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/3 15:56
 */
@Data
public class ReqBusinessSignUp {

    private Long studentId;

    // 经办校区
    private Long handleDepartId;

    // 是否使用余额支付
    private boolean useBalancePay;
    // 余额支付金额
    private BigDecimal balancePayValue;

    // 订单标签
    private String[] orderTag;

    // 销售员工
    private Long saleStaffId;

    // 经办日期
    private String handleDate;

    // 办理备注
    private String memo;

    // 销售来源
    private String[] saleSourceTag;

    // 实收金额
    private BigDecimal receiptWaySumMoney;

    // 应收金额
    private BigDecimal needReceiptFee;

    // 报读课程
    List<ReqBusinessSignUpItem> signUpItemList;

    // 收款方式
    List<ReqBusinessSignUpReceipt> signUpReceiptList;

    public APIResponse checkParam() {
        if (null == signUpItemList || signUpItemList.size() == 0) {
            return APIResponse.toExceptionResponse("请选择报读课程");
        } else if (null == studentId) {
            return APIResponse.toExceptionResponse("请选择学生");
        } else if (null == handleDepartId) {
            return APIResponse.toExceptionResponse("请选择经办校区");
        } else if (useBalancePay && null == balancePayValue) {
            return APIResponse.toExceptionResponse("余额支付时,请填写余额支付金额");
        } else if (null == needReceiptFee) {
            return APIResponse.toExceptionResponse("无应收金额");
        } else if (null == receiptWaySumMoney) {
            return APIResponse.toExceptionResponse("未填写实收金额");
        }
        for (ReqBusinessSignUpItem item : signUpItemList) {
            if(null == item.getCourseId()) {
                return APIResponse.toExceptionResponse("请选择课程");
            } else if (null == item.getDepartId()) {
                return APIResponse.toExceptionResponse("无法获取报读课程所属校区,请重试");
            }  else if (null == item.getChargeId()) {
                return APIResponse.toExceptionResponse("请选择收费方式");
            } else if (StringUtils.isEmpty(item.getChargeType())) {
                return APIResponse.toExceptionResponse("请选择收费方式");
            } else if (null == item.getBuyCount() || 0 == item.getBuyCount()) {
                return APIResponse.toExceptionResponse("请填写购买数量");
            } else if (item.isOpenExpire() && null == item.getExpireDate()) {
                return APIResponse.toExceptionResponse("设置有效期但未填写失效时间");
            } else if ("date".equals(item.getChargeType()) && null == item.getBeginDate()) {
                return APIResponse.toExceptionResponse("按时间收费 但未填写生效时间");
            } else if ("date".equals(item.getChargeType()) && null == item.getEndDate()) {
                return APIResponse.toExceptionResponse("按时间收费 但未填写失效时间");
            } else if (item.isOpenDiscount() && null == item.getDiscount()) {
                return APIResponse.toExceptionResponse("启学费折扣,但未填写折扣");
            } else if (item.isOpenDiscountFee() && null == item.getDiscountFee()) {
                return APIResponse.toExceptionResponse("启学费折扣,但未填写优惠金额");
            }
        }
        for (ReqBusinessSignUpReceipt signUpReceipt : signUpReceiptList) {
            if(null == signUpReceipt.getAccountId()){
                return APIResponse.toExceptionResponse("收款方式未填写,请选择填写已添加的收款方式");
            } else if(null == signUpReceipt.getReceiptMoney()) {
                return APIResponse.toExceptionResponse("请填写已选择收款方式的收款金额");
            }
        }
        return APIResponse.toOkResponse();
    }
}
