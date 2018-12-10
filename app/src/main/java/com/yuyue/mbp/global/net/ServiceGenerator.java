package com.yuyue.mbp.global.net;

import com.yuyue.mbp.global.preferencehelper.SettingUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit service generator
 * Created by Chen on 2016/7/1.
 */
public class ServiceGenerator {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder getBuilder() {
        return new Retrofit.Builder()
                .baseUrl("http://" + SettingUtil.getWebServerIp() + ":" + SettingUtil.getWebServePort())
                .addConverterFactory(GsonConverterFactory.create());
    }

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = getBuilder().client(httpClient
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()).build();
        return retrofit.create(serviceClass);
    }
}
