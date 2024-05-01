package com.furkanhrmnc.filmscape.data.remote.dto.details


import com.furkanhrmnc.filmscape.domain.model.details.Country
import com.google.gson.annotations.SerializedName

data class ProductionCountryDTO(
    @SerializedName("iso_3166_1")
    val iso31661: String?,
    @SerializedName("name")
    val name: String?
)

fun ProductionCountryDTO.toCountry(): Country = Country(
    iso = iso31661 ?: "",
    name = name ?: ""
)