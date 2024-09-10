package com.example.sample.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Model::class], version = 1)
@TypeConverters(ResponseConverter::class)
abstract class ResultDatabase : RoomDatabase() {

    abstract fun plantationDao(): DataDao

    companion object {
        @Volatile
        private var instance: ResultDatabase? = null

        fun getDatabase(context: Context): ResultDatabase {
            return instance ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    ResultDatabase::class.java,
                    "plantation_database"
                ).allowMainThreadQueries().build()
                instance = database
                database
            }
        }
    }
}
