package com.example.movieland.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Media tablosu
 */
@Entity
data class MediaEntity(
    @PrimaryKey val id: Int,

    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: String,
    val genreIds: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
)
