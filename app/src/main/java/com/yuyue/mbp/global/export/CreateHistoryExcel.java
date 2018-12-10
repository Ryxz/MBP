package com.yuyue.mbp.global.export;

import android.content.res.Resources;
import android.util.Log;

import com.yuyue.mbp.R;
import com.yuyue.mbp.entity.BPMeasurement;
import com.yuyue.mbp.global.GlobalConstant;
import com.yuyue.mbp.global.event.Event;
import com.yuyue.mbp.global.utils.DateUtil;
import com.yuyue.mbp.global.utils.FileManager;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 到处历史数据到Excel
 * Created by Chen on 14-11-19.
 */
public class CreateHistoryExcel implements Runnable {

    private static final String TAG = CreateHistoryExcel.class.getSimpleName();

    private final IExport export;
    private final Resources mResources;
    private final String fileName;
    private final List<BPMeasurement> data;

    public CreateHistoryExcel(IExport export, Resources mResources, List<BPMeasurement> data) {
        this.export = export;
        this.mResources = mResources;
        this.data = data;
        this.fileName = FileManager.getExportDir(FileManager.HISTORY) + File.separator + "export_template_"
               + DateUtil.formatCustomDate(new Date(), "yyyyMMddHHmmss") + ".xls";
    }

    @Override
    public void run() {
        try {
            WritableWorkbook excel = Workbook.createWorkbook(new File(fileName),
                    Workbook.getWorkbook(mResources.openRawResource(R.raw.export_template)));

            WritableSheet sheet = excel.getSheet(0);
            export.exportHistoryListToExcel(sheet,data);
            excel.write();
            excel.close();
            Event event = Event.getEvent(GlobalConstant.Event.EXPORT_SUCCESS);
            event.obj = fileName;
            EventBus.getDefault().post(event);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            EventBus.getDefault().post(Event.getEvent(GlobalConstant.Event.EXPORT_FAILED));
        } catch (BiffException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            EventBus.getDefault().post(Event.getEvent(GlobalConstant.Event.EXPORT_FAILED));
        } catch (WriteException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            EventBus.getDefault().post(Event.getEvent(GlobalConstant.Event.EXPORT_FAILED));
        }
    }
}
