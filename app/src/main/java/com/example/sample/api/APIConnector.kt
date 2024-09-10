package com.example.sample.api

import android.app.Dialog
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.sample.Utils.MessageUtils
import com.example.sample.Model.response.ApiResponse
import com.example.sample.Utils.AppConstants
import com.example.sample.Utils.Utils
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class APIConnector {

    companion object {

        fun apiCall(
            context: FragmentActivity,
            successResponseFromAPI: SuccessResponseFromAPI,
            failureResponseFromAPI: FailureResponseFromAPI,
            api: String,
            getMethod: String,
            loading: Boolean
        ) {
            var apiCall = RestApi.getClient(context).postApiCall(api)
            if (getMethod == ParamKey.GET_METHOD) {
                apiCall = RestApi.getClient(context).getApiCall(api)
            }
            responseCall(context, apiCall, successResponseFromAPI, failureResponseFromAPI, api, loading)
        }

        fun apiCallWithParam(
            context: FragmentActivity,
            successResponseFromAPI: SuccessResponseFromAPI,
            failureResponseFromAPI: FailureResponseFromAPI,
            api: String,
            map: HashMap<String, Any>,
            getMethod: String,
            loading: Boolean
        ) {
            var apiCall = RestApi.getClient(context).postApiCallWithParam(api, map)
            if (getMethod == ParamKey.GET_METHOD) {
                apiCall = RestApi.getClient(context).getApiCallWithParam(api, map)
            }
            responseCall(context, apiCall, successResponseFromAPI, failureResponseFromAPI, api, loading)
        }

        fun apiCallWithBody(
            context: FragmentActivity,
            successResponseFromAPI: SuccessResponseFromAPI,
            failureResponseFromAPI: FailureResponseFromAPI,
            api: String,
            map: HashMap<String, Any>,
            getMethod: String,
            loading: Boolean
        ) {
            var apiCall = RestApi.getClient(context).postApiCallWithBody(api, map)
            if (getMethod == ParamKey.GET_METHOD) {
                apiCall = RestApi.getClient(context).getApiCallWithBody(api, map)
            }
            responseCall(context, apiCall, successResponseFromAPI, failureResponseFromAPI, api, loading)
        }

        fun apiCallWithField(
            context: FragmentActivity,
            successResponseFromAPI: SuccessResponseFromAPI,
            failureResponseFromAPI: FailureResponseFromAPI,
            api: String,
            map: HashMap<String, Any>,
            getMethod: String,
            loading: Boolean
        ) {
            var apiCall = RestApi.getClient(context).postApiCallWithField(api, map)
            if (getMethod == ParamKey.GET_METHOD) {
                apiCall = RestApi.getClient(context).getApiCallWithField(api, map)
            }
            responseCall(context, apiCall, successResponseFromAPI, failureResponseFromAPI, api, loading)
        }

        fun apiCallWithMultiPart(
            context: FragmentActivity,
            successResponseFromAPI: SuccessResponseFromAPI,
            failureResponseFromAPI: FailureResponseFromAPI,
            api: String,
            partMap: ArrayList<MultipartBody.Part>,
            map: HashMap<String, RequestBody>,
            getMethod: String,
            loading: Boolean
        ) {
            var apiCall = RestApi.getClient(context).postApiCallWithMultiPart(api, partMap, map)
            if (getMethod == ParamKey.GET_METHOD) {
                apiCall = RestApi.getClient(context).getApiCallWithMultiPart(api, partMap, map)
            }
            responseCall(context, apiCall, successResponseFromAPI, failureResponseFromAPI, api,  loading)
        }

        fun apiCallWithRawMap(
            context: FragmentActivity,
            successResponseFromAPI: SuccessResponseFromAPI,
            failureResponseFromAPI: FailureResponseFromAPI,
            api: String,
            map: HashMap<String, Any>,
            getMethod: String,
            loading: Boolean
        ) {
            var apiCall = RestApi.getClient(context).postApiCallWithRawMap(api, map)
            if (getMethod == ParamKey.GET_METHOD) {
                apiCall = RestApi.getClient(context).getApiCallWithRawMap(api, map)
            }
            responseCall(context, apiCall, successResponseFromAPI, failureResponseFromAPI, api, loading)
        }

        private fun responseCall(
            context: FragmentActivity,
            call: Call<ResponseBody>,
            successResponseFromAPI: SuccessResponseFromAPI,
            failureResponseFromAPI: FailureResponseFromAPI,
            api: String,
            loading: Boolean
        ) {
            var dialog : Dialog?= null
            if (loading) {
                dialog = MessageUtils.showDialog(context)
            }
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e( "onResponseTime: ", System.currentTimeMillis().toString())
                    MessageUtils.dismissDialog(dialog)
                    if (response.isSuccessful) {
                        if (ParamAPI.isCode(response.code().toString())) {
                            response.body()?.let {
                                val jsonObject = JSONObject(it.string())
                                val jsonString = jsonObject.toString()
                                val apiResponse = Gson().fromJson(jsonString, ApiResponse::class.java)
                                if (ParamAPI.isStatus(apiResponse.status)) {
                                    /*if (!JSONObject(it).isNull(ParamKey.RESPONSE)) {
                                        responseString = JSONObject(it).get(ParamKey.RESPONSE).toString()
                                    }*/
                                    Log.e( "onResponseTime: ", System.currentTimeMillis().toString())
                                    successResponseFromAPI.onResponseSuccess(Gson().toJson(apiResponse.result), apiResponse, api)
                                }
                                else {
                                    failureResponseFromAPI.onResponseErrorCode(apiResponse.message, api, 100.toInt())
                                }
                            }
                        }
                        else {
                            failureResponseFromAPI.onResponseErrorCode(
                                response.message(),
                                api,
                                response.code()
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    MessageUtils.dismissDialog(dialog)
                    Log.e( "onFailure: ", t.message.toString())
                    failureResponseFromAPI.onResponseFailure(t.message.toString(), api)
                }
            })
        }

        /*private fun responseCall(
            context: FragmentActivity,
            call: Call<ResponseBody>,
            successResponseFromAPI: SuccessResponseFromAPI,
            failureResponseFromAPI: FailureResponseFromAPI,
            api: String,
            loading: Boolean
        ) {
            var dialog : Dialog?= null
            if (loading) {
                dialog = MessageUtils.showDialog(context)
            }
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    MessageUtils.dismissDialog(dialog)
                    if (ParamAPI.isStatus(response.code().toString())) {
                        val responseBody = response.body()?.string()
                        responseBody?.let {
                            val responseCode = JSONObject(it).get(ParamKey.RESPONSE_CODE).toString()
                            val responseMessage = JSONObject(it).get(ParamKey.MESSAGE).toString()
                            if (ParamAPI.isStatus(responseCode)) {
                                val responseString = JSONObject(it).get(ParamKey.RESPONSE).toString()
                                *//*if (!JSONObject(it).isNull(ParamKey.RESPONSE)) {
                                    responseString = JSONObject(it).get(ParamKey.RESPONSE).toString()
                                }*//*
                                val apiResponse = ParamAPI.convertResponseData(
                                    responseBody,
                                    ApiResponse::class.java
                                )
                                successResponseFromAPI.onResponseSuccess(responseString, apiResponse, api)
                            }
                            else {
                                failureResponseFromAPI.onResponseErrorCode(responseMessage, api, responseCode.toInt())
                            }
                        }
                    }
                    else {
                        failureResponseFromAPI.onResponseErrorCode(
                            response.message(),
                            api,
                            response.code()
                        )
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    MessageUtils.dismissDialog(dialog)
                    Log.e( "onFailure: ", t.message.toString())
                    failureResponseFromAPI.onResponseFailure(t.message.toString(), api)
                }
            })
        }*/

        fun googleApiCallWithParam(
            activity: FragmentActivity,
            successResponseFromAPI: SuccessResponseFromAPI,
            failureResponseFromAPI: FailureResponseFromAPI,
            api: String,
            map: HashMap<String, Any>,
            getMethod: String,
            loading: Boolean
        ) {
            var apiCall = RestApi.getClient(activity).postApiCallWithParam(api, map)
            if (getMethod == ParamKey.GET_METHOD) {
                apiCall = RestApi.getClient(activity).getApiCallWithParam(api, map)
            }

            var dialog : Dialog?= null
            if (loading) {
                dialog = MessageUtils.showDialog(activity)
            }

            apiCall.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    MessageUtils.dismissDialog(dialog)
                    if (ParamAPI.isStatus(response.code().toString())) {
                        val responseBody = response.body()?.string()
                        responseBody?.let {
                            if (ParamAPI.isStatus(response.code().toString())) {
                                val apiResponse = ApiResponse(response.message(), "", "", "")
                                successResponseFromAPI.onResponseSuccess(it.toString(), apiResponse, api)
                            }
                            else {
                                failureResponseFromAPI.onResponseErrorCode(
                                    response.message(),
                                    api,
                                    response.code()
                                )
                            }
                        }
                    }
                    else {
                        failureResponseFromAPI.onResponseErrorCode(
                            response.message(),
                            api,
                            response.code()
                        )
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    MessageUtils.dismissDialog(dialog)
                    Log.e( "onFailure: ", t.message.toString())
                    failureResponseFromAPI.onResponseFailure(t.message.toString(), api)
                }
            })
        }

    }

}