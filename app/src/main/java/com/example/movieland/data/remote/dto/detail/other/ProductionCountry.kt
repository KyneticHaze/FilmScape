package com.example.movieland.data.remote.dto.detail.other


import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val shortCut: String?,
    @SerializedName("name")
    val name: String?
)