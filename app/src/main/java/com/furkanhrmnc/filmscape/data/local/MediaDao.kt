package com.furkanhrmnc.filmscape.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.furkanhrmnc.filmscape.domain.model.Media

@Dao
interface MediaDao {

    @Query("SELECT * FROM media_entity WHERE id = :id")
    fun getMediaById(id: Int): Media

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMedia(media: Media)

    @Update
    fun updateMedia(media: Media)

    @Delete
    fun deleteMedia(media: Media)
}