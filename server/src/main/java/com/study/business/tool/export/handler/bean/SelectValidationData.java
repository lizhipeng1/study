package com.study.business.tool.export.handler.bean;

import lombok.Builder;
import lombok.Data;

/**
 * 单元格增加下拉菜单 参数
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/2 18:46
 */
@Data
@Builder
public class SelectValidationData {

    /**
     * 从第几行开始
     */
    private int firstRow;

    /**
     * 到第几行
     */
    private int lastRow;

    /**
     * 第几列开始
     */
    private int firstCol;

    /**
     * 到第几列
     */
    private int lastCol;

    /**
     * 下拉菜单数据
     */
    private String[] selectDataArray;

    public SelectValidationData(int firstRow, int lastRow, int firstCol, int lastCol, String[] selectDataArray) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
        this.selectDataArray = selectDataArray;
    }
}
