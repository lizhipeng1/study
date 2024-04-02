package com.study.base.business.sys.log.repo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 操作日志记录
 * </p>
 *
 * @author zhangby
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_operate_log")
public class SysOperateLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作模块
     */
    @TableId(value = "operate_id", type = IdType.ASSIGN_ID)
    private String operateId;

    /**
     * 操作模块
     */
    @TableField("operate_module")
    private String operateModule;

    /**
     * 业务类型
     */
    @TableField("business_type")
    private String businessType;

    /**
     * 方法名称
     */
    @TableField("method")
    private String method;

    /**
     * 请求方式
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 客户端 pc app
     */
    @TableField("client_type")
    private String clientType;

    /**
     * 操作人员
     */
    @TableField("user_id")
    private String userId;

    /**
     * 操作人员
     */
    @TableField("user_name")
    private String userName;

    /**
     * 请求URL
     */
    @TableField("operate_url")
    private String operateUrl;

    /**
     * 主机地址
     */
    @TableField("operate_ip")
    private String operateIp;

    /**
     * 操作地点
     */
    @TableField("operate_location")
    private String operateLocation;

    /**
     * 请求参数
     */
    @TableField("req_param")
    private String reqParam;

    /**
     * 返回参数
     */
    @TableField("resp_result")
    private String respResult;

    /**
     * 是否异常（0正常 1异常）
     */
    @TableField("error")
    private Integer error;

    /**
     * 错误消息
     */
    @TableField("error_msg")
    private String errorMsg;

    /**
     * 操作时间
     */
    @TableField("operate_time")
    private Date operateTime;


}
