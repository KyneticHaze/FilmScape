package com.furkanhrmnc.filmscape.data.remote.dto.details.credit

import com.furkanhrmnc.filmscape.domain.model.details.credit.Cast
import com.google.gson.annotations.SerializedName


data class CastDTO(
    @SerializedName("profile_path")
    val profilePath: String?,
    val character: String?,
    val id: Int?,
    val name: String?
)

fun CastDTO.toCast(): Cast = Cast(
    id = id ?: 1,
    character = character ?: "",
    profilePath = profilePath ?: "",
    name = name ?: ""
)