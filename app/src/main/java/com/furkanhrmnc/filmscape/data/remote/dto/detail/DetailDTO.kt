package com.furkanhrmnc.filmscape.data.remote.dto.detail

import com.google.gson.annotations.SerializedName

data class DetailDTO(
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("tagline")
    val tagline: String
)
