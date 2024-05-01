package com.furkanhrmnc.filmscape.data.remote.dto.details


import com.furkanhrmnc.filmscape.domain.model.details.Company
import com.google.gson.annotations.SerializedName

data class ProductionCompanyDTO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?
)

fun ProductionCompanyDTO.toCompany(): Company = Company(
    id = id ?: 1,
    logo = logoPath ?: "",
    name = name ?: "",
    country = originCountry ?: ""
)