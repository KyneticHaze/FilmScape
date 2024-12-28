package com.furkanhrmnc.filmscape.data.network.dto.credit


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastDTO(
    val adult: Boolean? = null,
    @SerialName("cast_id")
    val castId: Int? = null,
    val character: String? = null,
    @SerialName("credit_id")
    val creditId: String? = null,
    val gender: Int? = null,
    val id: Int? = null,
    @SerialName("known_for_department")
    val knownForDepartment: String? = null,
    val name: String? = null,
    val order: Int? = null,
    @SerialName("original_name")
    val originalName: String? = null,
    val popularity: Double? = null,
    @SerialName("profile_path")
    val profilePath: String? = null,
)