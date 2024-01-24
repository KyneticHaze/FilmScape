package com.example.movieland.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MediaEntity::class],
    version = 1
)
abstract class MediaDB: RoomDatabase() {
    abstract fun mediaDao(): MediaDao
}