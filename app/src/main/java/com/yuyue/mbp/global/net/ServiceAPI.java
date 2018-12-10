package com.yuyue.mbp.global.net;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Server rest api
 * Created by Chen on 2016/1/15.
 */
public interface ServiceAPI {

    @FormUrlEncoded
    @POST("/DataSyn/SynData")
    Call<String> sync(@Field("synModel") String model, @Field("uploadData") String uploadData);

    @GET("/DataSyn/GetNow")
    Call<JsonObject> getSystemTime();
}
