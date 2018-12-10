package com.yuyue.mbp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.yuyue.mbp.DatabaseHelper;
import com.yuyue.mbp.R;
import com.yuyue.mbp.entity.BPMeasurement;
import com.yuyue.mbp.global.GlobalConstant;
import com.yuyue.mbp.global.GlobalContext;
import com.yuyue.mbp.global.event.Event;
import com.yuyue.mbp.global.utils.CommonUtil;
import com.yuyue.mbp.global.utils.DateUtil;
import com.yuyue.mbp.global.utils.DialogUtil;
import com.yuyue.mbp.ui.base.AppBaseActivity;
import java.util.Date;
import de.greenrobot.event.EventBus;

/**
 * Created by Renyx on 2018/7/17
 */
public class MeasureSaveActivity extends AppBaseActivity{
    private DatabaseHelper db;
    private EventBus mEventBus;
    private DialogUtil mDialogUtil;
    private Toast mToast = null;

    private TextView mSbp;
    private TextView mDbp;
    private TextView mPulse;
    private TextView mMeasureTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurement_finish);
        setActionBarLayout(R.layout.actionbar_manual_add);

        mEventBus = EventBus.getDefault();
        mEventBus.register(this);
        db = GlobalContext.getDatabase();
        mDialogUtil = new DialogUtil(this);

        int [] array = (int[])getIntent().getIntArrayExtra("data");

        BPMeasurement measurement = new BPMeasurement();
        measurement.setMeasureTime(new Date());

        mSbp = (TextView) findViewById(R.id.manual_sbp);
        mDbp = (TextView) findViewById(R.id.manual_dbp);
        mPulse = (TextView) findViewById(R.id.manaul_pulse);
        mMeasureTime = (TextView) findViewById(R.id.manaul_date);
        if (measurement != null) {
            mSbp.setText(String.valueOf(array[0]));
            mDbp.setText(String.valueOf(array[1]));
            mPulse.setText(String.valueOf(array[2]));
            mMeasureTime.setText(DateUtil.formatCustomDate(measurement.getMeasureTime(), "yyyy-MM-dd HH:mm"));
        }
        Button mSave = (Button) findViewById(R.id.manual_save);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMeasurement();
            }
        });
    }

    @Override
    protected void initActionBarView(View v) {
        ImageView mBack = (ImageView) findViewById(R.id.btn_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventBus.post(Event.getEvent(GlobalConstant.Event.PATIENT_MANAGE_BACK_PRESSED));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mEventBus.unregister(this);
    }
    private void saveMeasurement() {
        String sbp = mSbp.getText().toString();
        String dbp = mDbp.getText().toString();
        String pulseRate = mPulse.getText().toString();

        BPMeasurement measurement = new BPMeasurement();
        measurement.setSbp(Integer.valueOf(sbp));
        measurement.setDbp(Integer.valueOf(dbp));
        measurement.setPulseRate(Integer.valueOf(pulseRate));
        measurement.setMeasureTime(new Date());
        measurement.setLevel(CommonUtil.getPressureLevel(Integer.valueOf(sbp),Integer.valueOf(dbp)));

        if (db.saveBPMeasurement(measurement)) {
            finish();
            showMessage(R.string.tip_data_saved);
            Intent intent = new Intent();
            intent.setClass(MeasureSaveActivity.this,Menu.class);
            startActivity(intent);
//            finish();
        }
    }

    /**
     * 事件消息接收
     * @param e
     */
    public void onEventMainThread(Object e) {
        if (e instanceof Event) {
            Event event = (Event) e;
            if (event.what == GlobalConstant.Event.PATIENT_MANAGE_BACK_PRESSED ) {
                if (canDirectBack()) {
                    finish();
                } else {
                    mDialogUtil.showAlertDialog(R.string.dialog_title_manage_back, R.string.dialog_message_manage_back,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    saveMeasurement();
                                }
                            },
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }
                    );
                }
            }
        }
    }

    /**
     * 显示Toast形式提示信息
     * @param resId
     */
    protected void showMessage(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

    /**
     * 显示Toast形式的提示信息
     * @param message
     */
    protected void showMessage(String message) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    private boolean canDirectBack() {
        boolean canBack = false;
            canBack = TextUtils.isEmpty(mSbp.getText().toString()) &&
                    TextUtils.isEmpty(mDbp.getText().toString()) &&
                    TextUtils.isEmpty(mPulse.getText().toString());
        return canBack;
    }

}
