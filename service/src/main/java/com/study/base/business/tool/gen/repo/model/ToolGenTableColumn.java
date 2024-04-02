package com.study.base.business.tool.gen.repo.model;

import cn.xluobo.business.tool.gen.enums.HtmlTypeEnum;
import cn.xluobo.business.tool.gen.enums.QueryTypeEnum;
import cn.xluobo.config.properties.GenCodeConfig;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 代码生成表字段
 * </p>
 *
 * @author zhangby
 * @since 2020-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tool_gen_table_column")
public class ToolGenTableColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "column_id", type = IdType.AUTO)
    private Long columnId;

    /**
     * 归属表编号
     */
    @TableField("table_id")
    private Long tableId;

    /**
     * 列名称
     */
    @TableField("column_name")
    private String columnName;

    /**
     * 列描述
     */
    @TableField("column_comment")
    private String columnComment;

    /**
     * 列类型
     */
    @TableField("column_type")
    private String columnType;

    /**
     * JAVA类型
     */
    @TableField("java_type")
    private String javaType;

    /**
     * JAVA字段名
     */
    @TableField("java_field")
    private String javaField;

    /**
     * 是否主键（1是）
     */
    @TableField("is_pk")
    private String isPk;

    /**
     * 是否自增（1是）
     */
    @TableField("is_increment")
    private String isIncrement;

    /**
     * 是否必填（1是）
     */
    @TableField("is_required")
    private String isRequired;

    /**
     * 是否为插入字段（1是）
     */
    @TableField("is_insert")
    private String isInsert;

    /**
     * 是否编辑字段（1是）
     */
    @TableField("is_edit")
    private String isEdit;

    /**
     * 是否列表字段（1是）
     */
    @TableField("is_list")
    private String isList;

    /**
     * 是否查询字段（1是）
     */
    @TableField("is_query")
    private String isQuery;

    /**
     * 查询方式（等于、不等于、大于、小于、范围）
     */
    @TableField("query_type")
    private String queryType;

    /**
     * 显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）
     */
    @TableField("html_type")
    private String htmlType;

    /**
     * 字典类型
     */
    @TableField("dict_type")
    private String dictType;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

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

    public ToolGenTableColumn dealIsInsert() {
        this.isInsert = "1";
        return this;
    }

    public ToolGenTableColumn dealIsEdit() {
        this.isEdit = "0";
        if (!GenCodeConfig.notEditList.contains(this.columnName) && "0".equals(this.isPk)) {
            this.isEdit = "1";
        }
        return this;
    }

    public ToolGenTableColumn dealIsList() {
        this.isList = "0";
        if (!GenCodeConfig.notTableList.contains(this.columnName) && "0".equals(this.isPk)) {
            this.isList = "1";
        }
        return this;
    }

    public ToolGenTableColumn dealIsQuery() {
        this.isQuery = "0";
        if (!GenCodeConfig.notQueryList.contains(this.columnName) && "0".equals(this.isPk)) {
            this.isQuery = "1";
        }
        return this;
    }

    public ToolGenTableColumn dealQueryType() {
        if (StringUtils.endsWithIgnoreCase(columnName, "name")) {
            this.queryType = QueryTypeEnum.LIKE.name();
        } else if (StringUtils.endsWithIgnoreCase(columnName, "time")) {
            this.queryType = QueryTypeEnum.BETWEEN.name();
        } else if (StringUtils.endsWithIgnoreCase(columnName, "date")) {
            this.queryType = QueryTypeEnum.BETWEEN.name();
        } else {
            this.queryType = QueryTypeEnum.EQ.name();
        }
        return this;
    }

    public ToolGenTableColumn dealHtmlType() {
        if (StringUtils.endsWithIgnoreCase(columnName, "time")) {
            this.htmlType = HtmlTypeEnum.datetime.name();
        } else if (StringUtils.endsWithIgnoreCase(columnName, "date")) {
            this.htmlType = HtmlTypeEnum.datetime.name();
        } else if (StringUtils.endsWithIgnoreCase(columnName, "type")) {
            this.htmlType = HtmlTypeEnum.select.name();
        } else if (GenCodeConfig.htmlRadioTypeList.contains(columnName)) {
            this.htmlType = HtmlTypeEnum.select.name();
        } else if (GenCodeConfig.htmlTextAreaTypeList.contains(columnName)) {
            this.htmlType = HtmlTypeEnum.textarea.name();
        } else {
            this.htmlType = HtmlTypeEnum.input.name();
        }
        return this;
    }
}
