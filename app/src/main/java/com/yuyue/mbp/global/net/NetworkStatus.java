package com.yuyue.mbp.global.net;

/**
 * 网络状况
 * Created by Chen on 2014/11/28.
 */
public enum NetworkStatus {
    //网络请求
    NETWORK_REQUEST_QUERY,
    //网络请求IO异常
    NETWORK_REQUEST_IOEXCEPTION,
    //网络请求返回空
    NETWORK_REQUEST_RETUN_NULL,
    //返回解析错误
    NETWORK_REQUEST_RESULT_PARSE_ERROR,
    //没有连接到WIFI网络
    NETWORK_NOT_CONNECTED
}
