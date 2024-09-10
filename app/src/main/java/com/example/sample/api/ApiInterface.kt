package com.example.sample.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList


interface ApiInterface {

    @GET("{path}")
    fun getApiCall(
        @Path("path", encoded = true) path: String,
    ) : Call<ResponseBody>

    @POST("{path}")
    fun postApiCall(
        @Path("path", encoded = true) path: String,
    ) : Call<ResponseBody>

    @GET("{path}")
    fun getApiCallWithParam(
        @Path("path", encoded = true) path: String,
        @QueryMap map: HashMap<String, Any>
    ) : Call<ResponseBody>

    @POST("{path}")
    fun postApiCallWithParam(
        @Path("path", encoded = true) path: String,
        @QueryMap map: HashMap<String, Any>
    ) : Call<ResponseBody>

    @GET("{path}")
    fun getApiCallWithBody(
        @Path("path", encoded = true) path: String,
        @Body map: HashMap<String, Any>
    ) : Call<ResponseBody>

    @POST("{path}")
    fun postApiCallWithBody(
        @Path("path", encoded = true) path: String,
        @Body map: HashMap<String, Any>
    ) : Call<ResponseBody>

    @FormUrlEncoded
    @GET("{path}")
    fun getApiCallWithField(
        @Path("path", encoded = true) path: String,
        @FieldMap map: HashMap<String, Any>
    ) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("{path}")
    fun postApiCallWithField(
        @Path("path", encoded = true) path: String,
        @FieldMap map: HashMap<String, Any>
    ) : Call<ResponseBody>

    @Multipart
    @GET("{path}")
    fun getApiCallWithMultiPart(
        @Path("path", encoded = true) path: String,
        @Part partMap: ArrayList<MultipartBody.Part>,
        @PartMap map: HashMap<String, RequestBody>
    ) : Call<ResponseBody>

    @Multipart
    @POST("{path}")
    fun postApiCallWithMultiPart(
        @Path("path", encoded = true) path: String,
        @Part partMap: ArrayList<MultipartBody.Part>,
        @PartMap map: HashMap<String, RequestBody>
    ) : Call<ResponseBody>

    @GET("{path}")
    fun getApiCallWithRawMap(
        @Path("path", encoded = true) path: String,
        @Body map: HashMap<String, Any>
    ) : Call<ResponseBody>

    @POST("{path}")
    fun postApiCallWithRawMap(
        @Path("path", encoded = true) path: String,
        @Body map: HashMap<String, Any>
    ) : Call<ResponseBody>
}