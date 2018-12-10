package com.yuyue.mbp.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.yuyue.mbp.DatabaseHelper;
import com.yuyue.mbp.R;
import com.yuyue.mbp.entity.BPMeasurement;
import com.yuyue.mbp.global.GlobalContext;
import com.yuyue.mbp.global.utils.ChartUtil;
import com.yuyue.mbp.global.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Renyx on 2018/7/4
 */
@SuppressLint("ValidFragment")
public class DataCurveFragment extends Fragment {
    private LineChart lineChart1;
    private LineChart lineChart2;
    private List<String> xDataList;
    private List<Entry> y1DataList;
    private List<Entry> y2DataList;
    private List<Entry> pDataList;
    private DatabaseHelper db;
    private List<BPMeasurement> mList;

    public DataCurveFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = GlobalContext.getDatabase();
        mList = db.getBPMeasurementList();
        xDataList = new ArrayList<>();
        y1DataList = new ArrayList<>();
        y2DataList = new ArrayList<>();
        pDataList = new ArrayList<>();
        setData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_curve_fragment,container,false);
        lineChart1 = (LineChart) view.findViewById(R.id.line_one);
        lineChart2 = (LineChart) view.findViewById(R.id.line_two);

        ChartUtil.showChart(getActivity(),lineChart2,xDataList,pDataList,"HeartBeat","Pluse/min");
        ChartUtil.showChart(getActivity(), lineChart1,xDataList,y1DataList,y2DataList,"Systolic","Diatolic","mmHg");
        return view;
    }


    private void setData() {
        String month = null;
        String day = null;
        String md = null;
        int sbp = 0;
        int dbp = 0;
        int pluse = 0;

        for (int i = 0;i < mList.size();i++) {
            month = DateUtil.formatCustomDate(mList.get(i).getMeasureTime(), "M");
            day = DateUtil.formatCustomDate(mList.get(i).getMeasureTime(), "d");
            md = month+"/"+day;
            xDataList.add(md);

            sbp = mList.get(i).getSbp();
            dbp = mList.get(i).getDbp();
            pluse = mList.get(i).getPulseRate();

            y1DataList.add(new Entry(sbp,i));
            y2DataList.add(new Entry(dbp,i));
            pDataList.add(new Entry(pluse,i));
        }

    }

}
