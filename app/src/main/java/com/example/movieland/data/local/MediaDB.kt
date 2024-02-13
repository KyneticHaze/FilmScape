package com.example.movieland.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author Furkan Harmancı
 * Media veritabanı
 */
@Database(
    entities = [MediaEntity::class],
    version = 1
)
abstract class MediaDB: RoomDatabase() {
    abstract fun mediaDao(): MediaDao
}