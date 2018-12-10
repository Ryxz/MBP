package com.yuyue.mbp.ui;

import android.app.ActivityManager;
import android.bluetooth.BluetoothProfile;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuwell.bluetooth.le.constants.BleMessage;
import com.yuwell.bluetooth.le.core.BleManager;
import com.yuwell.bluetooth.le.device.bpm.BPMManager;
import com.yuyue.mbp.DatabaseHelper;
import com.yuyue.mbp.R;
import com.yuyue.mbp.bluetooth.BluetoothLeService;
import com.yuyue.mbp.global.GlobalContext;
import com.yuyue.mbp.global.preferencehelper.SettingUtil;
import com.yuyue.mbp.global.utils.ResourceUtil;
import com.yuyue.mbp.ui.base.AppBaseActivity;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Renyx on 2018/7/3
 */
public class MeasureActivity extends AppBaseActivity {
    public static final String TAG = MeasureActivity.class.getSimpleName();
    private TextView mStatu;
    private ImageView mDeviceDes;
    private ImageView mDeviceCon;
    private ImageView mDeviceMea;
    private EventBus mEventBus;
    private BleManager bleDevice;
    private Toast mToast = null;
    private boolean isBLESupported = true;
    private BluetoothLeService.LocalBinder mBluetoothLeService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarLayout(R.layout.actionbar_measuring);
        setContentView(R.layout.measure_layout);

        mEventBus = EventBus.getDefault();
        mEventBus.register(this);

        bleDevice =  BPMManager.getInstance(getApplicationContext());
        ImageView imageView = (ImageView) findViewById(R.id.btn_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mStatu = (TextView) findViewById(R.id.text_status);
        mDeviceDes = (ImageView) findViewById(R.id.device_state_0);
        mDeviceCon = (ImageView) findViewById(R.id.device_state_1);
        mDeviceMea = (ImageView) findViewById(R.id.device_state_2);

        isBLESupported = SettingUtil.isBleSupported();
        // 设备支持BLE，和BleService建立绑定
        if (isBLESupported) {
            Intent startService = new Intent(MeasureActivity.this, BluetoothLeService.class);
            bindService(startService, mServiceConnection, BIND_AUTO_CREATE);
        } else {
            mStatu.setVisibility(View.GONE);
            mDeviceDes.setVisibility(View.GONE);
            mDeviceCon.setVisibility(View.GONE);
            mDeviceMea.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (isApplicationSentToBackground()) {
            if (mBluetoothLeService != null) {
                mBluetoothLeService.stopScanBleDevice();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventBus.unregister(this);

        if (mBluetoothLeService != null) {
            if (mBluetoothLeService.getConnectionState() == BluetoothProfile.STATE_CONNECTED) {
                mBluetoothLeService.setBleManager(null);
            }
            unbindService(mServiceConnection);
            mBluetoothLeService = null;
        }
    }

    /**
     * Service连接回调函数
     */
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = (BluetoothLeService.LocalBinder) service;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                mBluetoothLeService.setIntervals(20, 5);
            }
            mBluetoothLeService.setBleManager(bleDevice);
            // Activity创建时判断当前蓝牙服务连接状态
            // 若断开则重新扫描，否则直接更新界面
            int state = mBluetoothLeService.getConnectionState();
            Log.d(TAG, "onServiceConnected: " + state);
            setConnectionUI(state);
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    private void setConnectionUI(int state) {
        mStatu.setText(ResourceUtil.getStringId("ble_state_" + state));

            switch (state) {
                    case 0:
                    mDeviceDes.setBackgroundResource(ResourceUtil.getDrawableId("device_state_2"));
                    mDeviceCon.setBackgroundResource(ResourceUtil.getDrawableId("device_state_0"));
                    mDeviceMea.setBackgroundResource(ResourceUtil.getDrawableId("device_state_0"));
                    break;
                    case 1:
                    mDeviceDes.setBackgroundResource(ResourceUtil.getDrawableId("device_state_2"));
                    mDeviceCon.setBackgroundResource(ResourceUtil.getDrawableId("device_state_2"));
                    mDeviceMea.setBackgroundResource(ResourceUtil.getDrawableId("device_state_0"));
                    break;
                case 2:
                    mDeviceDes.setBackgroundResource(ResourceUtil.getDrawableId("device_state_2"));
                    mDeviceCon.setBackgroundResource(ResourceUtil.getDrawableId("device_state_2"));
                    mDeviceMea.setBackgroundResource(ResourceUtil.getDrawableId("device_state_2"));
                    break;
            }
        }

    /**
     * 在主线程中接收事件
     * @param e
     */
    public void onEventMainThread(Object e) {
        if (e instanceof Message) {
            Message event = (Message) e;
            switch (event.what) {
                case BleMessage.BP_DATA:
                    int[] array = (int[]) event.obj;
                    Intent intent = new Intent();
                    intent.putExtra("data",array);
                    intent.setClass(MeasureActivity.this,MeasureSaveActivity.class);
                    startActivity(intent);
                    break;
                case BleMessage.STATE_CHANGE:
                    setConnectionUI(event.arg1);
                    break;
            }
        }

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
     * 判断是否为切换到后台运行
     * @return
     */
    private boolean isApplicationSentToBackground() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(getPackageName())) {
                return true;
            }
        }
        return false;
    }




}
