package com.study.base.business.sys.admin.repo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zhangby
 * @since 2020-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    private String userId;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email_address")
    private String emailAddress;

    @TableField("enable")
    private String enable;

    @TableField("locked")
    private String locked;

    @TableField("account_expired")
    private String accountExpired;

    @TableField("pwd_expired")
    private String pwdExpired;

    /**
     * 微信用户 id
     */
    @TableField("wechat_user_id")
    private Long wechatUserId;

    /**
     * 所属部门
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 用户头像
     */
    @TableField("avatar_img")
    private String avatarImg;

    @TableField("create_date")
    private Date createDate;

    @TableField(exist = false)
    private String deptName;
    @TableField(exist = false)
    private String tenantNames;
}
