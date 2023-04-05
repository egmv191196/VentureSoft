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

    @GET("{method}")
    Call<ResponseBody> getResponse(@Path("method") String method);

    @GET("{method}")
    Call<ResponseBody> getResponseParamsQuery(@Path("method") String method, @QueryMap Map<String, String> params);

    @POST("{method}")
    Call<ResponseBody> postResponseParamsQuery(@Path("method") String method, @QueryMap Map<String, String> params);


    @GET
    Call<ResponseBody> responseGETAuthorization(
            @Url String url,
            @Header("Authorization") String auth
    );

    @Headers({"Content-Type:application/json"})
    @POST("{method}")
    Call<ResponseBody> responsePost(@Path("method") String method, @Body String params);

    @FormUrlEncoded
    @Headers({"Content-Type:application/x-www-form-urlencoded"})
    @POST("{method}")
    Call<ResponseBody> responsePost(@Path("method") String method, @FieldMap Map<String, String> params);

    @Headers({"Content-Type:application/json"})
    @POST("{method}")
    Call<ResponseBody> responsePostHeaders(
            @HeaderMap Map<String, String> headers,
            @Path("method") String method,
            @Body String parametros
    );

    @Headers({"Content-Type:application/json"})
    @POST("{method}")
    Call<ResponseBody> responsePostAuthorizationJson(
            @Header("Authorization") String auth,
            @Path("method") String method,
            @Body JsonObject params
    );
}
