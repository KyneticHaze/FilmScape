package com.furkanhrmnc.filmscape.data.network.dto.detail


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatedBy(
    @SerialName("credit_id")
    val creditİd: String?,
    @SerialName("gender")
    val gender: Int?,
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("original_name")
    val originalName: String?,
    @SerialName("profile_path")
    val profilePath: String?,
)