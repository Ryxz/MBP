package com.yuyue.mbp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.yuyue.mbp.global.utils.annotation.SynchronizeTable;

import java.util.Date;

/**
 * 血压测量
 * Created by Renyx on 2018/6/13
 */
@Table(name = "tb_measurement")
@SynchronizeTable
public class  BPMeasurement implements Parcelable{

    public static final String TABLE_NAME = "tb_measurement";
    public static final String ID = "id";
    public static final String MEASURE_TIME = "measureTime";
    public static final String PRESSURE_LEVEL_TEXT_IDEAL= "Ideal";
    public static final String PRESSURE_LEVEL_TEXT_NORMAL = "Normal";
    public static final String PRESSURE_LEVEL_TEXT_EDGE = "Edge";
    public static final String PRESSURE_LEVEL_TEXT_LIGHT = "Light";
    public static final String PRESSURE_LEVEL_TEXT_MODERATE = "Moderate";
    public static final String PRESSURE_LEVEL_TEXT_SEVERE = "Severe";

    public static final String PRESSURE_LEVEL_IDEAL = "0";
    public static final String PRESSURE_LEVEL_NORMAL = "1";
    public static final String PRESSURE_LEVEL_EDGE = "2";
    public static final String PRESSURE_LEVEL_LIGHT = "3";
    public static final String PRESSURE_LEVEL_MODERATE = "4";
    public static final String PRESSURE_LEVEL_SEVERE= "5";


    @Id
    @Column(column = "id")
    private int id;

    @Column(column = "sbp")
    private int sbp;

    @Column(column = "dbp")
    private int dbp;

    @Column(column = "pulseRate")
    private int pulseRate;

    @Column(column = "measureTime")
    private Date measureTime;

    @Column(column = "level")
    private String level;

    private BPMeasurement measurement;

    public BPMeasurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(BPMeasurement measurement) {
        this.measurement = measurement;
    }

    public BPMeasurement (){}

    public BPMeasurement (int id) {
        setId(id);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getSbp() {
        return sbp;
    }

    public void setSbp(int sbp) {
        this.sbp = sbp;
    }

    public int getDbp() {
        return dbp;
    }

    public void setDbp(int dbp) {
        this.dbp = dbp;
    }

    public int getPulseRate() {
        return pulseRate;
    }

    public void setPulseRate(int pulseRate) {
        this.pulseRate = pulseRate;
    }

    public Date getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(Date measureTime) {
        this.measureTime = measureTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sbp);
        dest.writeInt(dbp);
        dest.writeInt(pulseRate);
        dest.writeInt(getId());
    }

    public static final Parcelable.Creator<BPMeasurement> CREATOR = new Creator<BPMeasurement>() {
        @Override
        public BPMeasurement createFromParcel(Parcel source) {
            BPMeasurement measurement = new BPMeasurement();
            measurement.setId(source.readInt());
            measurement.setSbp(source.readInt());
            measurement.setDbp(source.readInt());
            measurement.setPulseRate(source.readInt());
            return measurement;
        }

        @Override
        public BPMeasurement[] newArray(int size) {
            return new BPMeasurement[size];
        }
    };
}
