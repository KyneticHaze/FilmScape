package com.furkanhrmnc.filmscape.data.network.dto.credit


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrewDTO(
    val adult: Boolean?,
    @SerialName("credit_id")
    val creditId: String?,
    val department: String?,
    val gender: Int?,
    val id: Int?,
    val job: String?,
    @SerialName("known_for_department")
    val knownForDepartment: String?,
    val name: String?,
    @SerialName("original_name")
    val originalName: String?,
    val popularity: Double?,
    @SerialName("profile_path")
    val profilePath: String?,
)