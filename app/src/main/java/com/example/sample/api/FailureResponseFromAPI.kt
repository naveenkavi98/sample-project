package com.example.sample.api

import java.io.Serializable

interface FailureResponseFromAPI:Serializable {

    fun onResponseErrorCode(message: String, api: String, responseCode: Int)

    fun onResponseFailure(message: String, api: String)

}