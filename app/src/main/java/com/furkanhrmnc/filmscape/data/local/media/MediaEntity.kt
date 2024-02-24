package com.furkanhrmnc.filmscape.data.local.media

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.furkanhrmnc.filmscape.common.Constants.MEDIA_TABLE

/**
 * Media tablosu
 */
@Entity(tableName = MEDIA_TABLE)
data class MediaEntity(
    @PrimaryKey val id: Int,

    val adult: Boolean,
    val backdropPath: String,
    val genreIds: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val movieDate: String,
    val tvDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
)
