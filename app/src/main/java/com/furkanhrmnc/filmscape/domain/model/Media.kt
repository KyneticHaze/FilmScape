package com.furkanhrmnc.filmscape.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Parcelize
data class Media(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val movieDate: String,
    val tvDate: String,
    val title: String,
    val voteAverage: Double,
    val video: Boolean,
    val voteCount: Int,
) : Parcelable