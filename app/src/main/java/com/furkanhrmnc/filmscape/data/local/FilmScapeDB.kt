package com.furkanhrmnc.filmscape.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.furkanhrmnc.filmscape.data.local.detail.DetailDao
import com.furkanhrmnc.filmscape.data.local.detail.DetailEntity
import com.furkanhrmnc.filmscape.data.local.media.MediaDao
import com.furkanhrmnc.filmscape.data.local.media.MediaEntity

/**
 * Projenin veritabanı soyut sınıfıdır.
 *
 * @property MediaEntity
 * @property DetailEntity
 *
 * @author Furkan Harmancı
 */
@Database(
    entities = [MediaEntity::class, DetailEntity::class],
    version = 1
)
abstract class FilmScapeDB: RoomDatabase() {
    abstract fun mediaDao(): MediaDao
    abstract fun detailDao(): DetailDao
}