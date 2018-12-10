package com.yuyue.mbp.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.yuyue.mbp.DatabaseHelper;
import com.yuyue.mbp.R;
import com.yuyue.mbp.entity.BPMeasurement;
import com.yuyue.mbp.global.GlobalContext;
import com.yuyue.mbp.global.utils.DateUtil;
import com.yuyue.mbp.ui.base.adapter.ExpandableListViewAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * Created by Renyx on 2018/7/4
 */
@SuppressLint("ValidFragment")
public class DataDailyFragment extends Fragment {
    private DatabaseHelper db;
    private ExpandableListViewAdapter expandableListViewAdapter;
    private List<List<BPMeasurement>> childList;
    private List<String> groupList;
    private List<BPMeasurement> mList;
    private TextView dataListNull;
    private BPMeasurement measurement;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = GlobalContext.getDatabase();
        groupList = new ArrayList<>();
        childList = new ArrayList<>();
        measurement = db.getBPMeasurement();
        expandableListViewAdapter = new ExpandableListViewAdapter(getActivity(),groupList,childList);

        initdata();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_daily_fragment,container,false);

        dataListNull = (TextView) view.findViewById(R.id.data_list_null);
        if (measurement == null) {
            dataListNull.setText("数据列表为空");
        } else {
            ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.expandable_listview);
            expandableListView.setAdapter(expandableListViewAdapter);
            for (int i = 0;i<groupList.size();i++) {
                expandableListView.expandGroup(i);
            }
            expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    if (parent.isGroupExpanded(groupPosition)) {
                        parent.collapseGroup(groupPosition);
                    } else {
                        parent.expandGroup(groupPosition,false);
                    }
                    return true;
                }
            });
        }
        return view;
    }
    private void initdata() {
        List<String> group = new ArrayList<>();
        mList = db.getBPMeasurementList();

        for (int i = 0;i<mList.size();i++) {
            group.add(DateUtil.formatCustomDate(mList.get(i).getMeasureTime(),"M"));
        }
        HashSet h = new HashSet(group);
        group.clear();
        group.addAll(h);
        for (int j = 0;j<group.size();j++) {
            groupList.add(group.get(j));
        }

//        for (int i = 0;i<group.size();i++) {
//            for (int h = group.size()-1;h>i;h--) {
//                if (group.get(i).equals(group.get(h))) {
//                    group.remove(h);
//                }
//            }
//            groupList.add(group.get(i));
//        }

        for (int k = 0;k<group.size();k++) {
            List<BPMeasurement> list = new ArrayList<>();
            String month = null;
            for (int j = 0;j<mList.size();j++) {
                    month = DateUtil.formatCustomDate( mList.get(j).getMeasureTime(), "M");
                    String a = group.get(k);
                    if (a.equals(month)) {
                        list.add(mList.get(j));
                    }
                }
            childList.add(list);
        }

    }


}
