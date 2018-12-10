package com.yuyue.mbp.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.yuyue.mbp.DatabaseHelper;
import com.yuyue.mbp.R;
import com.yuyue.mbp.entity.BPMeasurement;
import com.yuyue.mbp.global.GlobalContext;
import com.yuyue.mbp.global.utils.CommonUtil;

import java.util.List;

/**
 * Created by Renyx on 2018/7/3
 */
public class HomeFragment extends Fragment{
    private DatabaseHelper db;
    private TextView mMeasureResultState;
    private TextView mSbp;
    private TextView mUpSbp;
    private TextView mDbp;
    private TextView mPulseRate;
    private TextView mContrastHistory;
    private TextView mCount0;
    private TextView mCount1;
    private TextView mCount2;
    private TextView mCount3;
    private TextView mCount4;
    private TextView mCount5;
    private TextView totalTimes;
    private ImageView imageView;
    private List<BPMeasurement> mList;
    private BPMeasurement measurement;
    private  CircleProgressBar demo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = GlobalContext.getDatabase();
        measurement = db.getBPMeasurement();
        mList = db.getBPMeasurementList();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout,container,false);

        mContrastHistory = (TextView) view.findViewById(R.id.contrast_history);
        imageView = (ImageView) view.findViewById(R.id.contrast_image);

        mSbp = (TextView) view.findViewById(R.id.systolic);
        mDbp = (TextView) view.findViewById(R.id.diastolic);
        mPulseRate = (TextView) view.findViewById(R.id.pulse_min);

        mCount0 = (TextView) view.findViewById(R.id.count_times_ideal);
        mCount0.setTextColor(this.getResources().getColor(R.color.level_0));

        mCount1 = (TextView) view.findViewById(R.id.count_times_normal);
        mCount1.setTextColor(this.getResources().getColor(R.color.level_1));

        mCount2 = (TextView) view.findViewById(R.id.count_times_edge);
        mCount2.setTextColor(this.getResources().getColor(R.color.level_2));

        mCount3 = (TextView) view.findViewById(R.id.count_times_light);
        mCount3.setTextColor(this.getResources().getColor(R.color.level_3));

        mCount4 = (TextView) view.findViewById(R.id.count_times_moderate);
        mCount4.setTextColor(this.getResources().getColor(R.color.level_4));

        mCount5 = (TextView) view.findViewById(R.id.count_times_severe);
        mCount5.setTextColor(this.getResources().getColor(R.color.level_5));

        mMeasureResultState = (TextView) view.findViewById(R.id.measure_result_state);

        mUpSbp = (TextView) view.findViewById(R.id.up_systolic_result);
        totalTimes = (TextView) view.findViewById(R.id.total_times);

        demo = (CircleProgressBar) view.findViewById(R.id.circle_progressbar);
        demo.setMax(200);
        if (measurement != null) {
            mContrastHistory.setText(String.valueOf(getAbs()));
            mSbp.setText(String.valueOf(measurement.getSbp()));
            mDbp.setText(String.valueOf(measurement.getDbp()));
            mPulseRate.setText(String.valueOf(measurement.getPulseRate()));
            getLevelCount();
            mMeasureResultState.setText(CommonUtil.getLevelText(measurement.getLevel()));
            getTextLevelColor();
            mUpSbp.setText(String.valueOf(measurement.getSbp()));
            totalTimes.setText(String.valueOf(db.getMeasureTotalCount()));
            getImageUpOrDown();
            demo.setProgress(measurement.getSbp());
        } else {
            mSbp.setText("0");
            mDbp.setText("0");
            mPulseRate.setText("0");
            mCount5.setText("0");
            mCount4.setText("0");
            mCount3.setText("0");
            mCount2.setText("0");
            mCount1.setText("0");
            mCount0.setText("0");
            totalTimes.setText("0");
            mUpSbp.setText("0");
        }
        return view;
    }

    private void getLevelCount() {
        for (int i = 0;i<mList.size();i++) {
            String level = mList.get(i).getLevel();
            switch (level) {
                case BPMeasurement.PRESSURE_LEVEL_SEVERE:
                    mCount5.setText(String.valueOf(db.getCount(level)));
                    break;
                case BPMeasurement.PRESSURE_LEVEL_MODERATE:
                    mCount4.setText(String.valueOf(db.getCount(level)));
                    break;
                case BPMeasurement.PRESSURE_LEVEL_LIGHT:
                    mCount3.setText(String.valueOf(db.getCount(level)));
                    break;
                case BPMeasurement.PRESSURE_LEVEL_EDGE:
                    mCount2.setText(String.valueOf(db.getCount(level)));
                    break;
                case BPMeasurement.PRESSURE_LEVEL_NORMAL:
                    mCount1.setText(String.valueOf(db.getCount(level)));
                    break;
                case BPMeasurement.PRESSURE_LEVEL_IDEAL:
                    mCount0.setText(String.valueOf(db.getCount(level)));
                    break;
            }
        }



    }
    private void getTextLevelColor() {
            String level = measurement.getLevel();
            switch (level) {
                case BPMeasurement.PRESSURE_LEVEL_SEVERE:
                    mMeasureResultState.setTextColor(this.getResources().getColor(R.color.level_5));
                    break;
                case BPMeasurement.PRESSURE_LEVEL_MODERATE:
                    mMeasureResultState.setTextColor(this.getResources().getColor(R.color.level_4));
                    break;
                case BPMeasurement.PRESSURE_LEVEL_LIGHT:
                    mMeasureResultState.setTextColor(this.getResources().getColor(R.color.level_3));
                    break;
                case BPMeasurement.PRESSURE_LEVEL_EDGE:
                    mMeasureResultState.setTextColor(this.getResources().getColor(R.color.level_2));
                    break;
                case BPMeasurement.PRESSURE_LEVEL_NORMAL:
                    mMeasureResultState.setTextColor(this.getResources().getColor(R.color.level_1));
                    break;
                case BPMeasurement.PRESSURE_LEVEL_IDEAL:
                    mMeasureResultState.setTextColor(this.getResources().getColor(R.color.level_0));
                    break;
            }
    }

    /**
     * 取得对比结果绝对值
     * @return
     */
    private int getAbs() {
        int data = 0;
        data = Math.abs(db.getDataByLastMeasureAndMeasure());
        return data;
    }
    /**
     * 根据血压对比结果显示上升或下降图片
     */
    private void getImageUpOrDown() {
        if (db.getDataByLastMeasureAndMeasure() > 0) {
            imageView.getDrawable().setLevel(1);
        } else {
            imageView.getDrawable().setLevel(2);
        }
    }

}
