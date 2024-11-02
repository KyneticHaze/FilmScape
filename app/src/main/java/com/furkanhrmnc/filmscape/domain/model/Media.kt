package com.furkanhrmnc.filmscape.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.furkanhrmnc.filmscape.util.Constants.MEDIA_TABLE

@Entity(tableName = MEDIA_TABLE)
data class Media(
    @PrimaryKey val id: Int,

    @ColumnInfo(name = "adult") val adult: Boolean,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
    @ColumnInfo(name = "genre_ids") val genreIds: List<Int>,
    @ColumnInfo(name = "original_language") val originalLanguage: String,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "release_date") val releaseDate: String?,
    @ColumnInfo(name = "first_air_date") val firstAirDate: String?,
    @ColumnInfo(name = "origin_country") val originCountry: List<String>?,
    @ColumnInfo(name = "vote_count") val voteCount: Long,
    @ColumnInfo(name = "vote_average") val voteAverage: Float,
)