package com.furkanhrmnc.filmscape.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CastModel(
    val originalName: String,
    val characterName: String,
    val image: String
): Parcelable
