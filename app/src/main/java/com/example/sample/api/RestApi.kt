package com.example.sample.api

import android.content.Context
import com.example.sample.Utils.AppConstants
import com.example.sample.Utils.SharedPreferenceManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApi {
    companion object {
        var BASE_URL = ParamAPI.ACTUAL_BASE_URL

        fun getClient(context: Context): ApiInterface {
            val authToken = SharedPreferenceManager.getStringPreferences(context, AppConstants.AUTH_TOKEN)

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(oAuthInterceptor(authToken))
                .addInterceptor(loggingInterceptor)
                /*.readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)*/
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiInterface::class.java)
        }

        private fun oAuthInterceptor( authToken: String): Interceptor {
            return Interceptor { chain ->
                var request = chain.request()
                request = request.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", authToken)
                    .build()
                chain.proceed(request)
            }

        }
    }
}