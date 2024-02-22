package com.example.movieland.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * @author Furkan Harmancı
 */
@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedias(medias: List<MediaEntity>)

    /**
     * Tek media ekleme
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(media: MediaEntity)


    @Query("SELECT * FROM mediaentity WHERE id = :id")
    suspend fun getMediaById(id: Int): MediaEntity
}