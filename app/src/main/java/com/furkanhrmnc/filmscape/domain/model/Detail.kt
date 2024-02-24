package com.furkanhrmnc.filmscape.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Tv'lerde [originalName]
 *
 * Filmlerde [originalTitle]
 */
@Parcelize
data class Detail(

    val id: Int,

    val backdropPath: String,
    val posterPath: String,
    val isAdult: Boolean,
    val description: String,
    val originalName: String,
    val originalTitle: String,
    val voteAverage: Double,
    val voteCount: Int,
    val mediaUrl: String,
    val status: String,
    val tagline: String,
    val movieDate: String,
    val tvDate: String,
    val numberEpisode: Int,
    val numberSeason: Int,
    val lastEpisode: Episode,
    val nextEpisode: Episode,
    val createdBy: List<Created>,
    val seasons: List<SeasonModel>
): Parcelable
