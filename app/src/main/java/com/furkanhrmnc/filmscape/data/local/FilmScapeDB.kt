package com.furkanhrmnc.filmscape.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.furkanhrmnc.filmscape.data.local.media.MediaDao
import com.furkanhrmnc.filmscape.data.local.media.MediaEntity

/**
 * Projenin soyut veritabanı sınıfıdır.
 *
 * @property MediaEntity
 *
 * @author Furkan Harmancı
 */
@Database(
    entities = [MediaEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FilmScapeDB: RoomDatabase() {
    abstract fun mediaDao(): MediaDao
}