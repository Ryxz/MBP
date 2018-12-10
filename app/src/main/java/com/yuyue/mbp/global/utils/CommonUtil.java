package com.yuyue.mbp.global.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.yuyue.mbp.BuildConfig;
import com.yuyue.mbp.entity.BPMeasurement;
import com.yuyue.mbp.global.GlobalContext;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Renyx on 2018/6/7
 */
public class CommonUtil {
        /**
         * 保留一位小数
         * @param val
         * @return
         */
        public static String retainOneDecimal(String val) {
            if (val.endsWith(".")) {
                val = val.substring(0, val.length() - 1);
            }
            String[] strArr = val.split("\\.");
            if (strArr.length == 2 && strArr[1].length() > 1) {
                val = strArr[0] + "." + strArr[1].substring(0, 1);
            }
            return val;
        }

        /**
         * 判断IP地址是否合法
         * @param ip
         * @return
         */
        public static boolean isIpValid(String ip) {
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            return ip.matches(regex);
        }
        /**
         * 判断MAC地址是否合法
         * @param mac
         * @return
         */
        public static boolean isMacAddValid(String mac) {
            String regex = "^([0-9a-fA-F]{2})(([0-9a-fA-F]{2}){5})$";
            return mac.matches(regex);
        }

        /**
         * 获得打印程序Intent
         * @param filePath
         * @return
         */
        public static Intent startPrintApp(String filePath) {
            Uri uri = Uri.fromFile(new File(filePath));
            Intent intent = new Intent("org.androidprinting.intent.action.PRINT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri, "text/plain");
            return intent;
        }
        /**
         * 获得查看Excel Intent
         * @param filePath
         * @return
         */
        public static Intent startExcelApp(String filePath) {
            Uri uri = Uri.fromFile(new File(filePath));
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri, "application/vnd.ms-excel");
            return intent;
        }

        public static boolean isSnEquals(String settingSn, String actualSn) {
            return settingSn.substring(0, 12).equalsIgnoreCase(actualSn.substring(0, 12));
        }

        /**
         * 16进制的字符串转化成10进制的整形数组
         * @param ox
         * @return
         */
        private static int[] hexStr2DecArray(String ox, String regularExpression) {
            String stringmac[] = ox.split(regularExpression);
            int intmac[] = new int[stringmac.length];
            for (int i = 0; i < stringmac.length; i++) {
                intmac[i] = Integer.parseInt(stringmac[i], 16);
            }
            return intmac;
        }

        private static String[] xor(String string1, String string2) {
            int[] ox1 = hexStr2DecArray(string1, ":");
            int[] ox2 = hexStr2DecArray(string2, "-");
            String temp[] = new String[ox1.length];
            for (int i = 0; i < ox1.length; i++) {
                String s = Integer.toHexString(ox1[i] ^ ox2[i]);
                if (s.length() != 2) {
                    s = "0" + s;
                }
                temp[i] = s;
            }
            return temp;
        }

        public static String multiply(float v1, float v2) {
            BigDecimal b1 = new BigDecimal(Float.toString(v1));
            BigDecimal b2 = new BigDecimal(Float.toString(v2));
            return retainOneDecimal(b1.multiply(b2).toString());
        }

        public static String getVersion() {
            Context context = GlobalContext.getInstance();
            String version = "";
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                if (BuildConfig.DEBUG) {
                    Pattern pattern = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)");
                    Matcher matcherVersionNumber = pattern.matcher(packageInfo.versionName);
                    matcherVersionNumber.find();
                    int majorVersion = Integer.parseInt(matcherVersionNumber.group(1));
                    int minorVersion = Integer.parseInt(matcherVersionNumber.group(2));
                    int pointVersion = Integer.parseInt(matcherVersionNumber.group(3)) + 1;
                    if (pointVersion == 10) {
                        minorVersion++;
                        pointVersion = 0;
                    }
                    version = majorVersion + "." + minorVersion + "." + pointVersion + " (" + ++packageInfo.versionCode + ") beta";
                } else {
                    version = packageInfo.versionName + " (" + packageInfo.versionCode + ")";
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return version;
        }

        public static String getPressureLevel(int sbp,int dbp) {
            String sbpLevel;
            String dbpLevel;
           if (sbp >= 180) {
               sbpLevel = BPMeasurement.PRESSURE_LEVEL_SEVERE;
           } else if (sbp <=179 && sbp >= 160) {
               sbpLevel = BPMeasurement.PRESSURE_LEVEL_MODERATE;
           } else if (sbp <= 159 && sbp >=140) {
               sbpLevel = BPMeasurement.PRESSURE_LEVEL_LIGHT;
           } else if (sbp <= 139 && sbp >= 130) {
               sbpLevel = BPMeasurement.PRESSURE_LEVEL_EDGE;
           } else if (sbp <= 129 && sbp >= 120) {
               sbpLevel = BPMeasurement.PRESSURE_LEVEL_NORMAL;
           } else {
               sbpLevel = BPMeasurement.PRESSURE_LEVEL_IDEAL;
           }

           if (dbp >= 110) {
               dbpLevel = BPMeasurement.PRESSURE_LEVEL_SEVERE;
           } else if (dbp <= 109 && dbp >= 100) {
               dbpLevel = BPMeasurement.PRESSURE_LEVEL_MODERATE;
           } else if (dbp <= 99 && dbp >= 90) {
               dbpLevel = BPMeasurement.PRESSURE_LEVEL_LIGHT;
           } else if (dbp <= 89 && dbp >= 85) {
               dbpLevel = BPMeasurement.PRESSURE_LEVEL_EDGE;
           } else if (dbp <= 84 && dbp >= 80) {
               dbpLevel = BPMeasurement.PRESSURE_LEVEL_NORMAL;
           } else {
               dbpLevel = BPMeasurement.PRESSURE_LEVEL_IDEAL;
           }

            return  Integer.valueOf(sbpLevel) > Integer.valueOf(dbpLevel) ? sbpLevel : dbpLevel;
        }

    /**
     * 各等级文字显示
     * @return
     */
    public static String getLevelText(String level) {
        String levelText = null;
        switch (level) {
            case BPMeasurement.PRESSURE_LEVEL_IDEAL:
                levelText = BPMeasurement.PRESSURE_LEVEL_TEXT_IDEAL;
                break;
            case  BPMeasurement.PRESSURE_LEVEL_NORMAL:
                levelText = BPMeasurement.PRESSURE_LEVEL_TEXT_NORMAL;
                break;
            case BPMeasurement.PRESSURE_LEVEL_EDGE:
                levelText = BPMeasurement.PRESSURE_LEVEL_TEXT_EDGE;
                break;
            case BPMeasurement.PRESSURE_LEVEL_LIGHT:
                levelText = BPMeasurement.PRESSURE_LEVEL_TEXT_LIGHT;
                break;
            case BPMeasurement.PRESSURE_LEVEL_MODERATE:
                levelText = BPMeasurement.PRESSURE_LEVEL_TEXT_MODERATE;
                break;
            case BPMeasurement.PRESSURE_LEVEL_SEVERE:
                levelText = BPMeasurement.PRESSURE_LEVEL_TEXT_SEVERE;
                break;
        }
        return levelText;
    }

    }


