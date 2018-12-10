package com.yuyue.mbp.global;

/**
 * Created by Renyx on 2018/6/7
 */
    public class GlobalConstant {
        public static final int PRIORITY_BP = 1;
        /**
         * 总线消息类型
         */
        public static final class Event {
            // 测量数据保存成功
            public static final int NEW_MEASUREMENT = 0x02;
            // 蓝牙设备连接状态改变
            public static final int BLE_STATE_CHANGE = 0x04;
            // 接收到蓝牙设备消息
            public static final int BLE_DATA_AVAILABLE = 0x06;
            // 导出成功
            public static final int EXPORT_SUCCESS = 0x08;
            // 打印成功
            public static final int PRINT_SUCCESS = 0x09;
            // 导出失败
            public static final int EXPORT_FAILED = 0x10;
            // 打印失败
            public static final int PRINT_FAILED = 0x11;
            // 同步数据
            public static final int SYNC_DATA = 0x12;
            // 同步成功
            public static final int SYNC_SUCCESS = 0x13;
            // 同步失败
            public static final int SYNC_FAILED = 0x14;
            // 连接蓝牙中触发返回键
            public static final int PATIENT_MANAGE_BACK_PRESSED = 0x16;
            // 测量完成
            public static final int GLU_DATA_AVAILABLE = 0x19;
            //查询上一次测量结果对比成功
            public  static final int SELECT_MEASUREMENT = 0x23;
            // 错误
            public static final int GLU_ERROR = 0x20;
            // 初始化串口错误
            public static final int INIT_SP_FAILED = 0x21;
            // 初始化串口成功
            public static final int INIT_SP_SUCCESS = 0x22;
        }



    }
