package com.egmvdev.venturesoft.utils.web;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface UTGenericEndPointInterface {

    @POST("{method}")
    Call<ResponseBody> postResponseParamsQuery(@Path("method") String method, @QueryMap Map<String, String> params);


    @FormUrlEncoded
    @Headers({"Content-Type:application/x-www-form-urlencoded"})
    @POST("{method}")
    Call<ResponseBody> responsePostAuthorization(
            @Header("Authorization") String auth,
            @Path("method") String method,
            @FieldMap Map<String, Integer> params
    );

    @Headers({"Content-Type:application/json"})
    @POST("{method}")
    Call<ResponseBody> responsePostAuthorizationJson(
            @Header("Authorization") String auth,
            @Path("method") String method,
            @Body JsonObject params
    );
}
