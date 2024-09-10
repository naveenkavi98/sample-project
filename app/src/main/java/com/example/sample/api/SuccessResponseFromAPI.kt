package com.example.sample.api

import com.example.sample.Model.response.ApiResponse
import java.io.Serializable

interface SuccessResponseFromAPI:Serializable {

    fun onResponseSuccess(responseString: String, apiResponse: ApiResponse, api: String)
}