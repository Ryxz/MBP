package com.yuyue.mbp.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyue.mbp.DatabaseHelper;
import com.yuyue.mbp.R;
import com.yuyue.mbp.entity.BPMeasurement;
import com.yuyue.mbp.global.GlobalConstant;
import com.yuyue.mbp.global.GlobalContext;
import com.yuyue.mbp.global.event.Event;
import com.yuyue.mbp.global.export.ExportUtil;
import com.yuyue.mbp.global.utils.CommonUtil;


import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Renyx on 2018/7/3
 */
public class DataFragment extends Fragment implements View.OnClickListener{

    private DatabaseHelper db;
    private EventBus mEventBus;
    private FragmentManager fm;
    private List<BPMeasurement> exportList;
    private Toast mToast;
    private ExportUtil mExportUtil;
    private Button daily;
    private Button curve;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = GlobalContext.getDatabase();
        mExportUtil = GlobalContext.getExportUtil();
        mEventBus = EventBus.getDefault();
        mEventBus.register(this);
        exportList = db.getBPMeasurementList();
        setDefaultFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_layout,container,false);

        TextView export = (TextView) view.findViewById(R.id.btn_export);
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExportUtil.exportHistoryToExcel(exportList);
            }
        });

        daily = (Button) view.findViewById(R.id.tv_main_menu_daily);
        daily.setOnClickListener(this);
        daily.setSelected(true);
        curve = (Button) view.findViewById(R.id.tv_main_menu_curve);
        curve.setOnClickListener(this);

        return view;
    }


    private void setTabSelection(int posion) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        switch (posion) {
            case 0:
                ft.replace(R.id.tb_data_fm,new DataDailyFragment());
                break;
            case 1:
                ft.replace(R.id.tb_data_fm,new DataCurveFragment());
                break;
        }
        ft.commit();

    }
    private void setDefaultFragment() {
        fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tb_data_fm, new DataDailyFragment());
        transaction.commit();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_main_menu_daily:
                setTabSelection(0);
                daily.setSelected(true);
                curve.setSelected(false);
                break;
            case R.id.tv_main_menu_curve:
                setTabSelection(1);
                daily.setSelected(false);
                curve.setSelected(true);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mEventBus.unregister(this);
    }

    /**
     * 在主线程中接收事件
     * @param event
     */
    public void onEventMainThread(Event event) {
        String path = (String) event.obj;
        Intent intent = null;
        switch (event.what) {
            case GlobalConstant.Event.EXPORT_SUCCESS:
                showMessage(R.string.tip_export_success);

                intent = CommonUtil.startExcelApp(path);
                break;
        }
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

            showMessage(String.format(getString(R.string.tip_app_not_found), path), Toast.LENGTH_LONG);
        }
    }
    /**
     * 显示Toast形式提示信息
     * @param resId
     */
    protected void showMessage(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

    /**
     * 显示Toast形式的提示信息
     * @param message
     */
    protected void showMessage(String message, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), message, duration);
        } else {
            mToast.setText(message);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

}
