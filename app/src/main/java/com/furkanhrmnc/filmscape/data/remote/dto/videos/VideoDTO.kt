package com.furkanhrmnc.filmscape.data.remote.dto.videos


import com.furkanhrmnc.filmscape.domain.model.Video
import com.google.gson.annotations.SerializedName

data class VideoDTO(
    @SerializedName("id")
    val id: String?,
    @SerializedName("iso_3166_1")
    val iso31661: String?,
    @SerializedName("iso_639_1")
    val iso6391: String?,
    @SerializedName("key")
    val key: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("official")
    val official: Boolean?,
    @SerializedName("published_at")
    val publishedAt: String?,
    @SerializedName("site")
    val site: String?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("type")
    val type: String?
)

fun VideoDTO.toVideo(): Video = Video(
    id = id ?: "",
    iso6391 = iso6391 ?: "",
    iso31661 = iso31661 ?: "",
    key = key ?: "",
    publishedAt = publishedAt ?: "",
    name = name ?: "",
    official = official ?: true,
    size = size ?: 0,
    site = site ?: "",
    type = type ?: ""
)