package com.study.business.tool.export.handler;

import com.study.business.tool.export.handler.bean.SelectValidationData;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.google.common.collect.Maps;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.List;
import java.util.Map;

/**
 * 下拉菜单
 *
 * @author ：zhangbaoyu
 * @date ：Created in 2020/8/2 18:23
 */
public class SelectWriteHandler implements SheetWriteHandler {

    /**
     * 单元格 下拉配置
     */
    private List<SelectValidationData> validationDataList;

    public SelectWriteHandler(List<SelectValidationData> validationDataList) {
        this.validationDataList = validationDataList;
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        if (null != validationDataList && validationDataList.size() > 0) {

            Map<Integer, Integer> createRowCacheMap = Maps.newHashMap();

            Sheet shtDictionary = writeWorkbookHolder.getWorkbook().createSheet("ShtDictionary");
            for (SelectValidationData selectValidationData : validationDataList) {
                String[] selectDataArray = selectValidationData.getSelectDataArray();
                if(selectDataArray.length == 0) {
                    continue;
                }
                int firstCol = selectValidationData.getFirstCol();
                for (int i = 0; i < selectDataArray.length; i++) {
                    if (createRowCacheMap.containsKey(i)) {
                        shtDictionary.getRow(i).createCell(firstCol).setCellValue(selectDataArray[i]);
                    } else {
                        shtDictionary.createRow(i).createCell(firstCol).setCellValue(selectDataArray[i]);
                        createRowCacheMap.put(i,i);
                    }
                }
                CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(selectValidationData.getFirstRow(), selectValidationData.getLastRow(), selectValidationData.getFirstCol(), selectValidationData.getLastCol());

                String letterIndex = String.valueOf((char) (65 + firstCol));
                String formula = "ShtDictionary!$" + letterIndex + "$1:$" + letterIndex + "$" + selectDataArray.length;
                DataValidationHelper dvHelper = writeWorkbookHolder.getWorkbook().getSheet("ShtDictionary").getDataValidationHelper();
                DataValidationConstraint formulaListConstraint = dvHelper.createFormulaListConstraint(formula);
                DataValidation columnValidation = dvHelper.createValidation(formulaListConstraint, cellRangeAddressList);
                writeSheetHolder.getSheet().addValidationData(columnValidation);
            }
            writeWorkbookHolder.getWorkbook().setSheetHidden(writeWorkbookHolder.getWorkbook().getSheetIndex("ShtDictionary"), true);

        }
    }
}
