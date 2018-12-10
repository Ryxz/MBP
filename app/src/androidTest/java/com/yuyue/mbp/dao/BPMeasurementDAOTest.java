package com.yuyue.mbp.dao;

import com.yuyue.mbp.DatabaseHelper;
import com.yuyue.mbp.entity.BPMeasurement;
import com.yuyue.mbp.global.GlobalContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Renyx on 2018/6/27
 */


public class BPMeasurementDAOTest {

    private static final String TAG = BPMeasurementDAOTest.class.getSimpleName();
    private DatabaseHelper mDatabaseHelper;

    @Before
    public void setUp() throws Exception {
        mDatabaseHelper = GlobalContext.getDatabase();
    }
    public void getMeasureResult() {
        List<BPMeasurement> list = new ArrayList<BPMeasurement>();
            Assert.assertTrue(list.size()>0);
            BPMeasurement measurement = new BPMeasurement();
            measurement.setSbp(180);
            measurement.setDbp(80);
            measurement.setPulseRate(62);
            measurement.setMeasureTime(new
                    Date());
            mDatabaseHelper.saveBPMeasurement(measurement);
    }


}