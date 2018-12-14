package com.yuyue.mbp.dao;

import android.database.Cursor;
import android.util.Log;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.yuyue.mbp.entity.BPMeasurement;
import com.yuyue.mbp.global.utils.CommonUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by Renyx on 2018/6/13
 */
public class BPMeasurementDAO  extends BaseDAO<BPMeasurement> {
    public BPMeasurementDAO(DbUtils db) {
        super(db);
        this.TAG = BPMeasurementDAO.class.getSimpleName();
//        createMeasurement();
//        deleteMeasurement();
    }

    /**
     * 查询血压测量记录list
     *
     * @param
     */
    @SuppressWarnings("unchecked")
    public List<BPMeasurement> getListMeasurement() {
        StringBuilder sql = new StringBuilder("SELECT DISTINCT * FROM tb_measurement  ORDER BY measureTime DESC");
        return getList(sql.toString());
    }

    /**
     * 查询最后一次血压测量记录
     *
     * @param
     * @return
     */
    public BPMeasurement getMeasurement() {
        Selector selector =getSelector();
        selector.orderBy(BPMeasurement.MEASURE_TIME, true);
        selector.limit(1).offset(0);//从1行开始查，查1行

        BPMeasurement measurement = null;

        try {
                measurement = db.findFirst(selector);
        } catch (DbException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

        return  measurement;
    }
    /**
     * 查询倒数第二次测量记录
     * @return
     */
    public BPMeasurement getMeasurementFirst() {
        BPMeasurement measurement = null;
        Selector selector = getSelector();
        selector.orderBy("measureTime",true);
        selector.limit(1).offset(1);//从2行开始查，查1行
        try {
                measurement = db.findFirst(selector);

        } catch (DbException e) {
            e.printStackTrace();
        }
        return measurement;
    }
    /**
     * 查询总次数
     * @return
     */
    public int getCountTotalTimes() {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM tb_measurement");

        int data = 0;
        Cursor cursor;
            try {
                cursor = db.execQuery(sql.toString());
                if (cursor != null) {
                    cursor.moveToFirst();
                    data = cursor.getInt(0);
                }
            } catch (DbException e) {
                e.printStackTrace();
            }

        return data;
    }

    /**
     * 查询不同血压等级的次数
     * @return
     */
    public int getCountBySbp(String level) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM tb_measurement WHERE level =  '" + level + "' ");
        int data = 0;
        Cursor cursor;
        try {
            cursor = db.execQuery(sql.toString());
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    data = cursor.getInt(0);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return data;
    }


    @Override
    public boolean saveOrUpdate(BPMeasurement measurement) {
        boolean flag = false;
        try {
            db.saveOrUpdate(measurement);
            flag = true;
        } catch (DbException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }


//    private void createMeasurement() {
//            BPMeasurement measurement = new BPMeasurement();
//            int sbp = 200;
//            int dbp = 99;
//            int rate = 80;
//            for (int j = 0;j<5;j++) {
//                sbp=sbp+-5;
//                dbp = dbp-4;
//                rate = rate -3;
//                for (int i=0;i<20;i++) {
//
//                }
//            }
//        measurement.setSbp(118);
//        measurement.setDbp(66);
//        measurement.setMeasureTime(new Date());
//        measurement.setPulseRate(60);
//        measurement.setLevel(CommonUtil.getPressureLevel(measurement.getSbp(),measurement.getDbp()));
//        try {
//            db.save(measurement);
//        } catch (DbException e) {
//            Log.d(TAG, e.getMessage());
//            e.printStackTrace();
//        }


//    }

}