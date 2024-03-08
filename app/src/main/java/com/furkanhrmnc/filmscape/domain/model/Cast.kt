package com.furkanhrmnc.filmscape.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cast(
    val isAdult: Boolean,
    val castId: Int,
    val character: String,
    val creditId: String,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val order: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String
): Parcelable
