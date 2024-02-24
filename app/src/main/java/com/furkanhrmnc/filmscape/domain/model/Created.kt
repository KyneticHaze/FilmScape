package com.furkanhrmnc.filmscape.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Created(
    val name: String,
    val createdUrl: String
): Parcelable
