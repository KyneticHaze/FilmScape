package com.furkanhrmnc.filmscape.data.local.detail

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DetailDao {

    @Query("SELECT * FROM detail_table WHERE id = :id")
    suspend fun getDetailById(id: Int): DetailEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaDetail(entity: DetailEntity)

    @Delete
    suspend fun deleteMediaDetail(entity: DetailEntity)
}