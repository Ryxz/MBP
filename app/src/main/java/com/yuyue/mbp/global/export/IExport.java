package com.yuyue.mbp.global.export;


import com.yuyue.mbp.entity.BPMeasurement;

import java.util.List;

import jxl.write.WritableSheet;
import jxl.write.WriteException;

/**
 * 输出接口
 * Created by Chen on 14-11-17.
 */
public interface IExport {

//    public void exportDailyListToExcel(WritableSheet sheet, List<BPMeasurement> data) throws WriteException;

//    public void exportDailyListToPdf(InputStream is, ByteArrayOutputStream[] baos, int pages,
//                                     String hospitalName, List<DailyStatisticsBean> data) throws IOException, DocumentException;

    public void exportHistoryListToExcel(WritableSheet sheet, List<BPMeasurement> data) throws WriteException;

//    public void exportHistoryListToPdf(InputStream is, ByteArrayOutputStream[] baos, int pages,
//                                       String hospitalName, Patient patient, List<DailyStatistics> data) throws IOException, DocumentException;

//    void exportHistoryListToExcel(WritableSheet sheet, String hospitalName,
//                                  Patient patient, List<DailyStatistics> data) throws WriteException;

    public int getHistoryPerPageCount();

//    public int getDailyPerPageCount();
}
