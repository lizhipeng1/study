package com.study.business.sys.admin.repo.model;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author zhangby
 * @since 2020-03-01 10:09:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_file")
public class SysFile implements Serializable {


    @TableId(value = "file_id", type = IdType.ASSIGN_ID)
    private String fileId;

    /**
     * 所属父键
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 关联表主键
     */
    @TableField("reference_id")
    private String referenceId;

    /**
     * 关联表
     */
    @TableField("reference_table")
    private String referenceTable;

    /**
     * 存储路径前缀
     */
    @TableField("pre_path")
    private String prePath;

    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件逻辑名称
     */
    @TableField("logical_name")
    private String logicalName;

    /**
     * 文件大小
     */
    @TableField("file_size")
    private String fileSize;

    /**
     * 文件后缀
     */
    @TableField("file_suffix")
    private String fileSuffix;

    /**
     * 文件大类
     */
    @TableField("file_p_type")
    private String filePType;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;

    @TableField("enable")
    private String enable;

    /**
     * 批量上传时的序号
     */
    @TableField(exist = false)
    private String fileIndex;
}
