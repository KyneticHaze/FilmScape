package com.example.movieland.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedias(medias: List<MediaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(media: MediaEntity)

    @Update
    suspend fun updateMedia(media: MediaEntity)

    @Query("SELECT * FROM mediaentity WHERE id = :id")
    suspend fun getMediaById(id: Int): MediaEntity

    @Query("SELECT * FROM mediaentity WHERE mediaType = :mediaType AND category = :category")
    suspend fun getMediasByTypeAndCategory(
        mediaType: String, category: String
    ): List<MediaEntity>

    @Query("DELETE FROM mediaentity WHERE mediaType = :mediaType AND category = :category")
    suspend fun deleteMediaByTypeAndCategory(mediaType: String, category: String)

    @Query("SELECT * FROM mediaentity WHERE category = :category")
    suspend fun getTrendingMedias(category: String): List<MediaEntity>

    @Query("DELETE FROM mediaentity WHERE category = :category")
    suspend fun deleteTrendingMedia(category: String)
}