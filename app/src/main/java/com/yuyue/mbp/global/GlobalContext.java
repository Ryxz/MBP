package com.yuyue.mbp.global;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import com.yuyue.mbp.DatabaseHelper;
import com.yuyue.mbp.global.export.ExportUtil;


/**
 * Created by Renyx on 2018/6/7
 */
public class  GlobalContext extends Application{
    private static GlobalContext globalContext = null;
    private Activity activity = null;
    private static DatabaseHelper mDatabaseHelper = null;
    private Handler handler = new Handler();
    private static ExportUtil mExportUtil = null;

    @Override
    public void onCreate() {
        super.onCreate();
        globalContext = this;
        mDatabaseHelper = DatabaseHelper.getInstance();
        mExportUtil = ExportUtil.getInstance();
    }

    public static DatabaseHelper getDatabase() {
        return mDatabaseHelper;
    }

    public Handler getUIHandler() {
        return handler;
    }

    public static ExportUtil getExportUtil() {
        return mExportUtil;
    }

    public static GlobalContext getInstance() {
        return globalContext;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

}
