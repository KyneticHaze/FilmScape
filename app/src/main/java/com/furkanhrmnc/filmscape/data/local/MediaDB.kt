package com.furkanhrmnc.filmscape.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.furkanhrmnc.filmscape.domain.model.Media

@Database(entities = [Media::class], version = 1)
@TypeConverters(Converters::class)
abstract class MediaDB : RoomDatabase() {
    abstract fun movieDao(): MediaDao
}