package com.example.sample.roomDatabase

import androidx.room.TypeConverter
import com.example.sample.Model.response.PlantInfoResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ResponseConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromPlantInfoResponse(plantInfoResponse: ArrayList<PlantInfoResponse.TreenamesInfo>): String {
        return gson.toJson(plantInfoResponse)
    }

    @TypeConverter
    fun toPlantInfoResponse(json: String): ArrayList<PlantInfoResponse.TreenamesInfo> {
        val type: Type = object : TypeToken<ArrayList<PlantInfoResponse.TreenamesInfo>>() {}.type
        return gson.fromJson(json, type)
    }
}