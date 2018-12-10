package com.yuyue.mbp.global.export;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;


import com.yuyue.mbp.entity.BPMeasurement;
import com.yuyue.mbp.global.GlobalContext;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 导出工具类
 * Created by Renyx on 18-7-5.
 */
public class ExportUtil {

    private static final String TAG = ExportUtil.class.getSimpleName();
    private static ExportUtil exportUtil = null;

    /**
     * 用于保存等待执行的任务的阻塞队列。(有序的先进先出阻塞队列)
     */
    private static ArrayBlockingQueue<Runnable> mBlockingQueue = new ArrayBlockingQueue<Runnable>(15, true);

    /**
     * 线程池
     */
    private static AbstractExecutorService mThreadPoolExecutor = new ThreadPoolExecutor(5, 7, 10, TimeUnit.SECONDS,
            mBlockingQueue, new ThreadPoolExecutor.DiscardOldestPolicy());
    private IExport export;
    private Class<IExport> implClass;
    private Resources mResources;

    public static ExportUtil getInstance() {
        if (exportUtil == null) {
            exportUtil = new ExportUtil();
        }
        return exportUtil;
    }

    private ExportUtil() {
        try {
            implClass = (Class<IExport>) Class.forName("com.yuyue.mbp.global.export.impl.JSATCMExport");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        mResources = GlobalContext.getInstance().getResources();
        try {
            if (implClass != null) {
                export = implClass.newInstance();
            }
        } catch (InstantiationException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    public void exportHistoryToExcel(List<BPMeasurement> data) {
        mThreadPoolExecutor.execute(new CreateHistoryExcel(export, mResources, data));
    }

    public void shutdown() {
        if (mThreadPoolExecutor != null) {
            mThreadPoolExecutor.shutdown();
            Log.i(TAG, "ThreadPool shutdown!");
        }
    }

}
