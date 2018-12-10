package com.yuyue.mbp.global.export.impl;

import com.yuyue.mbp.entity.BPMeasurement;
import com.yuyue.mbp.global.export.IExport;
import com.yuyue.mbp.global.utils.DateUtil;

import java.util.List;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

/**
 * JSATCM导出实现
 * Created by Chen on 14-11-17.
 */
public class JSATCMExport implements IExport {

    private static final String TAG = JSATCMExport.class.getSimpleName();

    // 历史数据每页记录数
    public static final int HISTORY_PAGE_COUNT = 12;


    @Override
    public void exportHistoryListToExcel(WritableSheet sheet, List<BPMeasurement> data) throws WriteException {

        WritableCellFormat format = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"), 12));
        format.setBorder(Border.ALL, BorderLineStyle.THIN);
        format.setAlignment(Alignment.CENTRE);
        for (int i = 0; i < data.size(); i++) {
            BPMeasurement measurement = data.get(i);

            sheet.addCell(new Label(0,i+1, DateUtil.formatYMD(measurement.getMeasureTime()), format));
            setValueToCell(sheet,1,i+1,String.valueOf(measurement.getSbp()),format);
            setValueToCell(sheet,2,i+1,String.valueOf(measurement.getDbp()),format);
            setValueToCell(sheet,3,i+1,String.valueOf(measurement.getPulseRate()),format);
        }

    }




    @Override
    public int getHistoryPerPageCount() {
        return HISTORY_PAGE_COUNT;
    }

    /**
     * 将数值填充值单元格
     */
    private void setValueToCell(WritableSheet sheet, int column, int row,String value,
            WritableCellFormat format) throws WriteException {
        sheet.addCell(new Label(column,row,String.valueOf(value),format));



    }


}