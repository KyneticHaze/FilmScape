package com.furkanhrmnc.filmscape.data.remote.dto.details


import com.furkanhrmnc.filmscape.domain.model.details.Language
import com.google.gson.annotations.SerializedName

data class SpokenLanguageDTO(
    @SerializedName("english_name")
    val englishName: String?,
    @SerializedName("iso_639_1")
    val iso6391: String?,
    @SerializedName("name")
    val name: String?
)

fun SpokenLanguageDTO.toLanguage(): Language = Language(
    englishName = englishName ?: "",
    iso = iso6391 ?: "",
    name = name ?: ""
)