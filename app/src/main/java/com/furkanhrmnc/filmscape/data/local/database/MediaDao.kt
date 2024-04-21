package com.furkanhrmnc.filmscape.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * CRUD işlemlerinin yapıldığı arayüz
 *
 * @author Furkan Harmancı
 */
@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaList(medias: List<MediaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(media: MediaEntity)

    @Update
    suspend fun updateMedia(media: MediaEntity)


    @Query("DELETE FROM mediaentity WHERE mediaType = :mediaType AND category = :category")
    suspend fun deleteMediaByMediaTypeAndCategory(mediaType: String, category: String)

    @Query("SELECT * FROM mediaentity WHERE id = :id")
    suspend fun selectMediaById(id: Int): MediaEntity

    @Query("SELECT * FROM mediaentity WHERE mediaType = :mediaType AND category = :category")
    suspend fun selectMediaListByMediaTypeAndCategory(mediaType: String, category: String): List<MediaEntity>

    @Query("DELETE FROM mediaentity WHERE category = :category")
    suspend fun deleteTrendingList(category: String)

    @Query("SELECT * FROM mediaentity WHERE category = :category")
    suspend fun selectTrendingListByCategory(category: String): List<MediaEntity>

}