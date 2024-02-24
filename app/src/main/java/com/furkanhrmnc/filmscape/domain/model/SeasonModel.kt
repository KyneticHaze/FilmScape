package com.furkanhrmnc.filmscape.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeasonModel(
    val date: String,
    val episodeNumber: Int,
    val seasonNumber: Int,
    val name: String,
    val image: String,
    val voteAverage: Double
): Parcelable
