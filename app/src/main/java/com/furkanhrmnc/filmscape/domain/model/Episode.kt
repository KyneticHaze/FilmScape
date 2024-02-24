package com.furkanhrmnc.filmscape.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    val episodeName: String,
    val date: String,
    val episodeNumber: Int,
    val voteCount: Int,
    val voteAverage: Double
): Parcelable
