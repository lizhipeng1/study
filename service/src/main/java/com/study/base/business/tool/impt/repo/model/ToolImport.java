package com.study.base.business.tool.impt.repo.model;

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
 * 导入
 * </p>
 *
 * @author zhangby
 * @since 2020-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tool_import")
public class ToolImport implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "import_id", type = IdType.AUTO)
    private Long importId;

    /**
     * 导入 类型
     */
    @TableField("import_type")
    private String importType;

    /**
     * 文件大小
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 导入结果
     */
    @TableField("import_result")
    private String importResult;

    /**
     * 导入结果说明
     */
    @TableField("result_memo")
    private String resultMemo;

    /**
     * 导入人
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 导入时间
     */
    @TableField("create_time")
    private Date createTime;


}
