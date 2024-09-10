package com.example.sample.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ParamAPI {

    var DEVELOPER_MODE = false

    //val ACTUAL_BASE_URL = "https://reqres.in/api/"
    val ACTUAL_BASE_URL = "https://reqres.in/api/"

    val BASE_TEST_SERVER = "https://reqres.in/api/"
    //val BASE_TEST_SERVER = "https://reqres.in/api/"

    val BASE_URL_EVENTS = "https://www.aazp.in/events/"
    val API_EVENTS_LIST = "eventslistapi-v1"

    fun <S> convertResponseData(
        responseString: String?, convertedData: Class<S>
    ): S {
        return Gson().fromJson(responseString, convertedData)
    }

    inline fun <reified T> convertResponseToArrayList(jsonString: String): ArrayList<T> {
        val gson = Gson()
        val typeToken = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(jsonString, typeToken)
    }

    /**
     * Validate 200 is true else false
     */
    fun isStatus(code: String): Boolean {
        return code == "true"
    }

    fun isCode(code: String): Boolean {
        return code == "200"
    }
}