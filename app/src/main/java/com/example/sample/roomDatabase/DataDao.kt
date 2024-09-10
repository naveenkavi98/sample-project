package com.example.sample.roomDatabase

import androidx.room.*

@Dao
interface DataDao {

    @Query("SELECT * FROM plantation_data where plantId= :id")
    fun getById(id: Int): Model

    @Query("SELECT * FROM plantation_data")
    fun getAll(): List<Model>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(resultOffline: Model)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(resultOffline: List<Model>)

    @Update
    fun update(resultOffline: Model)

    @Delete
    fun deleteItem(resultOffline: Model)

    @Query("DELETE FROM plantation_data")
    fun delete()
}