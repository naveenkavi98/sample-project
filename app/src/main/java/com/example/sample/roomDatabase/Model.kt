package com.example.sample.roomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sample.Model.response.PlantInfoResponse

@Entity(tableName = "plantation_data")
data class Model(
    @PrimaryKey
    val plantId: Int,
    val timeAndTime: String,
    val result: ArrayList<PlantInfoResponse.TreenamesInfo>
)