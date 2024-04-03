package com.study.business.tool.gen.repo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 代码生成表
 * </p>
 *
 * @author zhangby
 * @since 2020-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tool_gen_table")
public class ToolGenTable implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "table_id", type = IdType.AUTO)
    private Long tableId;

    /**
     * 表名称
     */
    @TableField("table_name")
    private String tableName;

    /**
     * 表描述
     */
    @TableField("table_comment")
    private String tableComment;

    /**
     * 实体类名称
     */
    @TableField("class_name")
    private String className;

    /**
     * 使用的模板（curd单表操作 tree树表操作）
     */
    @TableField("tpl_category")
    private String tplCategory;

    /**
     * 生成包路径 如 com.study.business，最终路径 包路径+表前缀+模块名
     */
    @TableField("package_name")
    private String packageName;

    /**
     * 生成模块名 如 sys
     */
    @TableField("module_name")
    private String moduleName;

    /**
     * 生成子模块名 如 admin
     */
    @TableField("child_module_name")
    private String childModuleName;

    /**
     * 生成业务名 如 学生管理
     */
    @TableField("business_name")
    private String businessName;

    /**
     * 树键  如deptId
     */
    @TableField("tree_code")
    private String treeCode;

    /**
     * 树父键  如parentId
     */
    @TableField("tree_parent_code")
    private String treeParentCode;

    /**
     * 树展示名称 deptName
     */
    @TableField("tree_name")
    private String treeName;

    /**
     * 作者
     */
    @TableField("code_author")
    private String codeAuthor;

    /**
     * 备注
     */
    @TableField("memo")
    private String memo;

    /**
     * 其它生成选项 json格式
     */
    @TableField("options")
    private String options;

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

    /**
     * 表字段
     */
    @TableField(exist = false)
    private List<ToolGenTableColumn> columns;

}
