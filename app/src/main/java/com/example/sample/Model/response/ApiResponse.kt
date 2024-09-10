package com.example.sample.Model.response

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: Any,
    @SerializedName("status")
    val status: String
)