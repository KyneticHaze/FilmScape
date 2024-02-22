package com.example.movieland.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 *
 * Media Veritabanı
 *
 * @author Furkan Harmancı
 */
@Database(
    entities = [MediaEntity::class],
    version = 1
)
abstract class MediaDB: RoomDatabase() {
    abstract fun mediaDao(): MediaDao
}