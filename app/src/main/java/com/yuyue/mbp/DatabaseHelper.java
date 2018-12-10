package com.yuyue.mbp;

import android.util.Log;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DaoConfig;
import com.lidroid.xutils.exception.DbException;
import com.yuyue.mbp.dao.BPMeasurementDAO;
import com.yuyue.mbp.entity.BPMeasurement;
import com.yuyue.mbp.global.GlobalContext;
import com.yuyue.mbp.global.utils.annotation.Resource;
import java.util.List;

/**
 * Created by Renyx on 2018/6/13
 */
public class DatabaseHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DB_NAME = "mbp.db";

    private static DbUtils db = null;

    private static final int VERSION = 2;

    private static DatabaseHelper singleton = null;

    @Resource(name = "BPMeasurementDAO")
    private static BPMeasurementDAO mBPMeasurementDAO = null;

    public static synchronized DatabaseHelper getInstance() {

        if (db == null) {
            singleton = new DatabaseHelper();

            DaoConfig daoConfig = new DaoConfig(GlobalContext.getInstance());
            daoConfig.setDbName(DB_NAME);
            daoConfig.setDbVersion(VERSION);

            daoConfig.setDbUpgradeListener(new DbUtils.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbUtils dbUtils, int i, int i1) {
                    try {
                        if (i<2) {
                            dbUtils.execNonQuery("ALTER TABLE tb_measurement ADD COLUMN level TEXT");
//                            dbUtils.execNonQuery("DELETE FROM tb_measurement");
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            });
            db = DbUtils.create(daoConfig);
            db.configAllowTransaction(true);
            db.configDebug(BuildConfig.DEBUG);
            mBPMeasurementDAO = new BPMeasurementDAO(db);
        }
        return singleton;
    }
    /**
     * 查询最近一次测量
     * @return
     */
    public BPMeasurement getLastMeasurement ( ) {
        return  mBPMeasurementDAO.getMeasurement();
    }

    /**
     * 查询倒数第二次测量
     * @return
     */
    public BPMeasurement getFirstMeasurement() {
        return mBPMeasurementDAO.getMeasurementFirst();
    }

    /**
     * 最近一次测量和上一次测量sbp比较
     * @return
     */
    public int getDataByLastMeasureAndMeasure () {
        int data = 0;
        BPMeasurement lastMeasurement = getLastMeasurement();
        BPMeasurement firstMeasurement = getFirstMeasurement();

        if (lastMeasurement != null && firstMeasurement != null) {
            int lData = lastMeasurement.getSbp();
            int fData = firstMeasurement.getSbp();
            data = lData - fData;
        }
        return data;
    }
    public BPMeasurementDAO getmBPMeasurementDAO() {
        return mBPMeasurementDAO;
    }

    public static String getDbName() {
        return DB_NAME;
    }

    public void runRawSQL(String sql) {
        try {
            db.execNonQuery(sql);
        } catch (DbException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }
    private void beginTransaction() {
        db.configAllowTransaction(false);
        db.getDatabase().beginTransaction();
    }
    private void endTransaction() {
        db.getDatabase().endTransaction();
        db.configAllowTransaction(true);
    }
    public void setBPMeasurementDAO(BPMeasurementDAO mBPMeasurementDAO) {
        this.mBPMeasurementDAO = mBPMeasurementDAO;
    }
    public boolean saveBPMeasurement(BPMeasurement measurement) {
        return mBPMeasurementDAO.saveOrUpdate(measurement);
    }
    public  BPMeasurement getBPMeasurement() {
            return mBPMeasurementDAO.getMeasurement();
    }

    public List<BPMeasurement> getBPMeasurementList() {
        return mBPMeasurementDAO.getListMeasurement();
    }

    public int getCount(String level) {
        return mBPMeasurementDAO.getCountBySbp(level);
    }

    public int getMeasureTotalCount() {
        return mBPMeasurementDAO.getCountTotalTimes();
    }




}
