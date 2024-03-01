package com.furkanhrmnc.filmscape.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DetailDTO(
    @SerializedName("runtime")
    val runtime: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?
)
