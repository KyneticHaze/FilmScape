package com.furkanhrmnc.filmscape.data.remote.dto.details.credit

import com.furkanhrmnc.filmscape.domain.model.details.credit.Crew
import com.google.gson.annotations.SerializedName


data class CrewDTO(
    @SerializedName("profile_path")
    val profilePath: String?,
    val id: Int?,
    val job: String?,
    val name: String?
)

fun CrewDTO.toCrew(): Crew = Crew(
    id = id ?: 1,
    profilePath = profilePath ?: "",
    job = job ?: "",
    name = name ?: ""
)