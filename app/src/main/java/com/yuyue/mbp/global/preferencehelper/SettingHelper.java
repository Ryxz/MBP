package com.yuyue.mbp.global.preferencehelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Renyx on 2018/6/8
 */
class SettingHelper {

    private static SharedPreferences sharedPreferences = null;

    private SettingHelper() {

    }
    public static Boolean getSharedPreferences(Context paramContext, String paramString, Boolean paramBoolean) {
        return getSharedPreferencesObject(paramContext).getBoolean(paramString, paramBoolean);
    }

    public static String getSharedPreferences(Context paramContext, String paramString1, String paramString2) {
        return getSharedPreferencesObject(paramContext).getString(paramString1, paramString2);
    }

    private static SharedPreferences getSharedPreferencesObject(Context paramContext) {
        if (sharedPreferences == null)
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
        return sharedPreferences;
    }

}
