package com.furkanhrmnc.filmscape.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MediaEntity(
    @PrimaryKey val id: Int,

    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: String,
    val genreIds: String,
    val originalCountry: String,
    val originalLanguage: String,
    val originalName: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val rating: Double,
    val vote: Int,

    var videos: String,

    var mediaType: String,
    var category: String,

    var runtime: Int,
    var status: String,
    var tagline: String,

    var similarMediaList: String
)