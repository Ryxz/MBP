package com.yuyue.mbp.bluetooth;

import android.bluetooth.BluetoothProfile;

import com.yuwell.bluetooth.le.core.BleService;
import com.yuyue.mbp.global.GlobalConstant;
import com.yuyue.mbp.global.event.Event;

import de.greenrobot.event.EventBus;

/**
 * Service for managing connection and data communication with a GATT server
 * hosted on a given Bluetooth LE device.
 */
public class BluetoothLeService extends BleService{

    private boolean isNetworkActive = false;

    private EventBus mEventBus;

    @Override
    public void onCreate() {
        super.onCreate();
        mEventBus = EventBus.getDefault();
        mEventBus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mEventBus.unregister(this);
    }


    /**
     * 在主线程中接收事件
     * 实测发现在部分机型蓝牙扫描会影响Wifi的连接性，严重的甚至导致断开，故在上传时停止扫描蓝牙
     * @param event
     */
    public void onEventMainThread(Event event) {
        switch (event.what) {
            case GlobalConstant.Event.SYNC_DATA:
                stopScan();
                isNetworkActive = true;
                break;
            case GlobalConstant.Event.SYNC_FAILED:
            case GlobalConstant.Event.SYNC_SUCCESS:
                if (getConnectionState() == BluetoothProfile.STATE_DISCONNECTED) {
                    startScan(true);
                }
                isNetworkActive = false;
                break;
        }
    }
}
