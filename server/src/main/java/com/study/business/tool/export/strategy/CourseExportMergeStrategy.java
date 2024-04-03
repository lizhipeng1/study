package com.study.business.tool.export.strategy;

import com.study.business.tool.export.domain.ExportBaseBean;
import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * 导出合并单元格策略
 * 一对多 合并时需要
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/7/30 13:37
 */
public class CourseExportMergeStrategy<T extends ExportBaseBean> extends AbstractRowWriteHandler {

    private List<T> exportBeanList;

    private int startColumn = 0;

    private int endColumn = 0;

    public CourseExportMergeStrategy(List<T> exportBeanList, int startColumn, int endColumn) {
        this.exportBeanList = exportBeanList;
        this.startColumn = startColumn;
        this.endColumn = endColumn;
    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
        if (exportBeanList.isEmpty() || startColumn < 0 || endColumn < 0){
            return;
        }
        if(isHead){
            return;
        }
        if(relativeRowIndex<exportBeanList.size()-1){
            return;
        }
        // 从第几个数据开始计算
        int index = 0;
        // 头占几行
        int headRows = 1;
        while (index < exportBeanList.size()) {
            String primaryId = exportBeanList.get(index).getPrimaryId();

            int firstRow = index;
            int endRow = index;

            for (int i = index + 1; i < exportBeanList.size(); i++) {
                String tempPrimaryId = exportBeanList.get(i).getPrimaryId();
                if(primaryId.equals(tempPrimaryId)){
                    endRow++;
                }else {
                    break;
                }
            }
            index = endRow + 1;
            if(endRow > firstRow) {
                while (startColumn <= endColumn) {
                    writeSheetHolder.getSheet().addMergedRegionUnsafe(new CellRangeAddress(firstRow + headRows, endRow + headRows, startColumn, startColumn));
                    startColumn ++;
                }
                startColumn = 0;
            }
        }
    }
}
