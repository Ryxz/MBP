package com.yuyue.mbp.global.preferencehelper;

import android.content.Context;

import com.yuyue.mbp.global.GlobalConstant;
import com.yuyue.mbp.global.GlobalContext;

import java.util.UUID;

/**
 * Created by Renyx on 2018/6/8
 */
public class SettingUtil {

    private static final String WEB_SERVER = "webserver";
    private static final String PORT = "port";
    public static final String BLE_AVAILABLE = "ble_available";
    public static final String DEVICE_PATH = "device";
    public static final String BAUD_RATE = "baud_rate";
    public static final String SP_AVAILABLE = "sp_available";


    private SettingUtil() {}

    private static Context getContext() {
        return GlobalContext.getInstance();
    }

    public static String getWebServerIp() {
        return SettingHelper.getSharedPreferences(getContext(), WEB_SERVER, "192.168.18.222");
    }

    public static String getWebServePort() {
        return SettingHelper.getSharedPreferences(getContext(), PORT, "80");
    }

    public static boolean isBleSupported() {
        return SettingHelper.getSharedPreferences(getContext(), BLE_AVAILABLE, true);
    }

    public static String getDevicePath() {
        return SettingHelper.getSharedPreferences(getContext(), DEVICE_PATH, "/dev/ttyS3");
    }

    public static String getBaudRate() {
        return SettingHelper.getSharedPreferences(getContext(), BAUD_RATE, "9600");
    }

    public static boolean isSPDeviceSupported() {
        return SettingHelper.getSharedPreferences(getContext(), SP_AVAILABLE, false);
    }


}
