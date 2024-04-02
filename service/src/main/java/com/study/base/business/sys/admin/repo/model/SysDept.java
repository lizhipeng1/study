package com.study.base.business.sys.admin.repo.model;

import com.baomidou.mybatisplus.annotation.*;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author zhangby
 * @since 2020-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dept")
public class SysDept implements Serializable {

    private static final long serialVersionUID = 1L;

    public SysDept() {
    }

    public SysDept(Long deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }

    /**
     * 部门id
     */
    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long deptId;

    /**
     * 父部门id
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 部门类型 1部门 2校区
     */
    @TableField("dept_type")
    private String deptType;

    /**
     * 祖级列表
     */
    @TableField("ancestors")
    private String ancestors;

    /**
     * 部门名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 负责人
     */
    @TableField("leader")
    private String leader;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 显示顺序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 部门状态（1正常 0停用）
     */
    @TableField("in_use")
    private String inUse;

    /**
     * 删除标志（1删除 0在用）
     */
    @TableField("delete_flag")
    @TableLogic
    private String deleteFlag;

    /**
     * 创建者
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField("last_update_user")
    private String lastUpdateUser;

    /**
     * 更新时间
     */
    @TableField("last_update_time")
    private Date lastUpdateTime;


    @TableField(exist = false)
    private List<SysDept> children;

    //当前校区
    @TableField(exist = false)
    private boolean nowDept = false;

    private static Map<Long, List<SysDept>> subTreeMap = new HashMap<>();


    /**
     * 树转换
     * @param sysDeptList
     */
    public void converterTree(List<SysDept> sysDeptList) {
        subTreeMap.clear();
        for (SysDept menu : sysDeptList) {
            List<SysDept> subTreeList = subTreeMap.get(menu.getParentId());
            if (null == subTreeList) {
                subTreeList = new LinkedList<>();
                subTreeMap.put(menu.getParentId(),subTreeList);
            }
            subTreeList.add(menu);
        }

        this.children = treeDepth(this).getChildren();
        if(this.children.isEmpty()){
            this.children = sysDeptList;
        }
    }

    /**
     * 深度优先 遍历树
     * @param sysDept
     * @return
     */
    private SysDept treeDepth(SysDept sysDept) {
        Long deptId = sysDept.getDeptId();
        List<SysDept> childMenuList = subTreeMap.get(deptId);
        if(null != childMenuList){
            List<SysDept> childList = Lists.newLinkedList();
            for (SysDept childMenu : childMenuList) {
                SysDept child = treeDepth(childMenu);
                childList.add(child);
            }
            sysDept.setChildren(childList);
            return sysDept;
        }else{
            sysDept.setChildren(Lists.newLinkedList());
            return sysDept;
        }
    }
}
